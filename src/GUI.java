import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Random;

public class GUI implements ActionListener {

    public static final JButton[] buttons = new JButton[9];
    private static final JLabel terminal = new JLabel("Let the battle begin");
    private static boolean oneTurn;
    private static final JButton startButton = new JButton("Start");
    private static final JPanel bottom = new JPanel(new GridLayout(2, 1));
    private static boolean gameStarted = false;
    private static final int[][] lines = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };

    GUI() {
        JFrame frame = new JFrame();
        frame.setTitle("Tic-Tac-Toe");

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Column 0
        gbc.gridy = 0; // Row 0
        gbc.anchor = GridBagConstraints.CENTER;

//      Title of the Application
        JLabel tLabel = new JLabel("Welcome to Tic-Tac-Toe");
        tLabel.setFont(TFont.arial_24_bold);

//      Top
        JPanel top = new JPanel(new GridBagLayout());
        top.setPreferredSize(new Dimension(100, 100));
        top.add(tLabel, gbc);

//      Center
        JPanel center = new JPanel(new GridLayout(3, 3));
        for(int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttons[i].setFocusable(false);
            buttons[i].setBackground(Color.WHITE);
            buttons[i].setFont(TFont.arial_36_plain);
            buttons[i].addActionListener(this);
            center.add(buttons[i]);
        }

//      Bottom
        bottom.setPreferredSize(new Dimension(100, 120));
        bottom.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        terminal.setFont(TFont.arial_24_plain);

        startButton.setFont(TFont.arial_20_bold);
        startButton.setPreferredSize(new Dimension(150, 50));
        startButton.addActionListener(this);

        JPanel terminalWrapper = new JPanel(new FlowLayout());
        terminalWrapper.add(terminal);

        JPanel startButtonWrapper = new JPanel(new FlowLayout());
        startButtonWrapper.add(startButton);

        bottom.add(terminalWrapper);
        bottom.add(startButtonWrapper);

//      Add to frame
        contentPane.add(top, BorderLayout.NORTH);
        contentPane.add(bottom, BorderLayout.SOUTH);
        contentPane.add(center, BorderLayout.CENTER);

        frame.add(contentPane);
        frame.setSize(400, 620);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void firstTurn() {
        Random random = new Random();
        oneTurn = random.nextBoolean();
        if(oneTurn) terminal.setText("It's O's turn"); else terminal.setText("It's X's turn");
    }

    private void check() {

        boolean tie = true;

        for (int[] line : lines) {
            if (!buttons[line[0]].getText().isEmpty() &&
                    Objects.equals(buttons[line[0]].getText(), buttons[line[1]].getText()) &&
                    Objects.equals(buttons[line[1]].getText(), buttons[line[2]].getText())) {
                someoneWon(line[0], line[1], line[2]);
                return;
            }
        }

        for (JButton button : buttons) {
            if (button.getText().isEmpty()) {
                tie = false;
                break;
            }
        }

        if (tie) {
            terminal.setText("It's a tie!");
            for (JButton button : buttons) {
                button.setEnabled(false);
            }
        }
    }

    private void someoneWon(int x, int y, int z) {

        if(Objects.equals(buttons[x].getText(), "X")) terminal.setText("X Won"); else terminal.setText("O Won");

        buttons[x].setBackground(Color.GREEN);
        buttons[y].setBackground(Color.GREEN);
        buttons[z].setBackground(Color.GREEN);

        for(int i = 0; i < 9; i++) {
            if(i == x || i == y || i == z) continue;
            buttons[i].setEnabled(false);
        }

    }

    private void newGame() {
        firstTurn();
        for(int i = 0; i < 9; i++) {
            buttons[i].setEnabled(true);
            buttons[i].setBackground(Color.WHITE);
            buttons[i].setText("");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == startButton) {
            if(Objects.equals(startButton.getText(), "Start")) {
                firstTurn();
                startButton.setText("New Game");
            } else {
                newGame();
            }
            gameStarted = true;
        }

        for(int i = 0; i < 9; i++) {
            if(e.getSource() == buttons[i]) {
                if(gameStarted) {
                    if(buttons[i].getText().isEmpty()) {
                        if(oneTurn) {
                            buttons[i].setText("O");
                            terminal.setText("It's X's turn");
                            oneTurn = false;
                        } else {
                            buttons[i].setText("X");
                            terminal.setText("It's O's turn");
                            oneTurn = true;
                        }
                    }
                }
                check();
            }
        }
    }
}