import java.util.Scanner;

public class TicTacToe {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Tic Tac Toe!!");
        System.out.println("1. Player vs. Engine");
        System.out.println("2. Player vs. Player");
        System.out.println("3. Engine vs. Engine");
        System.out.print("Choose a mode (1-3): ");
        int mode = sc.nextInt();

        switch (mode) {
            case 1:
                playerVsEngine();
                break;
            case 2:
                playerVsPlayer();
                break;
            case 3:
                engineVsEngine();
                break;
            default:
                System.out.println("Invalid mode selected");
                break;
        }
    }

    public static void playerVsEngine(){
        PlayerVsEngine a = new PlayerVsEngine();
        a.display1();
    }

    public static void playerVsPlayer(){
        PlayerVsPlayer b = new PlayerVsPlayer();
        b.display2();
    }

    public static void engineVsEngine(){
        EngineVsEngine c = new EngineVsEngine();
        c.display3();
    }
}
