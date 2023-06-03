package cli;

import java.util.*;


public class WildTicTacToe {
    private static int[][] board = new int[3][3];
    private static int currentPlayer = 1;
    private static boolean isGameOver = false;
    private static int winnerPlayer = 0;
    private static Stack<Integer> player1Moves = new Stack<>();
    private static Stack<Integer> player2Moves = new Stack<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Wild Tic Tac Toe!");
        System.out.println("The game is played on a 3x3 grid.");
        System.out.println("The objective is to place three X's or O's in a row.");

        System.out.print("Enter the name of player 1: ");
        String player1Name = scanner.nextLine();

        String player2Name;
        boolean isBotMode = false;
        System.out.print("Do you want to play against a bot? (Y/N): ");
        String botMode = scanner.next();
        String  difficultyLevel = "";
        if (botMode.equalsIgnoreCase("Y")) {
            player2Name = "Bot";
            isBotMode = true;
            System.out.println(player1Name + " will start as X and " + player2Name + " will start as O.");


            do {
                System.out.print("Select difficulty level (easy/medium/hard): ");
                difficultyLevel = scanner.next();
                if (!difficultyLevel.equalsIgnoreCase("easy") &&
                        !difficultyLevel.equalsIgnoreCase("medium") &&
                        !difficultyLevel.equalsIgnoreCase("hard")) {
                    System.out.println("Invalid difficulty level. Please try again.");
                }
            } while (!difficultyLevel.equalsIgnoreCase("easy") &&
                    !difficultyLevel.equalsIgnoreCase("medium") &&
                    !difficultyLevel.equalsIgnoreCase("hard"));

            System.out.println("You have selected difficulty level: " + difficultyLevel);

        } else {
            System.out.print("Enter the name of player 2: ");
            player2Name = scanner.next();
            System.out.println(player1Name + " will start as X and " + player2Name + " will start as O.");
        }

        System.out.println("You will take turns placing your X's or O's on the board.");
        System.out.println("To make a move, enter the row and column numbers where you want to place your X or O.");
        System.out.println("If you get a line of three X's or O's in a row, you win! Good luck!");
        System.out.println("Let's get started, " + player1Name + " and " + player2Name + "!");

        while (!isGameOver) {
            printBoard();

            String playerName;
            if (currentPlayer == 1) {
                playerName = player1Name;
            } else {
                playerName = isBotMode ? "Bot" : player2Name;
            }

            if (isBotMode && currentPlayer == 2) {
                System.out.println("Bot is thinking...");
                makeBotMove(difficultyLevel);
            } else {
                int row, col;
                do {
                    System.out.print(playerName + ", enter 'X' or 'O' to mark this square, or 'U' to undo your last move: ");
                    String mark = scanner.next();
                    if (mark.equalsIgnoreCase("U")) {
                        undoMove(); // Undo the last move
                        currentPlayer = currentPlayer == 1 ? 2 : 1; // Switch the current player back
                        break;
                    } else if (!mark.equalsIgnoreCase("X") && !mark.equalsIgnoreCase("O")) {
                        System.out.println("Invalid mark. Please enter 'X', 'O', or 'U' to undo your last move.");
                        continue;
                    }
                    System.out.print("Enter the row number (1-3): ");
                    row = scanner.nextInt() - 1;
                    System.out.print("Enter the column number (1-3): ");
                    col = scanner.nextInt() - 1;
                    if (row < 0 || row > 2 || col < 0 || col > 2) {
                        System.out.println("Invalid row or column number. Please enter a number between 1 and 3.");
                        continue;
                    }
                    if (board[row][col] != 0) {
                        System.out.println("Square already taken. Please choose another square.");
                        continue;
                    }
                    board[row][col] = mark.equalsIgnoreCase("X") ? 1 : 2;

                    // Update the stacks with the player's move
                    if (currentPlayer == 1) {
                        player1Moves.push(row * 3 + col);
                    } else {
                        player2Moves.push(row * 3 + col);
                    }
                    break;
                } while (true);
            }

            try {
                if (checkWinner()) {
                    printBoard();
                    isGameOver = true;
                    winnerPlayer = currentPlayer; // Update the winnerPlayer variable
                } else if (isBoardFull()) {
                    printBoard();
                    System.out.println("The game is a draw.");
                    isGameOver = true;
                } else {
                    // Switch the current player if no winner and the board is not full
                    currentPlayer = currentPlayer == 1 ? 2 : 1;
                }
            } catch (Exception e) {
                // Handle any exceptions that may occur
                System.out.println("An error occurred: " + e.getMessage());
                isGameOver = true;
            }
        }

        // Print the winner message
        if (winnerPlayer == 1) {
            System.out.println(player1Name + " wins!");
        } else if (winnerPlayer == 2) {
            System.out.println(isBotMode ? "Bot wins!" : player2Name + " wins!");
        } else if (isBoardFull()) {
            System.out.println("The game is a draw.");
        }


