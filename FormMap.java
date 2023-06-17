package com.Suzume;

public class FormMap {
    private static int[][] completeMap = new int[40][20];

    public static void main(String[] args) {
        formCompleteMap();
        displayMap();
    }

    public static void formCompleteMap(){
        Pixel pixel = new Pixel();

        int[][] pixel1 = pixel.image1.getArray();
        int[][] pixel2 = pixel.image2.getArray();
        int[][] pixel3 = pixel.image3.getArray();
        int[][] pixel4 = pixel.image4.getArray();


        for(int i=0;i<40;i++){ // row
            // reset back to 0 after each outer loop
            int pixel2Column = 0, pixel4Column = 0;

            for(int j=0;j<20;j++) { // column

                // top left
                if (i<20 && j<10)
                    completeMap[i][j] = pixel1[i][j];

                    // top right
                else if(i<20 && j>=10){
                    completeMap[i][j] = pixel2[i][pixel2Column];
                    pixel2Column+=1;
                }

                // bottom left
                else if(i>=20 && j<10){
                    completeMap[i][j] = pixel3[i-20][j];
                }

                // bottom right
                else if(i>=20 && j>=10){
                    completeMap[i][j] = pixel4[i-20][pixel4Column];
                    pixel4Column+=1;
                }
            }
        }
    }
    public  static void displayMap(){
        // Display 2D array
        for(int[] row: completeMap){
            for(int element:row){
                System.out.print(element+" ");
            }
            System.out.println();
        }
        System.out.println();
    }
    public static int[][] getCompleteMap(){
        return completeMap;
    }
}
