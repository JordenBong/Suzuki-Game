package com.Suzume;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;


public class Pixel {
   PixelProcessing image1;
   PixelProcessing image2;
    PixelProcessing image3;
   PixelProcessing image4;

    public Pixel(){
        image1 = new PixelProcessing(PixelProcessing.getImage1());
        image2 = new PixelProcessing(PixelProcessing.getImage2());
        image3 = new PixelProcessing(PixelProcessing.getImage3());
        image4 = new PixelProcessing(PixelProcessing.getImage4());
    }
    public static void main(String[] args) throws Exception{
       Pixel pixel = new Pixel();
       System.out.println("Image 1");
       pixel.image1.display();
       System.out.println("Image 2");
       pixel.image2.display();
       System.out.println("Image 3");
       pixel.image3.display();
       System.out.println("Image 4");
       pixel.image4.display();
    }
}

class PixelProcessing {
    //we only pass in the image filename once, if new image is given, the filename is only edited here
    private static final String IMAGE1 = "C:\\Users\\Master\\Downloads\\image 1.png";
    private static final String IMAGE2 = "C:\\Users\\Master\\Downloads\\image 2.png";
    private static final String IMAGE3 = "C:\\Users\\Master\\Downloads\\image 3.png";
    private static final String IMAGE4 = "C:\\Users\\Master\\Downloads\\image 4.png";
    private int[][] array = new int[20][10]; // 20 rows x 10 columns

    // Pixel processing method
    public int[][] readImage(String imageFilePath) {
        try {
            // Read the grayscale image file
            File imageFile = new File(imageFilePath);
            BufferedImage image = ImageIO.read(imageFile);

            // Get the image dimension
            int width = image.getWidth();
            int height = image.getHeight();

            // Get the pixel values
            for (int y = 0; y < height; y++) {    // height = row, width = column
                for (int x = 0; x < width; x++) {
                    int rgb = image.getRGB(x, y);
                    // extract the red component as grayscale intensity
                    //  since in a grayscale image, red, green, and blue components are all the same
                    int gray = (rgb >> 16) & 0xFF;

                    // Mathematical calculation to convert pixel values to numbers in range 0-3
                    gray = gray / 64;
                    array[y][x] = gray;
                }
            }

        } catch (Exception e) {
            System.out.println("Image not found");
        }
        return this.array;
    }

    public PixelProcessing(String imageFilePath){
        this.array = readImage(imageFilePath);
    }

    public static String getImage1(){
        return IMAGE1;
    }
    public static String getImage2(){
        return IMAGE2;
    }
    public static String getImage3(){
        return IMAGE3;
    }
    public static String getImage4(){
        return IMAGE4;
    }
    public int[][] getArray(){
        return this.array;
    }
    public void display(){
        // Display 2D array
        for(int[] row: this.array){
            for(int element:row){
                System.out.print(element+" ");
            }
            System.out.println();
        }
        System.out.println();
    }
}