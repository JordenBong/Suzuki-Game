package com.Suzume;
import java.util.*;
public class DecryptAnswer {
    public static void main(String[] args) {

        System.out.println("Encrypt 17282: "+encrypt(17282,7));
        System.out.println("Decrypt 17355: "+decrypt(17355,7));
    }
    public static int encrypt(int toEncrypt, int key) {
        // convert number to binary format
        String binaryString = Integer.toBinaryString(toEncrypt);

        // to store blocks of three digits
        ArrayList<ArrayList<String>> mainBlocks = new ArrayList<>();

        // for binaryString length which is not divisible by 3
        int index = 0;

        // start from most right
        for (int i = binaryString.length() - 3; i >= 0; i -= 3) {
                ArrayList<String> oneBlock = new ArrayList<>();
                oneBlock.add(binaryString.substring(i, i + 3));
                mainBlocks.add(oneBlock);
                index = i;
        }

        // if length is 7, index is 1, if length is 8, index is 2
        // in both cases, 7 and 8 not divisible by 3, thus will arrive at this block of code
        if(index!=0){
            ArrayList<String> oneBlock = new ArrayList<>();
            String paddedString = String.format("%03d", Integer.parseInt(binaryString.substring(0,index)));
            oneBlock.add(paddedString);
            mainBlocks.add(oneBlock);
        }


        // for adding the secret key and shifting
        ArrayList<ArrayList<String>> computeBlocks = new ArrayList<>();

        for(ArrayList<String> block:mainBlocks){

            ArrayList<String> oneBlock = new ArrayList<>();

           int actualValue = Integer.parseInt(block.get(0),2);
           actualValue += key%2;
           key = key>>1;

           String paddedString = String.format("%03d",Integer.parseInt(Integer.toBinaryString(actualValue)));
           oneBlock.add(paddedString);
           computeBlocks.add(oneBlock);

        }

        StringBuilder encrypted = new StringBuilder();
        for(int i=computeBlocks.size()-1;i>=0;i--){
            encrypted.append(computeBlocks.get(i).get(0));
        }
       return Integer.parseInt(encrypted.toString(),2);
    } // end encrypt method


    public static int decrypt(int toEncrypt, int key) {



        // convert number to binary format
        String binaryString = Integer.toBinaryString(toEncrypt);

        // to store blocks of three digits
        ArrayList<ArrayList<String>> mainBlocks = new ArrayList<>();

        // for binaryString length which is not divisible by 3
        int index = 0;
        // start from most right
        for (int i = binaryString.length() - 3; i >= 0; i -= 3) {
            ArrayList<String> oneBlock = new ArrayList<>();
            oneBlock.add(binaryString.substring(i, i + 3));
            mainBlocks.add(oneBlock);
            index = i;
        }

        // if length is 7, index is 1, if length is 8, index is 2
        // in both cases, 7 and 8 not divisible by 3, thus will arrive at this block of code
        if(index!=0){
            ArrayList<String> oneBlock = new ArrayList<>();
            String paddedString = String.format("%03d", Integer.parseInt(binaryString.substring(0,index)));
            oneBlock.add(paddedString);
            mainBlocks.add(oneBlock);
        }


        // for subtracting the secret key and shifting
        ArrayList<ArrayList<String>> computeBlocks = new ArrayList<>();

        for(ArrayList<String> block:mainBlocks){

            ArrayList<String> oneBlock = new ArrayList<>();

            int actualValue = Integer.parseInt(block.get(0),2);
            actualValue -= key%2;
            key = key>>1;

            String paddedString = String.format("%03d",Integer.parseInt(Integer.toBinaryString(actualValue)));
            oneBlock.add(paddedString);
            computeBlocks.add(oneBlock);

        }

        StringBuilder decrypted = new StringBuilder();
        for(int i=computeBlocks.size()-1;i>=0;i--){
            decrypted.append(computeBlocks.get(i).get(0));
        }
        return Integer.parseInt(decrypted.toString(),2);
    } // end decrypt method
}
