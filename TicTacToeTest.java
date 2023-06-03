package cli;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TicTacToe {
    static Scanner sc = new Scanner(System.in);
    static char[][] board = new char[3][3];
    public enum Difficulty {
        EASY,
        MEDIUM,
        HARD
    }
    public static void main(String[] args) {
        boolean playAgain = true;
        while (playAgain) {
        initializeBoard();
        displayBoard();
        int choice;
        do {
            System.out.println("Choose your game mode:");
            System.out.println("1. Single Player (Against Bot)");
            System.out.println("2. Multiplayer");
            System.out.print("Enter your choice (1 or 2): ");
            choice = sc.nextInt();
        } while (choice != 1 && choice != 2);

        boolean isSinglePlayer = choice == 1;

        String player1Name, player2Name =" ";
        Difficulty difficulty = null;
        if (!isSinglePlayer) {
            System.out.print("Enter name of player 1: ");
            player1Name = sc.next();
            System.out.print("Enter name of player 2: ");
            player2Name = sc.next();
            System.out.println("Welcome " + player1Name + " and " + player2Name + "!");
        } else {
            player1Name = "Player 1";
            player2Name = "Bot";
            System.out.println("Welcome to the game against the bot!");

            // Prompt the player to choose a difficulty level

            do {
                System.out.println("Choose bot difficulty level:");
                System.out.println("1. Easy");
                System.out.println("2. Medium");
                System.out.println("3. Hard");
                System.out.print("Enter your choice (1-3): ");
                int diffChoice = sc.nextInt();
                switch (diffChoice) {
                    case 1:
                        difficulty = Difficulty.EASY;
                        break;
                    case 2:
                        difficulty = Difficulty.MEDIUM;
                        break;
                    case 3:
                        difficulty = Difficulty.HARD;
                        break;
                    default:
                        difficulty = null;
                        break;
                }
            } while (difficulty == null);
        }

        boolean gameOver = false;
        int player = 1;
        char symbol = ' ';
        while (!gameOver) {
            if (player % 2 == 1) {
                symbol = 'X';
                playerMove(player1Name, player, symbol);
            } else {
                symbol = 'O';
                if (isSinglePlayer) {
                    botMove(player2Name, player, symbol, difficulty); // Pass difficulty to botMove()
                } else {
                    playerMove(player2Name, player, symbol);
                } // Pass difficulty to botMove()
            }


            displayBoard();
            gameOver = checkWin(symbol) || checkDraw();
            player++;
        }

            if (checkWin(symbol)) {
                try {
                    if (symbol == 'X') {
                        System.out.println("Congratulations! " + player1Name + " wins! at step " + (player - 1));
                    } else {
                        if (isSinglePlayer) {
                            System.out.println("You lost! Better luck next time.");
                        } else {
                            System.out.println("Congratulations! " + player2Name + " wins! at step " + (player - 1));
                        }
                    }
                } catch (Exception e) {
                    System.out.println("An error occurred while printing the winner message: " + e.getMessage());
                }
            } else if (checkDraw()) {
                try {
                    System.out.println("It's a draw!");
                } catch (Exception e) {
                    System.out.println("An error occurred while printing the draw message: " + e.getMessage());
                }
            } else {
                try {
                    System.out.println("Something went wrong!");
                } catch (Exception e) {
                    System.out.println("An error occurred while printing the error message: " + e.getMessage());
                }
            }

            // Prompt the player to play again or quit
            boolean validResponse = false;
            while (!validResponse) {
                System.out.print("Do you want to play again? (Y/N): ");
                String response = sc.next();
                if (response.equalsIgnoreCase("Y")) {
                    validResponse = true;
                } else if (response.equalsIgnoreCase("N")) {
                    validResponse = true;
                    playAgain = false;
                    System.out.println("Thanks for playing! Goodbye.");
                } else {
                    System.out.println("Invalid response. Please enter Y or N.");
                }
            }
        }

    }

    public static void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    public static void displayBoard() {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }

    public static void playerMove(String playerName, int player, char symbol) {
        boolean validInput = false;
        System.out.println("It's " + playerName + "'s turn (Step " + player + "). " + playerName + ", enter your move (row[1-3] column[1-3]):");
        while (!validInput) {
            try {
                int row = sc.nextInt() - 1;
                int col = sc.nextInt() - 1;
                if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == '-') {
                    board[row][col] = symbol;
                    validInput = true;
                } else if (row < 0 || row >= 3 || col < 0 || col >= 3) {
                    System.out.println("Invalid move, row and column should be between 1 and 3.");
                } else if (board[row][col] != '-') {
                    System.out.println("Invalid move, cell [" + (row + 1) + "," + (col + 1) + "] is already occupied.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please enter the correct number!");
                sc.nextLine(); // clear input buffer
            } catch (Exception e) {
                System.out.println("Something went wrong, please try again!");
                sc.nextLine(); // clear input buffer
            }
        }
    }


    public static void botMove(String botName, int player, char symbol, Difficulty difficulty) {
        System.out.println("It's " + botName + "'s turn (Step " + player + ").");

        // Check if bot can win in the next move
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    board[i][j] = symbol;
                    if (checkWin(symbol)) {
                        System.out.println(botName + " chooses row " + (i + 1) + ", column " + (j + 1) + ".");
                        return;
                    }
                    board[i][j] = '-';
                }
            }
        }

        // Check if player can win in the next move and block them
        char opponentSymbol = symbol == 'X' ? 'O' : 'X';
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    board[i][j] = opponentSymbol;
                    if (checkWin(opponentSymbol)) {
                        board[i][j] = symbol;
                        System.out.println(botName + " chooses row " + (i + 1) + ", column " + (j + 1) + ".");
                        return;
                    }
                    board[i][j] = '-';
                }
            }
        }

        // Play the center if available (always for hard mode, sometimes for medium mode)
        if (board[1][1] == '-') {
            board[1][1] = symbol;
            System.out.println(botName + " chooses row 2, column 2.");
            return;
        } else if (difficulty == Difficulty.MEDIUM && Math.random() == 0.5) {
            for (int i = 0; i < 3; i += 2) {
                for (int j = 0; j < 3; j += 2) {
                    if (board[i][j] == '-') {
                        board[i][j] = symbol;
                        System.out.println(botName + " chooses row " + (i + 1) + ", column " + (j + 1) + ".");
                        return;
                    }
                }
            }
        }

        // Play a random cell (for easy mode)
        if (difficulty == Difficulty.EASY) {
            int i, j;
            do {
                i = (int) (Math.random() * 3);
                j = (int) (Math.random() * 3);
            } while (board[i][j] != '-');
            board[i][j] = symbol;
            System.out.println(botName + " chooses row " + (i + 1) + ", column " + (j + 1) + ".");
            return;
        }

        // Play a random corner (sometimes for medium mode, always for hard mode if no better move is available)
        int[][] corners = {{0, 0}, {0, 2}, {2, 0}, {2, 2}};
        if (difficulty == Difficulty.HARD || (difficulty == Difficulty.MEDIUM && Math.random() < 0.5)) {
            for (int i = 0; i < corners.length; i++) {
                int[] corner = corners[i];
                if (board[corner[0]][corner[1]] == '-') {
                    board[corner[0]][corner[1]] = symbol;
                    System.out.println(botName + " chooses row " + (corner[0] + 1) + ", column " + (corner[1] + 1) + ".");
                    return;
                }
            }
        }

        // Play a random empty cell (should not happen in hard mode or medium mode)
        int i, j;
        do {
            i = (int) (Math.random() * 3);
            j = (int) (Math.random() * 3);
        } while (board[i][j] != '-');
        board[i][j] = symbol;
        System.out.println(botName + " chooses row " + (i + 1) + ", column " + (j + 1) + ".");
    }

    public static boolean checkWin(char symbol) {
        try {
            // Check rows
            for (int i = 0; i < 3; i++) {
                if (board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) {
                    return true;
                }
            }
            // Check columns
            for (int j = 0; j < 3; j++) {
                if (board[0][j] == symbol && board[1][j] == symbol && board[2][j] == symbol) {
                    return true;
                }
            }
            // Check diagonals
            if (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) {
                return true;
            }
            if (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Something went wrong while checking for a win: " + e.getMessage());
            return false;
        }
    }

    public static boolean checkDraw() {
        try {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        return false;
                    }
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println("Something went wrong while checking for a draw: " + e.getMessage());
            return false;
        }
    }



}