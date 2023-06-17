package com.Suzume;

import java.sql.SQLOutput;
import java.util.*;

public class ReversePvP {
    static class Move
    {
        int row, col;
    };

    private static String aiPlayer = "X";
    private static String opponent = "O";
    public static int status;
    private Stack<Integer> slots = new Stack<>();

    private String[][] board = {{"", "", ""}, {"", "", ""}, {"", "", ""}};

    //Start Game
    public void game (String mode){

        Scanner sc = new Scanner (System.in);

        //Greeting
        greet();

        //Enter the game
        System.out.print("Type 'Y' to start the game: ");
        String reply = sc.next();
        while(!reply.equalsIgnoreCase("Y")){
            System.out.print("Please type 'Y' to continue the game: ");
            reply = sc.next();
        }

        System.out.println();
        System.out.println("Let's start the game ~~");
        System.out.println("----------------------------------------------");

        //Game mode
        System.out.print("Current game mode: " + mode + "\n");
        //Player go first or later?
        System.out.print("Would you want to go first? (Y/N): ");
        reply = sc.next();
        System.out.println();

        if(reply.equalsIgnoreCase("Y")){
            printBoard(board);
            while (true){
                //Players Turn
                System.out.println("Player's Turn ~~");
                if(slots.size()>=2){
                    System.out.print("Do you want to take back move(Y/N): ");
                    reply = sc.next();
                    if (reply.equalsIgnoreCase("Y")){
                        takeBackMove();
                        printBoard(board);
                    }
                }
                System.out.print("Enter the position (1-9): ");
                int move = sc.nextInt();
                while(move<=0 || move>9){
                    System.out.print("Please enter a number in the range (1-9)");
                    move = sc.nextInt();
                }
                while(slots.contains(move)){
                    System.out.print("The position has been taken. Please enter another number: ");
                    move = sc.nextInt();
                }
                slots.push(move);
                placePiece(move);
                printBoard(board);
                if(winningGame()){
                    break;
                }

                System.out.println();

                //CPU Turn
                System.out.println("CPU's Turn ~~");
                Move bestMove = new Move();
                bestMove = findBestMove(board, mode);
                aiMakeMove(bestMove);
                printBoard(board);
                if(winningGame()){
                    break;
                }
                System.out.println();
            }

        }else{
            while (true){
                //CPU Turn
                System.out.println("CPU's Turn ~~ ");
                Move bestMove = new Move();
                bestMove = findBestMove(board, mode);
                aiMakeMove(bestMove);
                printBoard(board);
                if(winningGame()){
                    break;
                }

                System.out.println();

                //Players Turn
                System.out.println("Player's Turn ~~");
                if(slots.size()>=2){
                    System.out.print("Do you want to take back move(Y/N): ");
                    reply = sc.next();
                    if (reply.equalsIgnoreCase("Y")){
                        takeBackMove();
                        printBoard(board);
                    }
                }
                System.out.print("Enter the position (1-9): ");
                int move = sc.nextInt();
                while(move<=0 || move>9){
                    System.out.print("Please enter a number in the range (1-9)");
                    move = sc.nextInt();
                }
                while(slots.contains(move)){
                    System.out.print("The position has been taken. Please enter another number: ");
                    move = sc.nextInt();
                }
                slots.push(move);
                placePiece(move);
                printBoard(board);
                if(winningGame()){
                    break;
                }
                System.out.println();
            }
        }

        if(status==1){
            System.out.println("Congrats! You won! ");
        }
        else if (status==-1){
            System.out.println("Oh No! You lost! ");
        }
        else if(status==0){
            System.out.println("TIE! Let's get another round.");
            reset();
            game(mode);
        }
    }

