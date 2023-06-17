package com.Suzume;

import java.util.*;

public class PlayerVsPlayer {
    static ArrayList<Integer> player1Positions = new ArrayList<>();
    static ArrayList<Integer> player2Positions = new ArrayList<>();
    static Stack<int[]> moveStack = new Stack<>();

    public void display2(){
        char[][] gameBoard = {{' ', '|', ' ', '|', ' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' ', '|', ' ', '|', ' '}};

        printGameBoard(gameBoard);

        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Player 1, Enter your placement (1-25): ");
            int player1Pos = sc.nextInt();

            while (player1Positions.contains(player1Pos) || player2Positions.contains(player1Pos)) {
                System.out.println("Position taken! Enter a correct Position!");
                player1Pos = sc.nextInt();
            }

            placePiece(gameBoard, player1Pos, "Player 1");
            moveStack.push(new int[]{player1Pos,1});
            printGameBoard(gameBoard);

            String result = checkWinner();
            if (result.length() > 0) {
                System.out.println(result);
                break;
            }
            System.out.println(result);

            System.out.print("Do you want to take back your move? (y/n): ");
            String takeBackChoice = sc.next();
            if (takeBackChoice.equalsIgnoreCase("y")) {
                if (!moveStack.isEmpty()) {
                    int[] lastMove = moveStack.pop();
                    int position = lastMove[0];

                    clearPosition(gameBoard, position);

                    player1Positions.remove(Integer.valueOf(position));

                    clearPosition(gameBoard, position);
                    printGameBoard(gameBoard);
                } else {
                    System.out.println("No moves to take back.");
                }
                System.out.print("Player 1, Enter your placement (1-25): ");
                player1Pos = sc.nextInt();

                while (player1Positions.contains(player1Pos) || player2Positions.contains(player1Pos)) {
                    System.out.println("Position taken! Enter a correct Position!");
                    player1Pos = sc.nextInt();
                }

                placePiece(gameBoard, player1Pos, "Player 1");
                moveStack.push(new int[]{player1Pos,1});
                printGameBoard(gameBoard);

                result = checkWinner();
                if (result.length() > 0) {
                    System.out.println(result);
                    break;
                }
                System.out.println(result);
            }

            System.out.print("Player 2, Enter your placement (1-25): ");
            int player2Pos = sc.nextInt();

            while (player1Positions.contains(player2Pos) || player2Positions.contains(player2Pos)) {
                System.out.println("Position taken! Enter a correct Position!");
                player2Pos = sc.nextInt();
            }

            placePiece(gameBoard, player2Pos, "Player 2");
            moveStack.push(new int[]{player2Pos,2});
            printGameBoard(gameBoard);

            result = checkWinner();
            if (result.length() > 0) {
                System.out.println(result);
                break;
            }
            System.out.println(result);

            System.out.print("Do you want to take back your move? (y/n): ");
            takeBackChoice = sc.next();
            if (takeBackChoice.equalsIgnoreCase("y")) {
                if (!moveStack.isEmpty()) {
                    int[] lastMove = moveStack.pop();
                    int position = lastMove[0];

                    clearPosition(gameBoard, position);

                    player2Positions.remove(Integer.valueOf(position));

                    clearPosition(gameBoard, position);
                    printGameBoard(gameBoard);
                } else {
                    System.out.println("No moves to take back.");
                }
                System.out.print("Player 2, Enter your placement (1-25): ");
                player2Pos = sc.nextInt();

                while (player1Positions.contains(player2Pos) || player2Positions.contains(player2Pos)) {
                    System.out.println("Position taken! Enter a correct Position!");
                    player2Pos = sc.nextInt();
                }

                placePiece(gameBoard, player2Pos, "Player 2");
                moveStack.push(new int[]{player2Pos,2});
                printGameBoard(gameBoard);

                result = checkWinner();
                if (result.length() > 0) {
                    System.out.println(result);
                    break;
                }
                System.out.println(result);
            }
        }
    }

    public static void clearPosition(char[][] gameBoard, int position) {
        switch(position){
            case 1:
                gameBoard[0][0] = ' ';
                break;
            case 2:
                gameBoard[0][2] = ' ';
                break;
            case 3:
                gameBoard[0][4] = ' ';
                break;
            case 4:
                gameBoard[0][6] = ' ';
                break;
            case 5:
                gameBoard[0][8] = ' ';
                break;
            case 6:
                gameBoard[2][0] = ' ';
                break;
            case 7:
                gameBoard[2][2] = ' ';
                break;
            case 8:
                gameBoard[2][4] = ' ';
                break;
            case 9:
                gameBoard[2][6] = ' ';
                break;
            case 10:
                gameBoard[2][8] = ' ';
                break;
            case 11:
                gameBoard[4][0] = ' ';
                break;
            case 12:
                gameBoard[4][2] = ' ';
                break;
            case 13:
                gameBoard[4][4] = ' ';
                break;
            case 14:
                gameBoard[4][6] = ' ';
                break;
            case 15:
                gameBoard[4][8] = ' ';
                break;
            case 16:
                gameBoard[6][0] = ' ';
                break;
            case 17:
                gameBoard[6][2] = ' ';
                break;
            case 18:
                gameBoard[6][4] = ' ';
                break;
            case 19:
                gameBoard[6][6] = ' ';
                break;
            case 20:
                gameBoard[6][8] = ' ';
                break;
            case 21:
                gameBoard[8][0] = ' ';
                break;
            case 22:
                gameBoard[8][2] = ' ';
                break;
            case 23:
                gameBoard[8][4] = ' ';
                break;
            case 24:
                gameBoard[8][6] = ' ';
                break;
            case 25:
                gameBoard[8][8] = ' ';
                break;
            default:
                break;
        }
    }

    public static void printGameBoard(char[][] gameBoard) {
        for (char[] row : gameBoard) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public static void placePiece(char[][] gameBoard,int position, String user){

        char symbol = ' ';
        if(user.equals("Player 1")){
            symbol = 'X';
            player1Positions.add(position);
        }else if(user.equals("Player 2")){
            symbol = 'O';
            player2Positions.add(position);
        }

        switch(position){
            case 1:
                gameBoard[0][0] = symbol;
                break;
            case 2:
                gameBoard[0][2] = symbol;
                break;
            case 3:
                gameBoard[0][4] = symbol;
                break;
            case 4:
                gameBoard[0][6] = symbol;
                break;
            case 5:
                gameBoard[0][8] = symbol;
                break;
            case 6:
                gameBoard[2][0] = symbol;
                break;
            case 7:
                gameBoard[2][2] = symbol;
                break;
            case 8:
                gameBoard[2][4] = symbol;
                break;
            case 9:
                gameBoard[2][6] = symbol;
                break;
            case 10:
                gameBoard[2][8] = symbol;
                break;
            case 11:
                gameBoard[4][0] = symbol;
                break;
            case 12:
                gameBoard[4][2] = symbol;
                break;
            case 13:
                gameBoard[4][4] = symbol;
                break;
            case 14:
                gameBoard[4][6] = symbol;
                break;
            case 15:
                gameBoard[4][8] = symbol;
                break;
            case 16:
                gameBoard[6][0] = symbol;
                break;
            case 17:
                gameBoard[6][2] = symbol;
                break;
            case 18:
                gameBoard[6][4] = symbol;
                break;
            case 19:
                gameBoard[6][6] = symbol;
                break;
            case 20:
                gameBoard[6][8] = symbol;
                break;
            case 21:
                gameBoard[8][0] = symbol;
                break;
            case 22:
                gameBoard[8][2] = symbol;
                break;
            case 23:
                gameBoard[8][4] = symbol;
                break;
            case 24:
                gameBoard[8][6] = symbol;
                break;
            case 25:
                gameBoard[8][8] = symbol;
                break;
            default:
                break;
        }
    }

    public static String checkWinner(){
        List row1 = Arrays.asList(1,2,3);
        List row2 = Arrays.asList(2,3,4);
        List row3 = Arrays.asList(3,4,5);
        List row4 = Arrays.asList(6,7,8);
        List row5 = Arrays.asList(7,8,9);
        List row6 = Arrays.asList(8,9,10);
        List row7 = Arrays.asList(11,12,13);
        List row8 = Arrays.asList(12,13,14);
        List row9 = Arrays.asList(13,14,15);
        List row10 = Arrays.asList(16,17,18);
        List row11 = Arrays.asList(17,18,19);
        List row12 = Arrays.asList(18,19,20);
        List row13 = Arrays.asList(21,22,23);
        List row14 = Arrays.asList(22,23,24);
        List row15 = Arrays.asList(23,24,25);

        List col1 = Arrays.asList(1,6,11);
        List col2 = Arrays.asList(6,11,16);
        List col3 = Arrays.asList(11,16,21);
        List col4 = Arrays.asList(2,7,12);
        List col5 = Arrays.asList(7,12,17);
        List col6 = Arrays.asList(12,17,22);
        List col7 = Arrays.asList(3,8,13);
        List col8 = Arrays.asList(8,13,18);
        List col9 = Arrays.asList(13,18,23);
        List col10 = Arrays.asList(4,9,14);
        List col11 = Arrays.asList(9,14,19);
        List col12 = Arrays.asList(14,19,24);
        List col13 = Arrays.asList(5,10,15);
        List col14 = Arrays.asList(10,15,20);
        List col15 = Arrays.asList(15,20,25);

        List cross1 = Arrays.asList(1,7,13);
        List cross2 = Arrays.asList(2,8,14);
        List cross3 = Arrays.asList(3,9,15);
        List cross4 = Arrays.asList(3,7,11);
        List cross5 = Arrays.asList(4,8,12);
        List cross6 = Arrays.asList(5,9,13);
        List cross7 = Arrays.asList(6,12,18);
        List cross8 = Arrays.asList(7,13,19);
        List cross9 = Arrays.asList(8,14,20);
        List cross10 = Arrays.asList(8,12,16);
        List cross11 = Arrays.asList(9,13,17);
        List cross12 = Arrays.asList(10,14,18);
        List cross13 = Arrays.asList(11,17,23);
        List cross14 = Arrays.asList(12,18,24);
        List cross15 = Arrays.asList(13,19,25);
        List cross16 = Arrays.asList(13,17,21);
        List cross17 = Arrays.asList(14,18,22);
        List cross18 = Arrays.asList(15,19,23);

        List<List> winning = new ArrayList<List>();
        winning.add(row1);
        winning.add(row2);
        winning.add(row3);
        winning.add(row4);
        winning.add(row5);
        winning.add(row6);
        winning.add(row7);
        winning.add(row8);
        winning.add(row9);
        winning.add(row10);
        winning.add(row11);
        winning.add(row12);
        winning.add(row13);
        winning.add(row14);
        winning.add(row15);
        winning.add(col1);
        winning.add(col2);
        winning.add(col3);
        winning.add(col4);
        winning.add(col5);
        winning.add(col6);
        winning.add(col7);
        winning.add(col8);
        winning.add(col9);
        winning.add(col10);
        winning.add(col11);
        winning.add(col12);
        winning.add(col13);
        winning.add(col14);
        winning.add(col15);
        winning.add(cross1);
        winning.add(cross2);
        winning.add(cross3);
        winning.add(cross4);
        winning.add(cross5);
        winning.add(cross6);
        winning.add(cross7);
        winning.add(cross8);
        winning.add(cross9);
        winning.add(cross10);
        winning.add(cross11);
        winning.add(cross12);
        winning.add(cross13);
        winning.add(cross14);
        winning.add(cross15);
        winning.add(cross16);
        winning.add(cross17);
        winning.add(cross18);

        for(List l : winning){
            if(player1Positions.containsAll(l)){
                return "Congratulations Player 1 win! ";
            }else if(player2Positions.containsAll(l)){
                return "Congratulations Player 2 win!";
            }else if(player1Positions.size() + player2Positions.size() == 25){
                return "Tie!";
            }
        }
        return "";
    }
}