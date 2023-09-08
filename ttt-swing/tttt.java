import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class tttt extends JFrame {
    private Clip clipstarting;

    public JButton[][] buttons;
    private char currentPlayer;
    private JPanel homePanel;
    private JPanel gamePanel;

    public char oppent;
    public Color def;
    public int level = 2;
    JButton undo = new JButton("Undo");
    JButton redo = new JButton("Redo");
    public boolean computergame = false;
    public DoublyLinkedListExample list = new DoublyLinkedListExample();
    public void startingmusic(String filename,int stopingtime) {
      try {
            // Load an audio file
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filename));
            
            // Get a Clip to play the audio
            clipstarting = AudioSystem.getClip();
            clipstarting.open(audioInputStream);

            // Set when to start playing (in milliseconds)
            long startTime = 0; // Play after 5 seconds (adjust as needed)
            
            // Set when to stop playing (in milliseconds)
            long stopTime = stopingtime; // Stop after 15 seconds (adjust as needed)

            // Start playing
            clipstarting.setMicrosecondPosition(startTime * 1000);
            clipstarting.start();

            // Stop the music when required
            Timer timer = new Timer(0, e -> {
                if (clipstarting.getMicrosecondPosition() >= stopTime * 1000) {
                    clipstarting.stop();
                    clipstarting.close();
                }
            });
            timer.setRepeats(false);
            
            timer.start();

        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    public tttt() {

        this.setTitle("Tic-Tac-Toe");
        this.setSize(600, 300);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        homePanel = new JPanel();
        ImageIcon image = new ImageIcon("image1.jpg"); // Replace "image.jpg" with your image file's path
        JLabel label = new JLabel(image);

        JButton playerVsPlayerButton = new JButton("Player vs. Player");
        JButton compVsPlayerButton = new JButton("Comp vs. Player");
        playerVsPlayerButton.addActionListener(new PlayerVsPlayerListener());
        compVsPlayerButton.addActionListener(new CompVsPlayerListener());
        homePanel.add(label);
        homePanel.add(playerVsPlayerButton);
        homePanel.add(compVsPlayerButton);
        undo.addActionListener(new UndoListener());
        redo.addActionListener(new RedoListener());
        undo.setEnabled(false);
        redo.setEnabled(false);
        JPanel bottom = new JPanel(new BorderLayout());
        this.add(homePanel);
        bottom.add(undo, BorderLayout.WEST);
        bottom.add(redo, BorderLayout.EAST);
        this.add(bottom, BorderLayout.SOUTH);
        this.setVisible(true);
        startingmusic("bg-music.wav", 200000);
        startingmusic("bg-starting.wav", 5000);
    }

    // Create the JFrame
    public void silder() {
        JFrame frame = new JFrame("Slider Panel Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 200);

        // Create the main JPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Create a label to display the current mode and level

        JLabel modeLabel = new JLabel(
                "EASY                                               MEDIUM                             HARD                           IMPOSSIBLE");

        // Create a slider
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 3, 0);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int selectedIndex = slider.getValue();
                switch (selectedIndex) {
                    case 0:
                        level = 2;
                        break;
                    case 1:
                        level = 4;
                        break;
                    case 2:
                        level = 5;
                        break;
                    case 3:
                        level = 11;
                        break;
                    default:
                        level = 2;
                        break;
                }

            }
        });

        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));
        sliderPanel.add(modeLabel);
        sliderPanel.add(slider);

        mainPanel.add(sliderPanel, BorderLayout.CENTER);

        frame.add(mainPanel);

        // Show the slider and wait for user input
        int result = JOptionPane.showOptionDialog(frame, mainPanel, "Select Level", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, null, null);
        if (result == JOptionPane.OK_OPTION) {
            // User clicked OK, so you can use the selected level (variable 'level') here.
            System.out.println("Selected Level: " + level);
        } else {
            System.out.println("User canceled.");
        }

        frame.dispose();
    }

    private class PlayerVsPlayerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Set up the game for Player vs. Player mode
            char userChoice = getUserChoice(); // Implement this method to get user choice ('X' or 'O')
            initializeplayer(userChoice);
        }
    }

    private class CompVsPlayerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Set up the game for Comp vs. Player mode
            char userChoice = getUserChoice();
            boolean playchoice = playfirst();
            System.out.println(userChoice);
            System.out.println(playchoice);
            initializecomputer(userChoice, playchoice);
            // Add code for Comp vs. Player mode
        }
    }

    private class UndoListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // undo the last move
            buttons[list.row()][list.col()].setText("");
            buttons[list.row()][list.col()].setEnabled(true);
            buttons[list.row()][list.col()].setBackground(Color.PINK);
            currentPlayer = list.curPlayer();
            list.undo();
            if (computergame == true) {
                buttons[list.row()][list.col()].setText("");
                buttons[list.row()][list.col()].setEnabled(true);
                buttons[list.row()][list.col()].setBackground(Color.PINK);
                currentPlayer = list.curPlayer();
                list.undo();
            }

        }
    }

    private class RedoListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // redo the last move
            if (computergame == true) {
                list.redo();

                buttons[list.row()][list.col()].setText(String.valueOf(list.curPlayer()));
                if (list.curPlayer() == 'X') {
                    currentPlayer = 'O';
                    buttons[list.row()][list.col()].setBackground(Color.YELLOW);
                } else {
                    currentPlayer = 'X';
                    buttons[list.row()][list.col()].setBackground(Color.GREEN);
                }
                buttons[list.row()][list.col()].setEnabled(false);
            }
            if (list.empty()) {
                return;
            }

            list.redo();

            buttons[list.row()][list.col()].setText(String.valueOf(list.curPlayer()));
            if (list.curPlayer() == 'X') {
                currentPlayer = 'O';
                buttons[list.row()][list.col()].setBackground(Color.YELLOW);
            } else {
                currentPlayer = 'X';
                buttons[list.row()][list.col()].setBackground(Color.GREEN);
            }
            buttons[list.row()][list.col()].setEnabled(false);
        }
    }

    private char getUserChoice() {
        String[] options = { "X", "O" };
        int choice = JOptionPane.showOptionDialog(
                this,
                "Choose 'X' or 'O':",
                "Player Choice",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        return choice == JOptionPane.YES_OPTION ? 'X' : 'O';
    }

    private boolean playfirst() {
        String[] options = { "Yes", "No" };
        int choice = JOptionPane.showOptionDialog(
                this,
                "Do you want to play first?",
                "Player Choice",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);

        return choice == JOptionPane.YES_OPTION ? true : false;
    }

    private void initializeplayer(char userChoice) {
        gamePanel = new JPanel(new GridLayout(3, 3));
        this.buttons = new JButton[3][3];
        this.currentPlayer = userChoice;

        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 3; ++col) {
                this.buttons[row][col] = new JButton("");
                this.buttons[row][col].setFont(new Font("Arial", 0, 60));
                this.buttons[row][col].setBackground(Color.PINK);
                this.buttons[row][col].addActionListener(new GameButtonPlayer(row, col));
                int row1 = row;
                int col1 = col;
                this.buttons[row][col].addMouseListener(new java.awt.event.MouseAdapter() {

                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        def = buttons[row1][col1].getBackground();
                        Color lightgryColor = new Color(10, 150, 205, 100);
                        buttons[row1][col1].setBackground(lightgryColor);
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {

                        buttons[row1][col1].setBackground(def);
                    }

                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        if (currentPlayer == 'X') {

                            def = Color.GREEN;
                        } else {
                            def = Color.YELLOW;
                        }
                        buttons[row1][col1].setBackground(def);
                    }
                });

                gamePanel.add(this.buttons[row][col]);
            }
        }

        showGamePanel();
    }

    private void initializecomputer(char userChoice, boolean playchoice) {
        computergame = true;
        gamePanel = new JPanel(new GridLayout(3, 3));
        this.buttons = new JButton[3][3];
        this.currentPlayer = userChoice;

        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 3; ++col) {
                this.buttons[row][col] = new JButton("");
                this.buttons[row][col].setFont(new Font("Arial", 0, 60));
                this.buttons[row][col].setBackground(Color.PINK);
                this.buttons[row][col].addActionListener(new GameButtonComputer(row, col));
                int row1 = row;
                int col1 = col;
                this.buttons[row][col].addMouseListener(new java.awt.event.MouseAdapter() {

                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        def = buttons[row1][col1].getBackground();
                        Color lightgryColor = new Color(10, 150, 205, 100);
                        buttons[row1][col1].setBackground(lightgryColor);
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {

                        buttons[row1][col1].setBackground(def);
                    }

                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        def = Color.YELLOW;
                        buttons[row1][col1].setBackground(def);
                    }
                });

                gamePanel.add(this.buttons[row][col]);
            }
        }
        if (currentPlayer == 'X') {
            oppent = 'O';
        } else {
            oppent = 'X';
        }

        showGamePanel();

        if (playchoice == false) {
            // Making of random first computer choice so that same pattern of game is not
            // repeated
            int i = (int) (Math.random() * 3);
            int j = (int) (Math.random() * 3);
            buttons[i][j].setText(String.valueOf(oppent));
            buttons[i][j].setEnabled(false);
            buttons[i][j].setBackground(Color.GREEN);
            list.addToBack(i, j, oppent);

        }

    }

    private class GameButtonPlayer implements ActionListener {
        private int row;
        private int col;

        public GameButtonPlayer(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public void actionPerformed(ActionEvent e) {
            startingmusic("bg-click.wav", 2000);

            JButton clickedButton = (JButton) e.getSource();
            if (clickedButton.getText().equals("")) {
                clickedButton.setText(String.valueOf(currentPlayer));
                list.addToBack(row, col, currentPlayer);
                if (currentPlayer == 'X') {
                    buttons[row][col].setBackground(Color.YELLOW);
                } else {
                    buttons[row][col].setBackground(Color.GREEN);
                }
                System.out.println("Player's Move ---> " + currentPlayer + " :" + (row * 3 + col) + "\n");
                clickedButton.setEnabled(false);
                if (checkForWin()) {
                    JOptionPane.showMessageDialog(tttt.this, "Player " + currentPlayer + " wins!");
                    startingmusic("bg-win.wav", 5000);
                    resetBoard();
                } else if (checkForDraw()) {
                    JOptionPane.showMessageDialog(tttt.this, "It's a draw!");
                    startingmusic("bg-over.wav", 5000);
                    resetBoard();
                } else {
                    currentPlayer = (char) (currentPlayer == 'X' ? 'O' : 'X');
                }
            }

        }
    }

    private class GameButtonComputer implements ActionListener {
        private int row;
        private int col;

        public GameButtonComputer(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public void actionPerformed(ActionEvent e) {
             startingmusic("bg-click.wav", 2000);
            boolean resetBoard = false;
            JButton clickedButton = (JButton) e.getSource();

            if (clickedButton.getText().equals("")) {
                clickedButton.setText(String.valueOf(currentPlayer));
                buttons[row][col].setBackground(Color.YELLOW);
                list.addToBack(row, col, currentPlayer);
                System.out.println("Player's Move ---> " + currentPlayer + " :" + (row * 3 + col) + "\n");
                clickedButton.setEnabled(false);
                if (checkForWin()) {
                    startingmusic("bg-win.wav", 5000);
                    JOptionPane.showMessageDialog(tttt.this, "Player " + currentPlayer + " wins!");
                    resetBoard = true;
                } else if (checkForDraw()) {
                      startingmusic("bg-over.wav", 5000);
                    JOptionPane.showMessageDialog(tttt.this, "It's a draw!");
                    resetBoard = true;
                }
            }
            // if game ends then reset the board

            if (resetBoard) {
                resetBoard();
            }

            int[] board = new int[9];
            for (int i = 0; i < 9; i++) {
                if (buttons[i / 3][i % 3].getText().equals("")) {
                    board[i] = 0;
                } else if (buttons[i / 3][i % 3].getText().equals(String.valueOf(currentPlayer))) {
                    board[i] = -1;
                } else {
                    board[i] = 1;
                }
            }
            compTurn(board);
            if (checkForWin()) {
                  startingmusic("bg-over.wav", 5000);
                JOptionPane.showMessageDialog(tttt.this, "Computer  " + " wins!");
                resetBoard = true;
            } else if (checkForDraw()) {
                  startingmusic("bg-over.wav", 5000);
                JOptionPane.showMessageDialog(tttt.this, "It's a draw!");
                resetBoard = true;
            }
            if (resetBoard) {
                resetBoard();
            }

        }
    }
    // starting of minimax algorithm fxn starts here

    static int analyzeBoard(int[] board) {
        int[][] cb = {
                { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 },
                { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 },
                { 0, 4, 8 }, { 2, 4, 6 }
        };

        for (int i = 0; i < 8; i++) {
            if (board[cb[i][0]] != 0 && board[cb[i][0]] == board[cb[i][1]] && board[cb[i][0]] == board[cb[i][2]]) {
                return board[cb[i][2]];
            }
        }
        return 0;
    }

    // MinMax function.
    static int minimax(int[] board, int player, int depth) {
        int x = analyzeBoard(board);
        if (x != 0) {
            return x * player;
        }
        if (depth == 0) {
            return 5;
        }
        int pos = -1;
        int value = -2;
        for (int i = 0; i < 9; i++) {
            if (board[i] == 0) {
                board[i] = player;
                int score = -minimax(board, player * -1, depth - 1);
                if (score > value) {
                    value = score;
                    pos = i;
                }
                board[i] = 0;
            }
        }

        if (pos == -1) {
            return 0;
        }
        return value;
    }

    public void compTurn(int[] board) {
        // System.out.println("Button value :"+buttons[0][0].getText());
        int pos = -1;
        int value = -2;
        int depth = level;
        Random rand = new Random();
        boolean useRandom = false;
        if (depth == 1) {
            useRandom = rand.nextInt(100) < 50;
        } else if (depth == 2) {
            useRandom = rand.nextInt(100) < 40;
        } else if (depth == 3) {
            useRandom = rand.nextInt(100) < 30;
        } else if (depth == 4) {
            useRandom = rand.nextInt(100) < 20;
        }
        if (useRandom) {
            int emptyCount = 0;
            for (int i = 0; i < 9; i++) {
                if (board[i] == 0) {
                    emptyCount++;
                }
            }
            if (emptyCount > 0) {
                int randomEmptyIndex = rand.nextInt(emptyCount);
                for (int i = 0; i < 9; i++) {
                    if (board[i] == 0) {
                        if (randomEmptyIndex == 0) {
                            pos = i;
                            break;
                        }
                        randomEmptyIndex--;
                    }
                }
            }
        } else {

            for (int i = 0; i < 9; i++) {
                if (board[i] == 0) {
                    board[i] = 1;
                    int score = -minimax(board, -1, depth - 1);
                    board[i] = 0;
                    if (score > value) {
                        value = score;
                        pos = i;
                    }
                }
            }
        }

        buttons[pos / 3][pos % 3].setText(String.valueOf(oppent));
        buttons[pos / 3][pos % 3].setEnabled(false);
        buttons[pos / 3][pos % 3].setBackground(Color.GREEN);
        list.addToBack(pos / 3, pos % 3, oppent);
        if(level>2){
            level = level - 1;
        }

        System.out.println("Computer's Move : " + pos);
    }

    // Ends here of minimax algorithm fxn

    private boolean checkForWin() {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(buttons[i][1].getText()) &&
                    buttons[i][0].getText().equals(buttons[i][2].getText()) &&
                    !buttons[i][0].getText().isEmpty()) {
                return true; // Row win
            }

            if (buttons[0][i].getText().equals(buttons[1][i].getText()) &&
                    buttons[0][i].getText().equals(buttons[2][i].getText()) &&
                    !buttons[0][i].getText().isEmpty()) {
                return true; // Column win
            }
        }

        // Check diagonals
        if (buttons[0][0].getText().equals(buttons[1][1].getText()) &&
                buttons[0][0].getText().equals(buttons[2][2].getText()) &&
                !buttons[0][0].getText().isEmpty()) {
            return true; 
            // Diagonal win
        }

        if (buttons[0][2].getText().equals(buttons[1][1].getText()) &&
                buttons[0][2].getText().equals(buttons[2][0].getText()) &&
                !buttons[0][2].getText().isEmpty()) {
            return true; // Diagonal win
        }

        return false; // No win
    }

    private boolean checkForDraw() {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 3; ++col) {
                if (this.buttons[row][col].getText().equals("")) {
                    return false;
                }
            }
        }

        return true;
    }

    private void resetBoard() {
        System.out.println("RESET BOARD");
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 3; ++col) {
                this.buttons[row][col].setText("");
                this.buttons[row][col].setEnabled(true);
            }
        }

        this.remove(gamePanel);

        homePanel = new JPanel();
        JButton playerVsPlayerButton = new JButton("Player vs. Player");
        JButton compVsPlayerButton = new JButton("Comp vs. Player");
        this.setSize(600, 300);
        playerVsPlayerButton.addActionListener(new PlayerVsPlayerListener());
        compVsPlayerButton.addActionListener(new CompVsPlayerListener());
         startingmusic("bg-starting.wav", 5000);
        ImageIcon image = new ImageIcon("image1.jpg"); // Replace "image.jpg" with your image file's path
        JLabel label = new JLabel(image);
        computergame=false;
        homePanel.add(label);
        homePanel.add(playerVsPlayerButton);
        homePanel.add(compVsPlayerButton);
        this.add(homePanel);

    }

    private void showGamePanel() {
        this.remove(homePanel);
        if (computergame == true) {
            silder();
        }
        undo.setEnabled(true);
        redo.setEnabled(true);
        this.setSize(550, 500);
        this.add(gamePanel);
        this.revalidate();
        this.repaint();
    }

    public static void main(String[] args) {

        tttt game = new tttt();

    }
}