    public void printBoard(String[][] board){
        System.out.println("-------------");
        for(int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                if(board[i][j].isEmpty()){
                    System.out.print("| " + "  ");
                }
                else {
                    System.out.print("| " + board[i][j] + " ");
                }
            }
            System.out.println("|");
            System.out.println("-------------");
        }
    }

    public void placePiece(int move){
        switch (move){
            case 1 -> board[0][0] = "O";
            case 2 -> board[0][1] = "O";
            case 3 -> board[0][2] = "O";
            case 4 -> board[1][0] = "O";
            case 5 -> board[1][1] = "O";
            case 6 -> board[1][2] = "O";
            case 7 -> board[2][0] = "O";
            case 8 -> board[2][1] = "O";
            case 9 -> board[2][2] = "O";
        }
    }
    public void greet(){
        System.out.println("Welcome to Reverse/Misere Tic Tac Toe ~~");
        System.out.println("---------------------------------------------");
        System.out.println("Rule of the Game: ");
        System.out.println("1. To win the game, you have to avoid yourself from placing " +
                "in a row or column or diagonal.");
        System.out.println("2. The location is based on the number 1-9. For example, number 4 " +
                "will be in 2nd row 1st column and number 8 in 3rd row 2nd column.");
        System.out.println();
    }

    // This function returns true if there are moves
    // remaining on the board. It returns false if
    // there are no moves left to play.
    private Boolean isMovesLeft(String board[][])
    {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j].isBlank())
                    return true;
        return false;
    }

    // This is the evaluation function as discussed
    // in the previous article ( http://goo.gl/sJgv68 )
    private int evaluate(String b[][])
    {
        // Checking for Rows for X or O victory.
        for (int row = 0; row < 3; row++)
        {
            if (b[row][0].equalsIgnoreCase(b[row][1]) &&
                    b[row][1].equalsIgnoreCase(b[row][2]))
            {
                if (b[row][0].equalsIgnoreCase(aiPlayer))
                    return -10;
                else if (b[row][0].equalsIgnoreCase(opponent))
                    return +10;
            }
        }

        // Checking for Columns for X or O victory.
        for (int col = 0; col < 3; col++)
        {
            if (b[0][col].equalsIgnoreCase(b[1][col]) &&
                    b[1][col].equalsIgnoreCase(b[2][col]))
            {
                if (b[0][col].equalsIgnoreCase(aiPlayer))
                    return -10;

                else if (b[0][col].equalsIgnoreCase(opponent))
                    return +10;
            }
        }

        // Checking for Diagonals for X or O victory.
        if (b[0][0].equalsIgnoreCase(b[1][1]) && b[1][1].equalsIgnoreCase(b[2][2]))
        {
            if (b[0][0].equalsIgnoreCase(aiPlayer))
                return -10;
            else if (b[0][0].equalsIgnoreCase(opponent))
                return +10;
        }

        if (b[0][2].equalsIgnoreCase(b[1][1]) && b[1][1].equalsIgnoreCase(b[2][0]))
        {
            if (b[0][2].equalsIgnoreCase(aiPlayer))
                return -10;
            else if (b[0][2].equalsIgnoreCase(opponent))
                return +10;
        }

        // Else if none of them have won then return 0
        return 0;
    }

    // This is the minimax function. It considers all
    // the possible ways the game can go and returns
    // the value of the board
    private int minimax(String board[][],
                        int depth, Boolean isMax)
    {
        int score = evaluate(board);

        // If Maximizer has won the game
        // return his/her evaluated score
        if (score == 10)
            return score;

        // If Minimizer has won the game
        // return his/her evaluated score
        if (score == -10)
            return score;

        // If there are no more moves and
        // no winner then it is a tie
        if (isMovesLeft(board) == false)
            return 0;

        // If this maximizer's move
        if (isMax)
        {
            int best = -1000;

            // Traverse all cells
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    // Check if cell is empty
                    if (board[i][j].isBlank())
                    {
                        // Make the move
                        board[i][j] = aiPlayer;

                        // Call minimax recursively and choose
                        // the maximum value
                        best = Math.max(best, minimax(board,
                                depth + 1, !isMax));

                        // Undo the move
                        board[i][j]="";
                    }
                }
            }
            return best;
        }

        // If this minimizer's move
        else
        {
            int best = 1000;

            // Traverse all cells
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    // Check if cell is empty
                    if (board[i][j].isBlank())
                    {
                        // Make the move
                        board[i][j] = opponent;

                        // Call minimax recursively and choose
                        // the minimum value
                        best = Math.min(best, minimax(board,
                                depth + 1, !isMax));

                        // Undo the move
                        board[i][j] = "";
                    }
                }
            }
            return best;
        }
    }

    private int getRandom(String mode){
        ArrayList<Integer> list = new ArrayList<>();
        if(mode.equalsIgnoreCase("hard")){
            list.addAll(Arrays.asList(1,1,1,1,1,1,1,1,1,-1));
        }
        else if (mode.equalsIgnoreCase("medium")){
            list.addAll(Arrays.asList(1,1,1,1,1,-1,-1,-1,-1,-1));
        }
        else {
            list.addAll(Arrays.asList(-1,-1,-1,-1,-1,-1,-1,-1,1,-1));
        }
        int a = new Random().nextInt(list.size());
        return list.get(a);
    }

    // This will return the best possible
    // move for the player
    private Move findBestMove(String board[][], String mode)
    {
        int bestVal = -1000;
        Move bestMove = new Move();
        bestMove.row = -1;
        bestMove.col = -1;

        // Traverse all cells, evaluate minimax function
        // for all empty cells. And return the cell
        // with optimal value.
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                // Check if cell is empty
                if (board[i][j].isBlank())
                {
                    // Make the move
                    board[i][j] = aiPlayer;

                    // compute evaluation function for this
                    // move.
                    int moveVal = minimax(board, 0, false);
                    moveVal *= getRandom(mode);

                    // Undo the move
                    board[i][j] = "";

                    // If the value of the current move is
                    // more than the best value, then update
                    // best/
                    if (moveVal > bestVal)
                    {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }

        //System.out.printf("The value of the best Move " +
        //        "is : %d\n\n", bestVal);

        return bestMove;
    }

    private void aiMakeMove(Move bestMove){
        if(bestMove.row == 0 && bestMove.col == 0){
            board[0][0] = aiPlayer;
            slots.push(1);
            winningGame();
        }
        else if (bestMove.row == 0 && bestMove.col == 1){
            board[0][1] = aiPlayer;
            slots.push(2);
            winningGame();
        }
        else if (bestMove.row == 0 && bestMove.col == 2){
            board[0][2] = aiPlayer;
            slots.push(3);
            winningGame();
        }
        else if (bestMove.row == 1 && bestMove.col == 0){
            board[1][0] = aiPlayer;
            slots.push(4);
            winningGame();
        }
        else if (bestMove.row == 1 && bestMove.col == 1){
            board[1][1] = aiPlayer;
            slots.push(5);
            winningGame();
        }
        else if (bestMove.row == 1 && bestMove.col == 2){
            board[1][2] = aiPlayer;
            slots.push(6);
            winningGame();
        }
        else if (bestMove.row == 2 && bestMove.col == 0){
            board[2][0] = aiPlayer;
            slots.push(7);
            winningGame();
        }
        else if (bestMove.row == 2 && bestMove.col == 1){
            board[2][1] = aiPlayer;
            slots.push(8);
            winningGame();
        }
        else if (bestMove.row == 2 && bestMove.col == 2){
            board[2][2] = aiPlayer;
            slots.push(9);
            winningGame();
        }
    }

    public boolean winningGame(){
        String temp = "";

        //checking row
        for(int row=0; row<3; row++){
            if((board[row][0].equals("X") && board[row][1].equals("X") && board[row][2].equals("X"))
                    ||(board[row][0].equals("O") && board[row][1].equals("O") && board[row][2].equals("O"))){

                temp = board[row][0];

                if(temp.equals("X")){
                    status = 1;
                }else{
                    status = -1;
                }
                return true;
            }
        }

        //Checking columns
        for(int col=0; col<3; col++){
            if((board[0][col].equals("X") && board[1][col].equals("X") && board[2][col].equals("X"))
                    ||(board[0][col].equals("O") && board[1][col].equals("O") && board[2][col].equals("O"))){

                temp = board[0][col];


                if(temp.equals("X")){
                    status = 1;
                }else{
                    status = -1;
                }
                return true;
            }
        }

        //Checking diagonals
        if ((board[0][0].equals("X") && board[1][1].equals("X") && board[2][2].equals("X"))
                || (board[0][0].equals("O") && board[1][1].equals("O") && board[2][2].equals("O"))){
            temp = board[0][0];

            if(temp.equals("X")){
                status = 1;
            }else{
                status = -1;
            }
            return true;
        }

        if ((board[0][2].equals("X") && board[1][1].equals("X") && board[2][0].equals("X"))
                || (board[0][2].equals("O") && board[1][1].equals("O") && board[2][0].equals("O"))){
            temp = board[0][2];

            if(temp.equals("X")){
                status = 1;

            }else{
                status = -1;
            }
            return true;
        }

        //Tie
        if(slots.size() == 9){
            status = 0;
            return true;
        }

        return false;
    }

    public int getStatus(){
        return status;
    }

    //Clear the board
    public void reset() {
        slots.removeAllElements();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = "";
            }
        }
    }

    public void removeMove(Integer pos){
        switch(pos){
            case 1:
                board[0][0] = "";
                break;
            case 2:
                board[0][1] = "";
                break;
            case 3:
                board[0][2] = "";
                break;
            case 4:
                board[1][0] = "";
                break;
            case 5:
                board[1][1] = "";
                break;
            case 6:
                board[1][2] = "";
                break;
            case 7:
                board[2][0] = "";
                break;
            case 8:
                board[2][1] = "";
                break;
            case 9:
                board[2][2] = "";
                break;
        }
    }

    public void takeBackMove(){
        if (slots.isEmpty()){
            System.out.println("You cannot take any move back.");
        }
        else{
            Integer pos = slots.pop();
            removeMove(pos);
            pos = slots.pop();
            removeMove(pos);
        }
    }



}
