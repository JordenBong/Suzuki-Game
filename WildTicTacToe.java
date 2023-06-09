package cli;


import java.util.*;


public class WildTicTacToe {
    private static int[][] board = new int[3][3];
    private static int currentPlayer = 1;
    private static boolean isGameOver = false;
    private static int winnerPlayer = 0;
    private static final Stack<Integer> player1Moves = new Stack<>();
    private static final Stack<Integer> player2Moves = new Stack<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        gameStartInfo();

        System.out.print("Enter your name, player 1: ");

        String player1Name = scanner.nextLine();
        String player2Name = "Bot";

        System.out.println(player1Name + " will start as X and " + player2Name + " will start as O.");

        System.out.print("Select the difficulty level for the bot (easy-1,medium-2,hard-3): ");
        int difficultyLevel = scanner.nextInt();
        System.out.println(player1Name + " will start as X and the bot will start as O.");

        gameInstruction(player1Name, player2Name);

        while (!isGameOver) {
            playGame(true, player1Name, player2Name, difficultyLevel);
        }

        printWinnerMessage(player1Name, player2Name, true, winnerPlayer);

        printGameOutcome(player1Name, player2Name, true, winnerPlayer);

        playerVsPlayer(player1Name);

        playAgain(player1Name, args);

    }

    // prints the initial instructions and information about the game.
    public static void gameStartInfo(){
        System.out.println("Welcome to Wild Tic Tac Toe!");
        System.out.println("The game is played on a 3x3 grid.");
        System.out.println("The objective is to place three X's or O's in a row.");
        System.out.println("You will play with Bot. After the game with Bot, you can play with another player for fun.");
    }

    // prints the instructions for the game.
    public static void gameInstruction(String player1Name, String player2Name){
        System.out.println("You will take turns placing your X's or O's on the board.");
        System.out.println("To make a move, enter the row and column numbers where you want to place your X or O.");
        System.out.println("If you get a line of three X's or O's in a row, you win! Good luck!");
        System.out.println("Let's get started, " + player1Name + " and " + player2Name + "!");
    }

    /*
    handles the gameplay logic. It alternates between players, takes input for moves, and updates the game board accordingly.
    If playing against a bot, it waits for the bot's move. It also checks for a winner or a draw after each move.
     */
    public static void playGame(boolean isBotMode, String player1Name, String player2Name, int difficultyLevel) {
        printBoard();

        String playerName;
        if (currentPlayer == 1) {
            playerName = player1Name;
        } else {
            playerName = isBotMode ? "Bot" : player2Name;
        }

        if (isBotMode && currentPlayer == 2) {
            System.out.println("Bot is thinking...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
            makeBotMove(difficultyLevel);
        } else {
            int row, col;
            boolean validMove = false;
            do {
                System.out.print(playerName + ", enter 'X' or 'O' to mark this square, or 'U' to undo your last move: ");
                Scanner sc = new Scanner(System.in);
                String mark = sc.next();
                if (mark.equalsIgnoreCase("U")) {
                    undoMove(); // Undo the last move
                    currentPlayer = currentPlayer == 1 ? 2 : 1; // Switch the current player back
                    break;
                } else if (!mark.equalsIgnoreCase("X") && !mark.equalsIgnoreCase("O")) {
                    System.out.println("Invalid mark. Please enter 'X', 'O', or 'U' to undo your last move.");
                    continue;
                }
                try {
                    System.out.print("Enter the row number (1-3): ");
                    row = sc.nextInt() - 1;

                    System.out.print("Enter the column number (1-3): ");
                    col = sc.nextInt() - 1;

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
                    validMove = true;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number between 1 and 3.");
                    sc.nextLine(); // Clear the invalid input from the scanner
                    continue;
                }
            } while (!validMove);
        }

        try {
            if (checkWinner()) {
                printBoard();
                isGameOver = true;
                winnerPlayer = currentPlayer; // Update the winnerPlayer variable
            } else if (isBoardFull()) {
                printBoard();
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

    // prints a message declaring the winner of the game
    public static void printWinnerMessage(String player1Name, String player2Name, boolean isBotMode, int winnerPlayer) {
        if (winnerPlayer == 1) {
            System.out.println(player1Name + " wins!");
        } else if (winnerPlayer == 2) {
            System.out.println(isBotMode ? "Bot wins!" : player2Name + " wins!");
        } else {
            System.out.println("The game is a draw.");
        }
    }

    // determines the outcome of the game (win, loss, or draw) and prints an appropriate message
    public static void printGameOutcome(String player1Name, String player2Name, boolean isBotMode, int winnerPlayer){
        int gameOutcome;
        // Determine the game outcome
        gameOutcome = determineGameOutcome(isBotMode, winnerPlayer);

        if (gameOutcome == 1) {
            System.out.println("Congratulations, " + player1Name + "! You won the game! You can make your next move now.");
        } else if (gameOutcome == 0) {
            System.out.println("Sorry, " + player1Name + ". You lost the game. You have to back to the previous station.");
        } else if (gameOutcome == -1) {
            System.out.println("Better luck next time!");
        }
    }

    // determine the game outcome based on the winner player and the game mode
    public static int determineGameOutcome(boolean isBotMode, int winnerPlayer) {
        if (winnerPlayer == 1) {
            return 1; // Player 1 wins
        } else if (winnerPlayer == 2 && isBotMode) {
            return 0; // Bot wins
        } else {
            return -1; // Draw
        }
    }

    // prompts the first player to play against another human player if desired
    public static void playerVsPlayer(String player1Name){
        System.out.print("Do " + player1Name + " want to play Player vs. Player? (Y/N): ");
        Scanner scanner = new Scanner(System.in);
        String playAgainstPlayer = scanner.next();
        if (playAgainstPlayer.equalsIgnoreCase("Y")) {
            // Reset the game and play in Player vs. Player mode
            resetGame();

            System.out.print("Enter your name, player 2: ");
            String player2Name = scanner.next();
            System.out.println(player1Name + " will start as X and " + player2Name + " will start as O.");

            System.out.println("You will take turns placing your X's or O's on the board.");
            System.out.println("To make a move, enter the row and column numbers where you want to place your X or O.");
            System.out.println("If you get a line of three X's or O's in a row, you win! Good luck!");
            System.out.println("Let's get started, " + player1Name + " and " + player2Name + "!");

            while (!isGameOver) {
                int difficultyLevel=0;
                playGame(false, player1Name, player2Name, difficultyLevel);
            }
            printWinnerMessage(player1Name, player2Name, false, winnerPlayer);

        }
    }

    // asks the first player if they want to play again with the bot
    public static void playAgain(String player1Name, String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do " + player1Name + " want to play again with Bot? (Y/N): ");
        String playAgain = scanner.next();
        if (playAgain.equalsIgnoreCase("Y")) {
            resetGame();
            main(args);
        } else {
            System.out.println("Thank you for playing Wild Tic Tac Toe!");
        }
    }

    // prints the current state of the game board.
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

    // checks if there is a winner on the current game board by examining rows, columns, and diagonals.
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

    // resets the game state, including the game board, current player, game over status, and move stacks.
    private static void resetGame() {
        board = new int[3][3];
        currentPlayer = 1;
        isGameOver = false;
        winnerPlayer = 0;
        // Clear the move stacks
        player1Moves.clear();
        player2Moves.clear();
    }

    // handles the bot's move based on the selected difficulty level. It randomly chooses a move if the difficulty level is invalid.
    private static void makeBotMove(int difficultyLevel) {
        int currentPlayerMark = new Random().nextInt(2) + 1;

        switch (difficultyLevel) {
            case 1 -> makeSmartMove(currentPlayerMark);
            case 2 -> makeAdvancedMove(currentPlayerMark);
            case 3 -> makeHardMove(currentPlayerMark);
            default -> {
                System.out.println("Invalid difficulty level. Bot will make a random move.");
                makeRandomMove(currentPlayerMark);
            }
        }
    }

    private static void makeRandomMove(int currentPlayerMark) {
        Random random = new Random();

        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (board[row][col] != 0);

        makeMove(row, col, currentPlayerMark);
    }

    // implements a strategy for the bot to find a winning move or block the opponent's winning move
    private static void makeSmartMove(int currentPlayerMark) {
        // Look for a winning move
        if (findWinningMove(currentPlayerMark)) {
            return;
        }

        // Block the opponent's winning move
        if (findWinningMove(3 - currentPlayerMark)) {
            return;
        }

        // Make a random move
        makeRandomMove(currentPlayerMark);
    }

    // Difficulty level easy checks if there is a winning move for a given player by examining rows, columns, and diagonals
    private static boolean findWinningMove(int player) {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == 0) {
                makeMove(i, 2, player);
                return true;
            }
            if (board[i][0] == player && board[i][2] == player && board[i][1] == 0) {
                makeMove(i, 1, player);
                return true;
            }
            if (board[i][1] == player && board[i][2] == player && board[i][0] == 0) {
                makeMove(i, 0, player);
                return true;
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == player && board[1][i] == player && board[2][i] == 0) {
                makeMove(2, i, player);
                return true;
            }
            if (board[0][i] == player && board[2][i] == player && board[1][i] == 0) {
                makeMove(1, i, player);
                return true;
            }
            if (board[1][i] == player && board[2][i] == player && board[0][i] == 0) {
                makeMove(0, i, player);
                return true;
            }
        }

        // Check diagonals
        if (board[0][0] == player && board[1][1] == player && board[2][2] == 0) {
            makeMove(2, 2, player);
            return true;
        }
        if (board[0][0] == player && board[2][2] == player && board[1][1] == 0) {
            makeMove(1, 1, player);
            return true;
        }
        if (board[1][1] == player && board[2][2] == player && board[0][0] == 0) {
            makeMove(0, 0, player);
            return true;
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == 0) {
            makeMove(2, 0, player);
            return true;
        }
        if (board[0][2] == player && board[2][0] == player && board[1][1] == 0) {
            makeMove(1, 1, player);
            return true;
        }
        if (board[1][1] == player && board[2][0] == player && board[0][2] == 0) {
            makeMove(0, 2, player);
            return true;
        }

        return false;
    }

    // Difficulty level medium
    private static void makeAdvancedMove(int currentPlayerMark) {
        int bestScore = Integer.MIN_VALUE+1;
        int bestRow = -1;
        int bestCol = -1;

        // Try every possible move and evaluate the score using the minimax algorithm
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    board[i][j] = currentPlayerMark;
                    int score = minimax(9, false, currentPlayerMark);
                    board[i][j] = 0;

                    if (score > bestScore) {
                        bestScore = score;
                        bestRow = i;
                        bestCol = j;
                    }
                }
            }
        }

        // Make the best move found
        makeMove(bestRow, bestCol, currentPlayerMark);
    }

    // Minimax algorithm
    private static int minimax(int depth, boolean isMaximizingPlayer, int currentPlayerMark) {
        int result = evaluateBoard();

        if (result != 0) {
            return result;
        }

        int bestScore;
        if (isMaximizingPlayer) {
            bestScore = Integer.MIN_VALUE;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = currentPlayerMark;
                        int score = minimax(depth + 1, false, currentPlayerMark);
                        board[i][j] = 0;

                        bestScore = Math.max(score, bestScore);
                    }
                }
            }

        } else {
            bestScore = Integer.MAX_VALUE;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = 3 - currentPlayerMark;
                        int score = minimax(depth + 1, true, currentPlayerMark);
                        board[i][j] = 0;

                        bestScore = Math.min(score, bestScore);
                    }
                }
            }

        }
        return bestScore;
    }

    private static final int MAX_DEPTH = 5;

    // Difficulty level hard - minimax with alpha-beta pruning
    private static void makeHardMove(int currentPlayerMark) {
        int bestScore = Integer.MIN_VALUE;
        int bestRow = -1;
        int bestCol = -1;

        // Try every possible move and evaluate the score using the minimax algorithm with alpha-beta pruning
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    board[i][j] = currentPlayerMark;
                    int score = alphaBetaPruning(0, Integer.MIN_VALUE, Integer.MAX_VALUE, false, currentPlayerMark);
                    board[i][j] = 0;

                    if (score > bestScore) {
                        bestScore = score;
                        bestRow = i;
                        bestCol = j;
                    }
                }
            }
        }

        // Make the best move found
        makeMove(bestRow, bestCol, currentPlayerMark);
    }

    // Alpha-beta pruning algorithm
    private static int alphaBetaPruning (int depth, int alpha, int beta, boolean isMaximizingPlayer, int currentPlayerMark) {
        int result = evaluateBoard();

        if (result != 0) {
            return result * depth; // Scale the score by the depth to encourage faster wins and slower losses
        }

        if (depth >= MAX_DEPTH) {
            return 0; // Limit the search depth to avoid excessive computation
        }

        if (isMaximizingPlayer) {
            int maxScore = Integer.MIN_VALUE;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = currentPlayerMark;
                        int score = alphaBetaPruning(depth + 1, alpha, beta, false, currentPlayerMark);
                        board[i][j] = 0;

                        maxScore = Math.max(score, maxScore);
                        alpha = Math.max(alpha, score);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }

            return maxScore;
        } else {
            int minScore = Integer.MAX_VALUE;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = 3 - currentPlayerMark;
                        int score = alphaBetaPruning(depth + 1, alpha, beta, true, currentPlayerMark);
                        board[i][j] = 0;

                        minScore = Math.min(score, minScore);
                        beta = Math.min(beta, score);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }

            return minScore;
        }
    }

    // Evaluate the current state of the board
    private static int evaluateBoard() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                if (board[i][0] == currentPlayer) {
                    return 10; // Current player has won
                } else if (board[i][0] == 3 - currentPlayer) {
                    return -10; // Opponent has won
                }
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                if (board[0][i] == currentPlayer) {
                    return 10; // Current player has won
                } else if (board[0][i] == 3 - currentPlayer) {
                    return -10; // Opponent has won
                }
            }
        }

        // Check diagonals
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == currentPlayer) {
                return 10; // Current player has won
            } else if (board[0][0] == 3 - currentPlayer) {
                return -10; // Opponent has won
            }
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] == currentPlayer) {
                return 10; // Current player has won
            } else if (board[0][2] == 3 - currentPlayer) {
                return -10; // Opponent has won
            }
        }

        return 0; // Game is a draw or not yet finished
    }

    // Make the move and the position on the board where the player wants to place their mark is passed as a parameter
    private static void makeMove(int row, int col, int currentPlayerMark) {
        // Set the current player's mark on the board
        board[row][col] = currentPlayerMark;

        if (currentPlayerMark == 1) {
            player1Moves.push(row * 3 + col);
        } else {
            player2Moves.push(row * 3 + col);
        }

        System.out.println("Bot has made its move at row " + (row + 1) + ", column " + (col + 1) + ".");
    }

    // Check if the board is full
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

    // Undo the last move made by a player in the game
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
