// Source code is decompiled from a .class file using FernFlower decompiler.
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class ttt extends JFrame implements ActionListener {
   private JButton[][] buttons;
   private char currentPlayer;

   public ttt() {
      this.setTitle("Tic-Tac-Toe");
      this.setSize(500, 550);
      this.setDefaultCloseOperation(3);
      this.setLayout(new GridLayout(3, 3));
      this.buttons = new JButton[3][3];
      this.currentPlayer = 'X'; 

      for(int row = 0; row < 3; ++row) {
         for(int col = 0; col < 3; ++col) {
            this.buttons[row][col] = new JButton("");
            this.buttons[row][col].setFont(new Font("Arial", 0, 60));
            this.buttons[row][col].addActionListener(this);
            this.add(this.buttons[row][col]);
         }
      }

   }

   public void actionPerformed(ActionEvent e) {
      JButton clickedButton = (JButton)e.getSource();
      if (clickedButton.getText().equals("")) {
         clickedButton.setText(String.valueOf(this.currentPlayer));
         clickedButton.setEnabled(false);
         if (this.checkForWin()) {
            JOptionPane.showMessageDialog(this, "Player " + this.currentPlayer + " wins!");
            this.resetBoard();
         } else if (this.checkForDraw()) {
            JOptionPane.showMessageDialog(this, "It's a draw!");
            this.resetBoard();
         } else {
            this.currentPlayer = (char)(this.currentPlayer == 'X' ? 79 : 88);
         }
      }

   }

   private boolean checkForWin() {
      return this.checkLine(0, 0, 0, 1, 0, 2) || this.checkLine(1, 0, 1, 1, 1, 2) || this.checkLine(2, 0, 2, 1, 2, 2) || this.checkLine(0, 0, 1, 0, 2, 0) || this.checkLine(0, 1, 1, 1, 2, 1) || this.checkLine(0, 2, 1, 2, 2, 2) || this.checkLine(0, 0, 1, 1, 2, 2) || this.checkLine(0, 2, 1, 1, 2, 0);
   }

   private boolean checkLine(int row1, int col1, int row2, int col2, int row3, int col3) {
      return this.buttons[row1][col1].getText().equals(String.valueOf(this.currentPlayer)) && this.buttons[row2][col2].getText().equals(String.valueOf(this.currentPlayer)) && this.buttons[row3][col3].getText().equals(String.valueOf(this.currentPlayer));
   }

   private boolean checkForDraw() {
      for(int row = 0; row < 3; ++row) {
         for(int col = 0; col < 3; ++col) {
            if (this.buttons[row][col].getText().equals("")) {
               return false;
            }
         }
      }

      return true;
   }

   private void resetBoard() {
      for(int row = 0; row < 3; ++row) {
         for(int col = 0; col < 3; ++col) {
            this.buttons[row][col].setText("");
            this.buttons[row][col].setEnabled(true);
         }
      }

      this.currentPlayer = 'X';
   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> {
         ttt game = new ttt();
         game.setVisible(true);
      });
   }
}
