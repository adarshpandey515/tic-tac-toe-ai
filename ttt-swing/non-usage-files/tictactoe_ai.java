
import java.util.*;

public class tictactoe_ai {
    
    // This function is used to draw the board's current state every time the user's turn arrives.
    static void printBoard(int[] board) {
        System.out.println("Current State Of Board : \n\n");
        for (int i = 0; i < 9; i++) {
            if (i > 0 && i % 3 == 0) {
                System.out.println("\n");
            }
            if (board[i] == 0) {
                System.out.print("- ");
            } else if (board[i] == 1) {
                System.out.print("O ");
            } else if (board[i] == -1) {
                System.out.print("X ");
            }
        }
        System.out.println("\n\n");
    }

    // This function takes the user's move as input and makes the required changes on the board.
    static void user1Turn(int[] board) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter X's position from [1...9]: ");
        int pos = scanner.nextInt();
        if (board[pos - 1] != 0) {
            System.out.println("Wrong Move!!!");
            System.exit(0);
        }
        board[pos - 1] = -1;
    }

    static void user2Turn(int[] board) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter O's position from [1...9]: ");
        int pos = scanner.nextInt();
        if (board[pos - 1] != 0) {
            System.out.println("Wrong Move!!!");
            System.exit(0);
        }
        board[pos - 1] = 1;
    }

    // MinMax function.
    static int minimax(int[] board, int player,int depth) {
        int x = analyzeBoard(board);
        if (x != 0) {
            return x * player;
        }
        if(depth==0){
            return 5;
        }
        int pos = -1;
        int value = -2;
        for (int i = 0; i < 9; i++) {
            if (board[i] == 0) {
                board[i] = player;
                int score = -minimax(board, player * -1,depth-1);
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

    // This function makes the computer's move using the minmax algorithm.
    static void compTurn(int[] board,int depth) {
        int pos = -1;
        int value = -2;
        Random rand = new Random();
          boolean useRandom=false;
          if(depth==1){
              useRandom=rand.nextInt(100) < 50;
          }
          else if(depth==2){
              useRandom=rand.nextInt(100) < 40;
          }else if( depth ==3){
             useRandom=rand.nextInt(100) < 30;
          }else if(depth ==4){
            useRandom=rand.nextInt(100) <20;
          }
     // 40% chance of random move
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
        } else{

            for (int i = 0; i < 9; i++) {
                if (board[i] == 0) {
                    board[i] = 1;
                    int score = -minimax(board, -1,depth-1);
                    board[i] = 0;
                    if (score > value) {
                        value = score;
                        pos = i;
                    }
                }
            }
        }
        board[pos] = 1;
    }

    // This function is used to analyze a game.
    static int analyzeBoard(int[] board) {
        int[][] cb = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
        };

        for (int i = 0; i < 8; i++) {
            if (board[cb[i][0]] != 0 && board[cb[i][0]] == board[cb[i][1]] && board[cb[i][0]] == board[cb[i][2]]) {
                return board[cb[i][2]];
            }
        }
        return 0;
    }

    // Main Function.
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter 1 for single player, 2 for multiplayer: ");
        int choice=1;
        // int choice = scanner.nextInt();
        int[] board = new int[9];
        
        if (choice == 1) {
            System.out.println("Computer : O Vs. You : X");
            System.out.print("Enter to play 1(st) or 2(nd) : ");
            int player = scanner.nextInt();
            int level =5;
            // System.out.print("\nEnter the Level Easy(1) or Medium (2) or Hard (3) or Impossible (4): ");
            // level = scanner.nextInt();
            // System.out.println(level);
            for (int i = 0; i < 9; i++) {
                if (analyzeBoard(board) != 0) {
                    break;
                }
                if ((i + player) % 2 == 0) {
                    compTurn(board,level);
                } else {
                    printBoard(board);
                    user1Turn(board);
                }
            }
            if(level>2){
                level=level-1;
            }
        } else {
            for (int i = 0; i < 9; i++) {
                if (analyzeBoard(board) != 0) {
                    break;
                }
                if (i % 2 == 0) {
                    printBoard(board);
                    user1Turn(board);
                } else {
                    printBoard(board);
                    user2Turn(board);
                }
            }
        }

        int x = analyzeBoard(board);
        if (x == 0) {
            printBoard(board);
            System.out.println("Draw!!!");
        } else if (x == -1) {
            printBoard(board);
            System.out.println("X Wins!!! O Loses !!!");
        } else if (x == 1) {
            printBoard(board);
            System.out.println("X Loses!!! O Wins !!!!");
        }
    }
}