        System.out.print("Do " + player1Name + " and " + (isBotMode ? "Bot" : player2Name) + " want to play again? (Y/N): ");
        String playAgain = scanner.next();
        if (playAgain.equalsIgnoreCase("Y")) {
            resetGame();
            main(args);
        } else {
            System.out.println("Thank you for playing Wild Tic Tac Toe!");
        }
    }

    private static void printBoard() {
        System.out.println("-------------");
        for (int row = 0; row < 3; row++) {
            System.out.print("| ");
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == 0) {
                    System.out.print("  | ");
                } else {
                    String mark = (board[row][col] == 1) ? "X" : "O";
                    System.out.print(mark + " | ");
                }
            }
            System.out.println();
            System.out.println("-------------");
        }
    }

    private static boolean checkWinner() {
        int winner = 0;

        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != 0) {
                winner = board[i][0];
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != 0) {
                winner = board[0][i];
            }
        }

        // Check diagonals
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != 0) {
            winner = board[0][0];
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != 0) {
            winner = board[0][2];
        }

        if (winner != 0) {
            isGameOver = true;
            winnerPlayer = winner;
            return true;
        }

        return false;
    }



    private static void resetGame() {
        board = new int[3][3];
        currentPlayer = 1;
        isGameOver = false;
        winnerPlayer = 0;
        // Clear the move stacks
        player1Moves.clear();
        player2Moves.clear();
    }

    private static void makeBotMove(String difficultyLevel) {
        Random random = new Random();

        // Generate X or O randomly
        int botMark = random.nextInt(2) + 1;
        String markSymbol = (botMark == 1) ? "X" : "O";
        System.out.println("Bot chooses " + markSymbol + ".");

        // Select move based on difficulty level
        int row, col;
        if (difficultyLevel.equalsIgnoreCase("easy")) {
            // Easy mode: Randomly pick an empty cell
            do {
                row = random.nextInt(3);
                col = random.nextInt(3);
            } while (board[row][col] != 0);
        } else if (difficultyLevel.equalsIgnoreCase("medium")) {
            // Medium mode: Use Minimax algorithm with depth 3
            Move bestMove = minimax(3, botMark);
            row = bestMove.row;
            col = bestMove.col;
        } else if (difficultyLevel.equalsIgnoreCase("hard")) {
            // Hard mode: Use Minimax algorithm with depth 5
            Move bestMove = minimax(5, botMark);
            row = bestMove.row;
            col = bestMove.col;
        } else {
            // Invalid difficulty level
            System.out.println("Invalid difficulty level. Bot will make a random move.");
            do {
                row = random.nextInt(3);
                col = random.nextInt(3);
            } while (board[row][col] != 0);
        }

        // Place the mark in the chosen cell
        board[row][col] = botMark;
        System.out.println("Bot placed " + markSymbol + " at row " + (row + 1) + ", column " + (col + 1) + ".");

        // Update the stack with the bot's move
        player2Moves.push(row * 3 + col);


    }

    private static Move minimax(int depth, int player) {
        List<Move> availableMoves = getAvailableMoves();

        // Terminal conditions: check for winner or draw
        if (checkWinner()) {
            int score = (currentPlayer == player) ? 1 : -1;
            return new Move(score);
        } else if (isBoardFull()) {
            return new Move(0);
        }

        List<Move> moves = new ArrayList<>();

        for (Move move : availableMoves) {
            // Make the move
            board[move.row][move.col] = player;

            // Recursively call minimax to evaluate the move
            int score = minimax(depth - 1, 3 - player).score;
            move.score = score;

            // Undo the move
            board[move.row][move.col] = 0;

            moves.add(move);
        }

        // Find the best move based on the player's turn
        return (player == currentPlayer) ? Collections.max(moves) : Collections.min(moves);
    }

    private static List<Move> getAvailableMoves() {
        List<Move> moves = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            int row = i / 3;
            int col = i % 3;
            if (board[row][col] == 0) {
                moves.add(new Move(row, col));
            }
        }

        return moves;
    }

    private static boolean isBoardFull() {
        for (int[] row : board) {
            for (int cell : row) {
                if (cell == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    static class Move implements Comparable<Move> {
        int row, col, score;

        public Move(int score) {
            this.score = score;
        }

        public Move(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public int compareTo(Move otherMove) {
            return Integer.compare(score, otherMove.score);
        }
    }

    private static void undoMove() {
        if (currentPlayer == 1 && !player1Moves.isEmpty()) {
            int lastMove = player1Moves.pop();
            int row = lastMove / 3;
            int col = lastMove % 3;
            board[row][col] = 0;
        } else if (currentPlayer == 2 && !player2Moves.isEmpty()) {
            int lastMove = player2Moves.pop();
            int row = lastMove / 3;
            int col = lastMove % 3;
            board[row][col] = 0;
        }
    }

}
