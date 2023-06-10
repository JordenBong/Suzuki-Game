import java.util.Scanner;

public class MainRegularTicTacToe {
    public static void main(String[] args) {
        MainRegularTicTacToe main = new MainRegularTicTacToe();
        main.start();
    }

    public static void start(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Regular Tic Tac Toe!!");
        System.out.println("In this game, you have to beat the CPU (in Main Mode) in order to continue your journey. ");
        System.out.println("Please choose the game mode below (1/2/3): ");
        System.out.println("1. Main Mode: Battle with the CPU");
        System.out.println("2. Fun Mode: Challenge your Friends");
        System.out.println("2. Fun Mode: CPU Challenge CPU");
        System.out.print("Choice: ");
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
        start();
    }

    public static void engineVsEngine(){
        EngineVsEngine c = new EngineVsEngine();
        c.display3();
        start();
    }
}