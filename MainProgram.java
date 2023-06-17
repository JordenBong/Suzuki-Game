package com.Suzume;
import java.util.Scanner;

public class MainProgram {
    public static void main(String[] args) throws Exception {
    // basic();
     WildTicTacToe.main(null);
        //System.out.println(WildTicTacToe.winnerPlayer);
    }

    public static void basic() throws Exception {
        System.out.println("Basic Requirement 1");
        Pixel.main(null);
        Scanner sc = new Scanner(System.in);
        Scanner sc1 = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);
        Scanner sc3 = new Scanner(System.in);
        Scanner sc4 = new Scanner(System.in);

        if(sc.nextLine().isEmpty()){
            System.out.println("Basic Requirement 2");
            MapPieces.main(null);
        }

        if(sc1.nextLine().isEmpty()){
            System.out.println("Basic Requirement 3");
            FormMap.main(null);
        }

        if(sc2.nextLine().isEmpty()){
            System.out.println("Basic Requirement 4\n");
            PossiblePaths.main(null);
        }

        if(sc3.nextLine().isEmpty()){
            System.out.println("Basic Requirement 5\n");
            DecryptAnswer.main(null);
        }

        if(sc4.nextLine().isEmpty()){
            System.out.println("Basic Requirement 6");
            ShortestPath.main(null);
        }

    }
}