class DoublyLinkedListExample {
    class Node {
        int row;
        int col;
        char curPlayer;
        Node prev;
        Node next;

        public Node(int row, int col, char curPlayer) {
            this.row = row;
            this.col = col;
            this.curPlayer = curPlayer;
            this.prev = null;
            this.next = null;
        }
    }

    private Node head;

    private Node current;

    public DoublyLinkedListExample() {
        this.head = null;

        this.current = null;

    }

    public int row() {
        return current.row;
    }

    public int col() {
        return current.col;
    }

    public char curPlayer() {
        return current.curPlayer;
    }

    public void addToBack(int row, int col, char curPlayer) {

        Node newNode = new Node(row, col, curPlayer);
        if (head == null) {
            head = newNode;
            current = newNode;
        } else {
            newNode.prev = current;
            current.next = newNode;
            current = newNode;

        }

    }

    public void undo() {
        if (current.prev != null) {
            current = current.prev;
            System.out.println(" Undo Move excuted --> Row: " + current.row + ", Col: " + current.col
                    + ", Current Player: " + current.curPlayer);
        } else {
            System.out.println("No more moves to undo");
        }

    }

    public void redo() {
        if (current.next != null) {
            current = current.next;
            System.out.println(" Redo Move excuted --> Row: " + current.row + ", Col: " + current.col
                    + ", Current Player: " + current.curPlayer);
        } else {
            System.out.println("No more moves to redo");
        }

    }

    public boolean empty() {
        if (current == null) {
            return true;
        }
        return false;
    }

    public void printList() {
        System.out.println("\n Printing the list from head to tail: \n");
        Node temp = head;
        while (temp != current.next) {
            System.out.println("Row: " + temp.row + ", Col: " + temp.col + ", Current Player: " + temp.curPlayer);
            temp = temp.next;
        }
        System.out.println("\n Printed the list from tail to head: \n");
    }

}
