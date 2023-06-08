import org.w3c.dom.ls.LSOutput;

import java.util.Scanner;
import java.util.Stack;

public class ReverseFvF extends ReversePvP{
    private static String playerX = "X";
    private static String playerO = "O";
    public int status;
    private Stack<Integer> slots = new Stack<>();
    private String[][] board = {{"", "", ""}, {"", "", ""}, {"", "", ""}};
    private int xCount = 0;
    private int oCount = 0;
    Scanner sc = new Scanner(System.in);

    public boolean game(){
        //greet
        greet();

        //Ready?
        if(ready()){
            showScore();
            printBoard(board);

            //Player X Turn
            while (true) {
                System.out.println("Player X's Turn ~~");
                System.out.print("Choose a position (1-9): ");
                int pos = sc.nextInt();
                while (pos <= 0 || pos > 9) {
                    System.out.println("Please choose the number in the range (1-9): ");
                    pos = sc.nextInt();
                }
                while(slots.contains(pos)){
                    System.out.println("The position has been taken. Please choose another number: ");
                    pos = sc.nextInt();
                }
                placePiece(pos, "X");
                printBoard(board);
                if (winningGame()) {
                    break;
                }
                System.out.println();

                //Player O Turn
                System.out.println("Player O's Turn ~~");
                System.out.print("Choose a position (1-9): ");
                pos = sc.nextInt();
                while (pos <= 0 || pos > 9) {
                    System.out.println("Please choose the number in the range (1-9): ");
                    pos = sc.nextInt();
                }
                while(slots.contains(pos)){
                    System.out.println("The position has been taken. Please choose another number: ");
                    pos = sc.nextInt();
                }
                placePiece(pos, "O");
                printBoard(board);
                if (winningGame()) {
                    break;
                }
                System.out.println();
            }

            System.out.println();

            if(status == 1){
                System.out.println("Player X Won !!");
                xCount++;
            }else if(status == -1){
                System.out.println("Player O Won !!");
                oCount++;
            }
            else{
                System.out.println("TIE ~~");
            }
            showScore();
            System.out.print("Do you want to play again (Y/N): ");
            String ans = sc.next();
            if(ans.equalsIgnoreCase("Y")){
                reset();
                return game();
            }else{
                System.out.println("...Back to main mode...");
                System.out.println();
                return true;
            }
        }
        return true;
    }

    public void placePiece(int pos, String player){
        switch (pos){
            case 1 -> board[0][0] = player;
            case 2 -> board[0][1] = player;
            case 3 -> board[0][2] = player;
            case 4 -> board[1][0] = player;
            case 5 -> board[1][1] = player;
            case 6 -> board[1][2] = player;
            case 7 -> board[2][0] = player;
            case 8 -> board[2][1] = player;
            case 9 -> board[2][2] = player;
        }
    }

    public void showScore(){
        System.out.println("Player X Score: " + xCount);
        System.out.println("Player O Score: " + oCount);
        System.out.println();
    }
    public boolean ready(){
        System.out.print("Do you want to continue Friend Vs Friend mode (Y/N): ");
        String reply = sc.next();
        if(reply.equalsIgnoreCase("Y")){
            System.out.println("Let's Get Started !");
            System.out.println();
            return true;
        }else if(reply.equalsIgnoreCase("N")){
            System.out.println("...Back to main mode...");
            System.out.println();
            return false;
        }else{
            System.out.print("You have entered the wrong command. Please enter again: ");
            System.out.println();
            return ready();
        }
    }

    @Override
    public void greet(){
        System.out.println("Welcome to Reverse/Misere Tic Tac Toe: Friend Vs Friend ~~");
        System.out.println("-----------------------------------------------------------");
        System.out.println("Rule of the Game: ");
        System.out.println("1. To win the game, you have to avoid yourself from placing " +
                "in a row or column or diagonal.");
        System.out.println("2. The location is based on the number 1-9. For example, number 4 " +
                "will be in 2nd row 1st column and number 8 in 3rd row 2nd column.");
        System.out.println();
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
}
