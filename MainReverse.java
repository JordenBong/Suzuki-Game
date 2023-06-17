package com.Suzume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;

public class MainReverse {

    String[] arr = {"Easy","Medium","Hard"};
    public static void main(String[] args) {
        MainReverse main = new MainReverse();
        main.menu();
        //System.out.println(ReversePvP.status);
    }

    public void menu(){
        Scanner sc = new Scanner(System.in);
        Random rdm = new Random();
        ArrayList<String> list = new ArrayList<>(Arrays.asList("1","2"));
        System.out.println("Welcome to Reverse/Misere Tic Tac Toe ~~");
        System.out.println("In this game, you have to beat the CPU (in Main Mode) in order to continue your journey. ");
        System.out.println("Please choose the game mode below (1/2): ");
        System.out.println("1. Main Mode: Battle with the CPU");
        System.out.println("2. Fun Mode: Challenge your Friends");
        System.out.print("Choice: ");
        String reply = sc.next();
        while(!list.contains(reply)){
            System.out.print("Please enter valid command: ");
            reply = sc.next();
        }
        System.out.println();

        if(reply.equals("1")){
            ReversePvP rev1 = new ReversePvP();
            rev1.game(arr[rdm.nextInt(3)]);
            //System.out.println("Status: "+rev1.sta);
            //rev1.game("Hard");
        }else{
            ReverseFvF rev2 = new ReverseFvF();
            rev2.game();
            System.out.println();
            menu();
        }
    }
}
