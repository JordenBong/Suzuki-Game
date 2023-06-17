package com.Suzume;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BombMap extends JPanel implements Runnable, KeyListener{

    // FILE USAGE
    public static final String mainDatabase = "C:\\Users\\Master\\Downloads\\ds.csv";
    public static final String  stepsLossDatabase = "C:\\Users\\Master\\Downloads\\step&loss.csv";
    public static final String leaderboardDatabase = "C:\\Users\\Master\\Downloads\\leaderboard.csv";
    private static final String imageBomberman = "C:\\Users\\Master\\Downloads\\sheets.png";



    public static int showLeaderboardAtLast = 0;
    private DirectionFrame directionFrame;
    private Login login;
    private static SaveButton saveButton;
    private static SaveButton2 saveButton2;
    public static ReadExcel readExcel;
    public static ErrorMessage errorMessage;
    private static JDialog dialog;
    private Clicks click;
    public static Leaderboard leaderboard;
    public  static StepsCount stepsCount;

    public static int steps;
    public static int loss;

    private ArrayList<String> direction1;

    public static ArrayList<Double> coordinateX = new ArrayList<>();
    public static ArrayList<Double> coordinateY = new ArrayList<>();

    static boolean isRunning;
    static Thread thread;

    BufferedImage view, concreteTile, blockTile, player;
    int[][] scene;
    static double playerX, playerY;

    int tileSize = 16, rows = 40, columns = 20;

    final int SCALE = 1;
    final int WIDTH = (tileSize * SCALE) * columns;
    final int HEIGHT = (tileSize * SCALE) * rows;

    double speed = 0.05;
    boolean right, left, up, down;
    boolean moving;
    int framePlayer = 0, intervalPlayer = 5, indexAnimPlayer = 0;
    BufferedImage[] playerAnimUp, playerAnimDown, playerAnimRight, playerAnimLeft;

    public static BombMap obj = new BombMap();  // create BombMap object here

    public static JFrame w;
    public BombMap(){
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addKeyListener(this);

        // can remove afterwards
        //hello();
    }



    public static void main(String[] args) {
        w = new JFrame("Bomberman");
        w.setResizable(false);
        w.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        w.add(obj); // can straight use w.add(new BombMap())
        w.pack();
        w.setLocationRelativeTo(null);
        w.setVisible(true);

    }

    public boolean isFree(double nextX, double nextY){

        int size = SCALE * tileSize;
        int rows = 40;
        int columns = 20;

        // Check if the next position is out of bounds
        if (nextX < 0 || nextY < 0 ) {
            return false;
        }

        double nextX_1 = nextX / size;
        double nextY_1 = nextY / size;

        double nextX_2 = (nextX + size - 1)/size;
        double nextY_2 = nextY / size;

        double nextX_3 = nextX / size;
        double nextY_3 = (nextY + size - 1)/size;

        double nextX_4 = (nextX + size - 1)/size;
        double nextY_4 = (nextY + size - 1)/size;


        try{
        return !(scene[(int)nextY_1][(int)nextX_1]==1 || scene[(int)nextY_2][(int)nextX_2] == 1 ||
                 scene[(int)nextY_3][(int)nextX_3]==1 || scene[(int)nextY_4][(int)nextX_4]== 1 ||
                scene[(int)nextY_1][(int)nextX_1]==10 || scene[(int)nextY_2][(int)nextX_2] == 10 ||
                scene[(int)nextY_3][(int)nextX_3]==10 || scene[(int)nextY_4][(int)nextX_4]== 10);}
        catch (ArrayIndexOutOfBoundsException e){
            return false;
        }

    }
    public void start(double playerX, double playerY){
        try{
            view = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_RGB);
            BufferedImage sheet = ImageIO.read(new File(imageBomberman));

            concreteTile = sheet.getSubimage(4 * tileSize, 3 * tileSize, tileSize,tileSize);

            blockTile = sheet.getSubimage(3 * tileSize, 3 * tileSize, tileSize,tileSize);

            player = sheet.getSubimage(4*tileSize, 0, tileSize,tileSize);

            playerAnimUp = new BufferedImage[3];
            playerAnimDown = new BufferedImage[3];
            playerAnimRight = new BufferedImage[3];
           playerAnimLeft = new BufferedImage[3];

            for(int i = 0;i<3;i++){
               playerAnimLeft[i] = sheet.getSubimage(i*tileSize,0,tileSize,tileSize);
                playerAnimRight[i] = sheet.getSubimage(i*tileSize,tileSize,tileSize,tileSize);
                playerAnimDown[i] = sheet.getSubimage((i+3)*tileSize,0,tileSize,tileSize);
                playerAnimUp[i] = sheet.getSubimage((i+3)*tileSize,tileSize,tileSize,tileSize);
            }


            scene = map();

//            for(int i = 0;i<rows;i++){
//                for(int j=0;j<columns;j++){
//                    if(scene[i][j]==0){
//                        if(new Random().nextInt(10)<5)
//                            scene[i][j] = 2;
//                    }
//                }
//            }
//            scene[1][1] = 0;
//            scene[2][1] = 0;
//            scene[1][2] = 0;

            // Player X position and Y position respectively
            // starting at (0,0)
            this.playerX = playerX;
            this.playerY = playerY;

//              first station
//            playerX = 0;
//            playerY = 240;

//             second station
//            playerX = 33;
//            playerY = 320;

//             third station
//            playerX = 112;
//            playerY = 368;

//            fourth station
//            playerX = 160;
//            playerY = 560;

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    // for first time user
    public void draw(){


        Graphics2D g2 = (Graphics2D) view.getGraphics();
        g2.setColor(new Color(18, 19, 17));

        g2.fillRect(0,0,WIDTH,HEIGHT);

        int size = tileSize * SCALE;

        for(int i = 0;i<columns;i++){
            for(int j=0;j<rows;j++){
                // Obstacle, 1, is grey block
                if(scene[j][i]==1)
                g2.drawImage(blockTile,i*size,j*size,size,size,null);
                // Station, 2, is blue
                else if (scene[j][i] == 2 || scene[j][i] == 10) {
                    g2.setColor(new Color(7, 55, 134)); // Set the desired color here
                    g2.fillRect(i * size, j * size, size, size);
                }
                else if (click.isClicked() && click.clickTimes()==2) {
                    scene[0][0] = 4;scene[1][0] = 4;scene[2][0] = 4;scene[3][0] = 4;
                    scene[4][0] = 4;scene[5][0] = 4;scene[6][0] = 4;scene[7][0] = 4;
                    scene[8][0] = 4;scene[9][0] = 4;scene[10][0] = 4;scene[11][0] = 4;
                    scene[12][0] = 4;scene[13][0] = 4;scene[14][0] = 4;//ultimateMap.getCompleteMap()[15][0] = 4;
                    scene[16][0] = 4;scene[17][0] = 4;scene[18][0] = 4;scene[19][0] = 4;
                    scene[20][0] = 4;scene[20][1] = 4;
                    //ultimateMap.getCompleteMap()[20][2] = 4;
                    scene[20][3] = 4;scene[20][4] = 4;
                    scene[20][5] = 4;scene[21][5] = 4;scene[21][6] = 4;scene[21][7] = 4;
                    scene[22][7] = 4;//ultimateMap.getCompleteMap()[23][7] = 4;
                    scene[23][8] = 4;scene[24][8] = 4;scene[25][8] = 4;scene[26][8] = 4;scene[27][8] = 4;
                    scene[28][8] = 4;scene[28][7] = 4;scene[29][7] = 4;
                    scene[30][7] = 4;scene[30][8] = 4;scene[31][8] = 4;scene[32][8] = 4;
                    scene[32][9] = 4;scene[32][10] = 4;scene[33][10] = 4;scene[34][10] = 4;
                    //scene[35][10] = 4;
                    scene[36][10] = 4;scene[37][10] = 4;scene[38][10] = 4;
                    scene[38][11] = 4;scene[38][12] = 4;scene[38][13] = 4;scene[39][13] = 4;
                    scene[39][14] = 4;scene[39][15] = 4;scene[38][15] = 4;scene[38][16] = 4;
                    scene[38][17] = 4;scene[38][18] = 4;scene[39][18] = 4;scene[39][19] = 4;
                    if(scene[j][i]==4){
                        g2.setColor(new Color(229, 89, 106)); // Set the desired color here
                        g2.fillRect(i * size, j * size, size, size);
                    }
                }
                else if (click.isClicked() && click.clickTimes()==3) {
                    scene[0][0] = 5;scene[1][0] = 5;scene[2][0] = 5;scene[3][0] = 5;
                    scene[4][0] = 5;scene[5][0] = 5;scene[6][0] = 5;scene[7][0] = 5;
                    scene[8][0] = 5;scene[9][0] = 5;scene[10][0] = 5;scene[11][0] = 5;
                    scene[12][0] = 5;scene[13][0] = 5;scene[14][0] = 5;//ultimateMap.getCompleteMap()[15][0] = 4;
                    scene[16][0] = 5;scene[17][0] = 5;scene[18][0] = 5;scene[19][0] = 5;
                   scene[20][0] = 5;scene[20][1] = 5;
                    //ultimateMap.getCompleteMap()[20][2] = 4;
                    scene[20][3] = 5;scene[20][4] = 5;
                    scene[20][5] = 5;scene[21][5] = 5;
                    scene[21][6] = 5;scene[21][7] = 5;
                    scene[22][7] = 5;//ultimateMap.getCompleteMap()[23][7] = 4;
                    scene[23][8] = 5;scene[24][8] = 5;
                    scene[25][8] = 5;scene[26][8] = 5;
                    scene[27][8] = 5;scene[28][8] = 5;
                    scene[28][7] = 5;scene[29][7] = 5;
                    scene[30][7] = 5;scene[30][8] = 5;scene[31][8] = 5;scene[32][8] = 5;
                    scene[32][9] = 5;scene[33][9] = 5;scene[33][10] = 5;scene[34][10] = 5;
                   // scene[35][10] = 5;
                    scene[36][10] = 5;scene[37][10] = 5;scene[38][10] = 5;
                    scene[38][11] = 5;scene[38][12] = 5;scene[38][13] = 5;scene[39][13] = 5;
                    scene[39][14] = 5;scene[39][15] = 5;scene[38][15] = 5;scene[38][16] = 5;
                    scene[38][17] = 5;scene[38][18] = 5;scene[39][18] = 5;scene[39][19] = 5;
                    if(scene[j][i] == 5) {
                        g2.setColor(new Color(232, 153, 65)); // Set the desired color here
                        g2.fillRect(i * size, j * size, size, size);
                    }
                }
                else if (click.isClicked() && click.clickTimes()==4) {
                    scene[0][0] = 6;scene[1][0] = 6;scene[2][0] = 6;scene[3][0] = 6;
                    scene[4][0] = 6;scene[5][0] = 6;scene[6][0] = 6;scene[7][0] = 6;
                    scene[8][0] = 6;scene[9][0] = 6;scene[10][0] = 6;scene[11][0] = 6;
                    scene[12][0] = 6;scene[13][0] = 6;scene[14][0] = 6;//ultimateMap.getCompleteMap()[15][0] = 4;
                    scene[16][0] = 6;scene[17][0] = 6;scene[18][0] = 6;scene[19][0] = 6;
                    scene[20][0] = 6;scene[20][1] = 6;
                    //ultimateMap.getCompleteMap()[20][2] = 4;
                    scene[20][3] = 6;scene[20][4] = 6;
                    scene[20][5] = 6;scene[21][5] = 6;
                    scene[21][6] = 6;scene[21][7] = 6;
                    scene[22][7] = 6;//ultimateMap.getCompleteMap()[23][7] = 4;
                    scene[23][8] = 6;scene[24][8] = 6;
                    scene[25][8] = 6;scene[26][8] = 6;
                    scene[27][8] = 6;scene[28][8] = 6;
                    scene[28][7] = 6;scene[29][7] = 6;
                    scene[30][7] = 6;scene[30][8] = 6;scene[31][8] = 6;scene[32][8] = 6;
                    scene[32][9] = 6;scene[33][9] = 6;scene[34][9] = 6;scene[34][10] = 6;
                    //scene[35][10] = 6;
                    scene[36][10] = 6;scene[37][10] = 6;scene[38][10] = 6;
                    scene[38][11] = 6;scene[38][12] = 6;scene[38][13] = 6;scene[39][13] = 6;
                    scene[39][14] = 6;scene[39][15] = 6;scene[38][15] = 6;scene[38][16] = 6;
                    scene[38][17] = 6;scene[38][18] = 6;scene[39][18] = 6;scene[39][19] = 6;
                    if(scene[j][i] == 6) {
                        g2.setColor(new Color(191, 191, 31)); // Set the desired color here
                        g2.fillRect(i * size, j * size, size, size);
                    }
                }
                else if (click.isClicked() && click.clickTimes()==5) {
                    scene[0][0] = 7;scene[1][0] = 7;scene[2][0] = 7;scene[3][0] = 7;
                    scene[4][0] = 7;scene[5][0] = 7;scene[6][0] = 7;scene[7][0] = 7;
                    scene[8][0] = 7;scene[9][0] = 7;scene[10][0] = 7;scene[11][0] = 7;
                    scene[12][0] = 7;scene[13][0] = 7;scene[14][0] = 7;//ultimateMap.getCompleteMap()[15][0] = 4;
                    scene[16][0] = 7;scene[17][0] = 7;scene[18][0] = 7;scene[19][0] = 7;
                    scene[20][0] = 7;scene[20][1] = 7;
                    //ultimateMap.getCompleteMap()[20][2] = 4;
                    scene[20][3] = 7;scene[20][4] = 7;
                    scene[20][5] = 7;scene[21][5] = 7;
                    scene[22][5] = 7;scene[23][5] = 7;
                    scene[23][6] = 7;//ultimateMap.getCompleteMap()[23][7] = 4;
                    scene[23][8] = 7;scene[24][8] = 7;
                    scene[25][8] = 7;scene[26][8] = 7;
                    scene[27][8] = 7;scene[28][8] = 7;
                    scene[28][7] = 7;scene[29][7] = 7;
                    scene[30][7] = 7;scene[30][8] = 7;scene[31][8] = 7;scene[32][8] = 7;
                    scene[32][9] = 7;scene[32][10] = 7;scene[33][10] = 7;scene[34][10] = 7;
                    //scene[35][10] = 6;
                    scene[36][10] = 7;scene[37][10] = 7;scene[38][10] = 7;
                    scene[38][11] = 7;scene[38][12] = 7;scene[38][13] = 7;scene[39][13] = 7;
                    scene[39][14] = 7;scene[39][15] = 7;scene[38][15] = 7;scene[38][16] = 7;
                    scene[38][17] = 7;scene[38][18] = 7;scene[39][18] = 7;scene[39][19] = 7;
                    if(scene[j][i] == 7) {
                        g2.setColor(new Color(70, 163, 50)); // Set the desired color here
                        g2.fillRect(i * size, j * size, size, size);
                    }
                }
                else if (click.isClicked() && click.clickTimes()==6) {
                    scene[0][0] = 8;scene[1][0] = 8;scene[2][0] = 8;scene[3][0] = 8;
                    scene[4][0] = 8;scene[5][0] = 8;scene[6][0] = 8;scene[7][0] = 8;
                    scene[8][0] = 8;scene[9][0] = 8;scene[10][0] = 8;scene[11][0] = 8;
                    scene[12][0] = 8;scene[13][0] = 8;scene[14][0] = 8;//ultimateMap.getCompleteMap()[15][0] = 4;
                    scene[16][0] = 8;scene[17][0] = 8;scene[18][0] = 8;scene[19][0] = 8;
                    scene[20][0] = 8;scene[20][1] = 8;
                    //ultimateMap.getCompleteMap()[20][2] = 4;
                    scene[20][3] = 8;scene[20][4] = 8;
                    scene[20][5] = 8;scene[21][5] = 8;
                    scene[22][5] = 8;scene[23][5] = 8;
                    scene[23][6] = 8;//ultimateMap.getCompleteMap()[23][7] = 4;
                    scene[23][8] = 8;scene[24][8] = 8;
                    scene[25][8] = 8;scene[26][8] = 8;
                    scene[27][8] = 8;scene[28][8] = 8;
                    scene[28][7] = 8;scene[29][7] = 8;
                    scene[30][7] = 8;scene[30][8] = 8;scene[31][8] = 8;scene[32][8] = 8;
                    scene[32][9] = 8;scene[33][9] = 8;scene[33][10] = 8;scene[34][10] = 8;
                    //scene[35][10] = 6;
                    scene[36][10] = 8;scene[37][10] = 8;scene[38][10] = 8;
                    scene[38][11] = 8;scene[38][12] = 8;scene[38][13] = 8;scene[39][13] = 8;
                    scene[39][14] = 8;scene[39][15] = 8;scene[38][15] = 8;scene[38][16] = 8;
                    scene[38][17] = 8;scene[38][18] = 8;scene[39][18] = 8;scene[39][19] = 8;
                    if(scene[j][i] == 8) {
                        g2.setColor(new Color(62, 143, 239)); // Set the desired color here
                        g2.fillRect(i * size, j * size, size, size);
                    }
                }
                else if (click.isClicked() && click.clickTimes()==7) {
                    scene[0][0] = 9;scene[1][0] = 9;scene[2][0] = 9;scene[3][0] = 9;
                    scene[4][0] = 9;scene[5][0] = 9;scene[6][0] = 9;scene[7][0] = 9;
                    scene[8][0] = 9;scene[9][0] = 9;scene[10][0] = 9;scene[11][0] = 9;
                    scene[12][0] = 9;scene[13][0] = 9;scene[14][0] = 9;//ultimateMap.getCompleteMap()[15][0] = 4;
                    scene[16][0] = 9;scene[17][0] = 9;scene[18][0] = 9;scene[19][0] = 9;
                    scene[20][0] = 9;scene[20][1] = 9;
                    //ultimateMap.getCompleteMap()[20][2] = 4;
                    scene[20][3] = 9;scene[20][4] = 9;
                    scene[20][5] = 9;scene[21][5] = 9;
                    scene[22][5] = 9;scene[23][5] = 9;
                    scene[23][6] = 9;//ultimateMap.getCompleteMap()[23][7] = 4;
                    scene[23][8] = 9;scene[24][8] = 9;
                    scene[25][8] = 9;scene[26][8] = 9;
                    scene[27][8] = 9;scene[28][8] = 9;
                    scene[28][7] = 9;scene[29][7] = 9;
                    scene[30][7] = 9;scene[30][8] = 9;scene[31][8] = 9;scene[32][8] = 9;
                    scene[32][9] = 9;scene[33][9] = 9;scene[34][9] = 9;scene[34][10] = 9;
                    //scene[35][10] = 6;
                    scene[36][10] = 9;scene[37][10] = 9;scene[38][10] = 9;
                    scene[38][11] = 9;scene[38][12] = 9;scene[38][13] = 9;scene[39][13] = 9;
                    scene[39][14] = 9;scene[39][15] = 9;scene[38][15] = 9;scene[38][16] = 9;
                    scene[38][17] = 9;scene[38][18] = 9;scene[39][18] = 9;scene[39][19] = 9;
                    if(scene[j][i] == 9) {
                        g2.setColor(new Color(152, 90, 212)); // Set the desired color here
                        g2.fillRect(i * size, j * size, size, size);
                    }
                }

            }
        }

        g2.drawImage(player,(int)playerX,(int)playerY,size,size,null);
        Graphics g = getGraphics();
        g.drawImage(view,0,0,WIDTH,HEIGHT,null);
        g.dispose();
}

// for match in database
    public void draw2(){


        Graphics2D g2 = (Graphics2D) view.getGraphics();
        g2.setColor(new Color(18, 19, 17));

        g2.fillRect(0,0,WIDTH,HEIGHT);

        int size = tileSize * SCALE;

        for(int i = 0;i<columns;i++){
            for(int j=0;j<rows;j++){
                // Obstacle, 1, is grey block
                if(scene[j][i]==1)
                    g2.drawImage(blockTile,i*size,j*size,size,size,null);
                    // Station, 2, is blue
                else if (scene[j][i] == 2 || scene[j][i] == 10) {
                    g2.setColor(new Color(7, 55, 134)); // Set the desired color here
                    g2.fillRect(i * size, j * size, size, size);
                }
                else if (ReadExcel.pathNumber.equals("1")) {
                    scene[0][0] = 4;scene[1][0] = 4;scene[2][0] = 4;scene[3][0] = 4;
                    scene[4][0] = 4;scene[5][0] = 4;scene[6][0] = 4;scene[7][0] = 4;
                    scene[8][0] = 4;scene[9][0] = 4;scene[10][0] = 4;scene[11][0] = 4;
                    scene[12][0] = 4;scene[13][0] = 4;scene[14][0] = 4;//ultimateMap.getCompleteMap()[15][0] = 4;
                    scene[16][0] = 4;scene[17][0] = 4;scene[18][0] = 4;scene[19][0] = 4;
                    scene[20][0] = 4;scene[20][1] = 4;
                    //ultimateMap.getCompleteMap()[20][2] = 4;
                    scene[20][3] = 4;scene[20][4] = 4;
                    scene[20][5] = 4;scene[21][5] = 4;scene[21][6] = 4;scene[21][7] = 4;
                    scene[22][7] = 4;//ultimateMap.getCompleteMap()[23][7] = 4;
                    scene[23][8] = 4;scene[24][8] = 4;scene[25][8] = 4;scene[26][8] = 4;scene[27][8] = 4;
                    scene[28][8] = 4;scene[28][7] = 4;scene[29][7] = 4;
                    scene[30][7] = 4;scene[30][8] = 4;scene[31][8] = 4;scene[32][8] = 4;
                    scene[32][9] = 4;scene[32][10] = 4;scene[33][10] = 4;scene[34][10] = 4;
                    //scene[35][10] = 4;
                    scene[36][10] = 4;scene[37][10] = 4;scene[38][10] = 4;
                    scene[38][11] = 4;scene[38][12] = 4;scene[38][13] = 4;scene[39][13] = 4;
                    scene[39][14] = 4;scene[39][15] = 4;scene[38][15] = 4;scene[38][16] = 4;
                    scene[38][17] = 4;scene[38][18] = 4;scene[39][18] = 4;scene[39][19] = 4;
                    if(scene[j][i]==4){
                        g2.setColor(new Color(229, 89, 106)); // Set the desired color here
                        g2.fillRect(i * size, j * size, size, size);
                    }
                }
                else if (ReadExcel.pathNumber.equals("2")) {
                    scene[0][0] = 5;scene[1][0] = 5;scene[2][0] = 5;scene[3][0] = 5;
                    scene[4][0] = 5;scene[5][0] = 5;scene[6][0] = 5;scene[7][0] = 5;
                    scene[8][0] = 5;scene[9][0] = 5;scene[10][0] = 5;scene[11][0] = 5;
                    scene[12][0] = 5;scene[13][0] = 5;scene[14][0] = 5;//ultimateMap.getCompleteMap()[15][0] = 4;
                    scene[16][0] = 5;scene[17][0] = 5;scene[18][0] = 5;scene[19][0] = 5;
                    scene[20][0] = 5;scene[20][1] = 5;
                    //ultimateMap.getCompleteMap()[20][2] = 4;
                    scene[20][3] = 5;scene[20][4] = 5;
                    scene[20][5] = 5;scene[21][5] = 5;
                    scene[21][6] = 5;scene[21][7] = 5;
                    scene[22][7] = 5;//ultimateMap.getCompleteMap()[23][7] = 4;
                    scene[23][8] = 5;scene[24][8] = 5;
                    scene[25][8] = 5;scene[26][8] = 5;
                    scene[27][8] = 5;scene[28][8] = 5;
                    scene[28][7] = 5;scene[29][7] = 5;
                    scene[30][7] = 5;scene[30][8] = 5;scene[31][8] = 5;scene[32][8] = 5;
                    scene[32][9] = 5;scene[33][9] = 5;scene[33][10] = 5;scene[34][10] = 5;
                    // scene[35][10] = 5;
                    scene[36][10] = 5;scene[37][10] = 5;scene[38][10] = 5;
                    scene[38][11] = 5;scene[38][12] = 5;scene[38][13] = 5;scene[39][13] = 5;
                    scene[39][14] = 5;scene[39][15] = 5;scene[38][15] = 5;scene[38][16] = 5;
                    scene[38][17] = 5;scene[38][18] = 5;scene[39][18] = 5;scene[39][19] = 5;
                    if(scene[j][i] == 5) {
                        g2.setColor(new Color(232, 153, 65)); // Set the desired color here
                        g2.fillRect(i * size, j * size, size, size);
                    }
                }
                else if (ReadExcel.pathNumber.equals("3")) {
                    scene[0][0] = 6;scene[1][0] = 6;scene[2][0] = 6;scene[3][0] = 6;
                    scene[4][0] = 6;scene[5][0] = 6;scene[6][0] = 6;scene[7][0] = 6;
                    scene[8][0] = 6;scene[9][0] = 6;scene[10][0] = 6;scene[11][0] = 6;
                    scene[12][0] = 6;scene[13][0] = 6;scene[14][0] = 6;//ultimateMap.getCompleteMap()[15][0] = 4;
                    scene[16][0] = 6;scene[17][0] = 6;scene[18][0] = 6;scene[19][0] = 6;
                    scene[20][0] = 6;scene[20][1] = 6;
                    //ultimateMap.getCompleteMap()[20][2] = 4;
                    scene[20][3] = 6;scene[20][4] = 6;
                    scene[20][5] = 6;scene[21][5] = 6;
                    scene[21][6] = 6;scene[21][7] = 6;
                    scene[22][7] = 6;//ultimateMap.getCompleteMap()[23][7] = 4;
                    scene[23][8] = 6;scene[24][8] = 6;
                    scene[25][8] = 6;scene[26][8] = 6;
                    scene[27][8] = 6;scene[28][8] = 6;
                    scene[28][7] = 6;scene[29][7] = 6;
                    scene[30][7] = 6;scene[30][8] = 6;scene[31][8] = 6;scene[32][8] = 6;
                    scene[32][9] = 6;scene[33][9] = 6;scene[34][9] = 6;scene[34][10] = 6;
                    //scene[35][10] = 6;
                    scene[36][10] = 6;scene[37][10] = 6;scene[38][10] = 6;
                    scene[38][11] = 6;scene[38][12] = 6;scene[38][13] = 6;scene[39][13] = 6;
                    scene[39][14] = 6;scene[39][15] = 6;scene[38][15] = 6;scene[38][16] = 6;
                    scene[38][17] = 6;scene[38][18] = 6;scene[39][18] = 6;scene[39][19] = 6;
                    if(scene[j][i] == 6) {
                        g2.setColor(new Color(191, 191, 31)); // Set the desired color here
                        g2.fillRect(i * size, j * size, size, size);
                    }
                }
                else if (ReadExcel.pathNumber.equals("4")) {
                    scene[0][0] = 7;scene[1][0] = 7;scene[2][0] = 7;scene[3][0] = 7;
                    scene[4][0] = 7;scene[5][0] = 7;scene[6][0] = 7;scene[7][0] = 7;
                    scene[8][0] = 7;scene[9][0] = 7;scene[10][0] = 7;scene[11][0] = 7;
                    scene[12][0] = 7;scene[13][0] = 7;scene[14][0] = 7;//ultimateMap.getCompleteMap()[15][0] = 4;
                    scene[16][0] = 7;scene[17][0] = 7;scene[18][0] = 7;scene[19][0] = 7;
                    scene[20][0] = 7;scene[20][1] = 7;
                    //ultimateMap.getCompleteMap()[20][2] = 4;
                    scene[20][3] = 7;scene[20][4] = 7;
                    scene[20][5] = 7;scene[21][5] = 7;
                    scene[22][5] = 7;scene[23][5] = 7;
                    scene[23][6] = 7;//ultimateMap.getCompleteMap()[23][7] = 4;
                    scene[23][8] = 7;scene[24][8] = 7;
                    scene[25][8] = 7;scene[26][8] = 7;
                    scene[27][8] = 7;scene[28][8] = 7;
                    scene[28][7] = 7;scene[29][7] = 7;
                    scene[30][7] = 7;scene[30][8] = 7;scene[31][8] = 7;scene[32][8] = 7;
                    scene[32][9] = 7;scene[32][10] = 7;scene[33][10] = 7;scene[34][10] = 7;
                    //scene[35][10] = 6;
                    scene[36][10] = 7;scene[37][10] = 7;scene[38][10] = 7;
                    scene[38][11] = 7;scene[38][12] = 7;scene[38][13] = 7;scene[39][13] = 7;
                    scene[39][14] = 7;scene[39][15] = 7;scene[38][15] = 7;scene[38][16] = 7;
                    scene[38][17] = 7;scene[38][18] = 7;scene[39][18] = 7;scene[39][19] = 7;
                    if(scene[j][i] == 7) {
                        g2.setColor(new Color(70, 163, 50)); // Set the desired color here
                        g2.fillRect(i * size, j * size, size, size);
                    }
                }
                else if (ReadExcel.pathNumber.equals("5")) {
                    scene[0][0] = 8;scene[1][0] = 8;scene[2][0] = 8;scene[3][0] = 8;
                    scene[4][0] = 8;scene[5][0] = 8;scene[6][0] = 8;scene[7][0] = 8;
                    scene[8][0] = 8;scene[9][0] = 8;scene[10][0] = 8;scene[11][0] = 8;
                    scene[12][0] = 8;scene[13][0] = 8;scene[14][0] = 8;//ultimateMap.getCompleteMap()[15][0] = 4;
                    scene[16][0] = 8;scene[17][0] = 8;scene[18][0] = 8;scene[19][0] = 8;
                    scene[20][0] = 8;scene[20][1] = 8;
                    //ultimateMap.getCompleteMap()[20][2] = 4;
                    scene[20][3] = 8;scene[20][4] = 8;
                    scene[20][5] = 8;scene[21][5] = 8;
                    scene[22][5] = 8;scene[23][5] = 8;
                    scene[23][6] = 8;//ultimateMap.getCompleteMap()[23][7] = 4;
                    scene[23][8] = 8;scene[24][8] = 8;
                    scene[25][8] = 8;scene[26][8] = 8;
                    scene[27][8] = 8;scene[28][8] = 8;
                    scene[28][7] = 8;scene[29][7] = 8;
                    scene[30][7] = 8;scene[30][8] = 8;scene[31][8] = 8;scene[32][8] = 8;
                    scene[32][9] = 8;scene[33][9] = 8;scene[33][10] = 8;scene[34][10] = 8;
                    //scene[35][10] = 6;
                    scene[36][10] = 8;scene[37][10] = 8;scene[38][10] = 8;
                    scene[38][11] = 8;scene[38][12] = 8;scene[38][13] = 8;scene[39][13] = 8;
                    scene[39][14] = 8;scene[39][15] = 8;scene[38][15] = 8;scene[38][16] = 8;
                    scene[38][17] = 8;scene[38][18] = 8;scene[39][18] = 8;scene[39][19] = 8;
                    if(scene[j][i] == 8) {
                        g2.setColor(new Color(62, 143, 239)); // Set the desired color here
                        g2.fillRect(i * size, j * size, size, size);
                    }
                }
                else if (ReadExcel.pathNumber.equals("6")) {
                    scene[0][0] = 9;scene[1][0] = 9;scene[2][0] = 9;scene[3][0] = 9;
                    scene[4][0] = 9;scene[5][0] = 9;scene[6][0] = 9;scene[7][0] = 9;
                    scene[8][0] = 9;scene[9][0] = 9;scene[10][0] = 9;scene[11][0] = 9;
                    scene[12][0] = 9;scene[13][0] = 9;scene[14][0] = 9;//ultimateMap.getCompleteMap()[15][0] = 4;
                    scene[16][0] = 9;scene[17][0] = 9;scene[18][0] = 9;scene[19][0] = 9;
                    scene[20][0] = 9;scene[20][1] = 9;
                    //ultimateMap.getCompleteMap()[20][2] = 4;
                    scene[20][3] = 9;scene[20][4] = 9;
                    scene[20][5] = 9;scene[21][5] = 9;
                    scene[22][5] = 9;scene[23][5] = 9;
                    scene[23][6] = 9;//ultimateMap.getCompleteMap()[23][7] = 4;
                    scene[23][8] = 9;scene[24][8] = 9;
                    scene[25][8] = 9;scene[26][8] = 9;
                    scene[27][8] = 9;scene[28][8] = 9;
                    scene[28][7] = 9;scene[29][7] = 9;
                    scene[30][7] = 9;scene[30][8] = 9;scene[31][8] = 9;scene[32][8] = 9;
                    scene[32][9] = 9;scene[33][9] = 9;scene[34][9] = 9;scene[34][10] = 9;
                    //scene[35][10] = 6;
                    scene[36][10] = 9;scene[37][10] = 9;scene[38][10] = 9;
                    scene[38][11] = 9;scene[38][12] = 9;scene[38][13] = 9;scene[39][13] = 9;
                    scene[39][14] = 9;scene[39][15] = 9;scene[38][15] = 9;scene[38][16] = 9;
                    scene[38][17] = 9;scene[38][18] = 9;scene[39][18] = 9;scene[39][19] = 9;
                    if(scene[j][i] == 9) {
                        g2.setColor(new Color(152, 90, 212)); // Set the desired color here
                        g2.fillRect(i * size, j * size, size, size);
                    }
                }

            }
        }
        g2.drawImage(player,(int)playerX,(int)playerY,size,size,null);
        Graphics g = getGraphics();
        g.drawImage(view,0,0,WIDTH,HEIGHT,null);
        g.dispose();
    }

    public int[][] map(){
        FormMap.formCompleteMap();

        // change 3 to 1 in 3 map pieces
        FormMap.getCompleteMap()[19][9] = 1;  // pixel 1
        FormMap.getCompleteMap()[19][19] = 1; // pixel 2
        FormMap.getCompleteMap()[39][9] = 1;  // pixel 3
//        CompleteMap ultimateMap = new CompleteMap();
//        ultimateMap.formCompleteMap();
//
//        // change 3 to 1 in 3 map pieces
//        ultimateMap.getCompleteMap()[19][9] = 1;  // pixel 1
//        ultimateMap.getCompleteMap()[19][19] = 1; // pixel 2
//        ultimateMap.getCompleteMap()[39][9] = 1;  // pixel 3

        return FormMap.getCompleteMap();
    }
    @Override
    public void addNotify(){
        super.addNotify();
        if(thread ==null){
            thread = new Thread(this);
            isRunning = true;
            thread.start();

        }
    }

    @Override
    public void run() {
        try {
            loginPrompt();
            readCSV();
            while(Login.username.equals("") || Login.password.equals("")){
                loginPrompt();
                readCSV();
            }
            while(readExcel.invalidPassword){
                //errorMessage();
                loginPrompt();
                readCSV();
            }
            // if no match in database
            if (!readExcel.match){
                requestFocus();
                pop();
                start(0,0);
                showLeaderboard();
                while (isRunning) {
                    update();
                    draw();
                    if (click.getConfirmMou()) {
                         click.setConfirmMou(false);
                         saveButton();
                    }
                }
            }
            // if got match in database
            else if (readExcel.match){
                requestFocus();
               // pop();
                start(ReadExcel.xCoordinate,ReadExcel.yCoordinate);
                showLeaderboard();
                while (isRunning) {
                    update();
                    draw2();
                    if(!readExcel.forSave){
                        readExcel.forSave = true;
                        saveButton2();}
                    //if (click.getConfirmMou()) {
                     //   click.setConfirmMou(false);
                      //  saveButton();
                        // setDirections(click.clickTimes());
                    //}
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void update(){
       scene[24][5] = 10;

        moving = false;
        if(right && isFree(playerX + speed, playerY)){
            playerX += speed;
            moving = true;

            // SECOND STATION - Wild TTT
            if(((playerX >= 29.97 && playerX <= 30.04) && (playerY >=320.88 && playerY <= 320.95))
                || ((playerX >= 32.76 && playerX <= 32.85) && (playerY >= 320.95 && playerY<=320.98))
                || ((playerX >= 33.84 && playerX<=33.86)) && playerY==320.9){
                System.out.println("\n****************************\nSECOND STATION\n****************************\n");
               WildTicTacToe.main(null);
                if(WildTicTacToe.gameOutcome==-1){
                    if (!readExcel.match){
                        requestFocus();
                        start(0,238.2);

                        WildTicTacToe.resetGame();

                        saveButton.incrementLoss();
                        saveButton.steps = 14;

                         while (isRunning) {
                         update();
                            draw();
                        }
                    }
                // if got match in database
                else if (readExcel.match){
                    requestFocus();
                    start(0,238.2);

                    WildTicTacToe.resetGame();

                    saveButton2.incrementLoss();
                    BombMap.steps = 14;

                    while (isRunning) {
                        update();
                        draw2();
                        if(!readExcel.forSave){
                            readExcel.forSave = true;
                            saveButton2();}
                        }
                    }
                }
                else
                    WildTicTacToe.resetGame();
            }

//            if(((playerX >= 112.08 && playerX <= 112.16)  && (playerY >=368.91 && playerY <= 368.99))
//                    ||((playerX>=113.86 && playerX<=113.90) && (playerY>=368.91 && playerY <= 368.99)))

            // THIRD STATION FROM RIGHT  - Regular TTT
          //  if((playerX>=113.86 && playerX<=113.90) && (playerY>=368.91 && playerY <= 368.99))
            if(((playerX>=113.86 && playerX<=113.90) && (playerY>=368.91 && playerY <= 368.99))
            || ((playerX>=114.75 && playerX<=114.78) && (playerY>=368.91 && playerY <= 368.99))
            || ((playerX>=114.23 && playerX<=114.26) && (playerY>=368.89 && playerY <= 368.91))) {
                System.out.println("\n****************************\nTHIRD STATION\n****************************\n");
                 MainRegularTicTacToe.main(null);

                if(PlayerVsEngine.status==-1){
                    if (!readExcel.match){
                        requestFocus();
                        start(30,320.9);

                        saveButton.incrementLoss();
                        saveButton.steps = 21;

                        while (isRunning) {
                         update();
                         draw();
                        }
                     }
                // if got match in database
                    else if (readExcel.match){
                        requestFocus();
                        start(30,320.9);

                        saveButton2.incrementLoss();
                        BombMap.steps = 21;
                        while (isRunning) {
                            update();
                            draw2();
                            if(!readExcel.forSave){
                                readExcel.forSave = true;
                                saveButton2();}
                            }
                         }
                    }
                }

            // ENDING
            if((playerX >= 304.93 && playerX <= 305.00)  && (playerY >=624.91 && playerY <= 625.01)) {
                System.out.println("Reached!");
                playerX=playerX-2;
                playerY=playerY-2;
                BombMap.leaderboard.close();  // this
                writeLeaderboard();
                showLeaderboard();              // and this
            }
            coordinateX.add(playerX);
            coordinateY.add(playerY);
        }
        if(left && isFree(playerX - speed, playerY)){
            playerX -= speed;
            moving = true;

            coordinateX.add(playerX);
            coordinateY.add(playerY);
        }

        if(up && isFree(playerX, playerY - speed)){
            playerY -= speed;
            moving = true;

            coordinateY.add(playerY);
            coordinateX.add(playerX);
        }

        if(down && isFree(playerX, playerY + speed)){
            playerY += speed;
            moving = true;

            // FIRST STATION - Reverse TTT
            if((playerX==0.0 || (playerX>=0.97 && playerX<=0.99)) && (playerY >=238.23 && playerY <= 238.29)) {
                System.out.println("\n****************************\nFIRST STATION\n****************************\n");
               MainReverse.main(null);
                if(ReversePvP.status==-1){
                   //  if no match in database
                  if (!readExcel.match){
                         requestFocus();
                        start(0,0);
                      saveButton.incrementLoss();
                      saveButton.steps = 0;
                        while (isRunning) {
                            update();
                            draw();
                        }
                 }
                // if got match in database
               else if (readExcel.match){
                        requestFocus();
                        start(0,0);
                      saveButton2.incrementLoss();
                      BombMap.steps = 0;
                        while (isRunning) {
                            update();
                            draw2();
                        if(!readExcel.forSave){
                            readExcel.forSave = true;
                            saveButton2();}
                    }
                  }
                }
            }

            // THIRD STATION FROM DOWN
           // if((playerX >= 112.94 && playerX <= 112.99)  && (playerY >=368.94 && playerY <= 368.98)) {
           //     System.out.println("hello3");
//                if (!readExcel.match){
//                    requestFocus();
//                    start(30,320.9);
//                    while (isRunning) {
//                        update();
//                        draw();
//                    }
//                }
//                // if got match in database
//                else if (readExcel.match){
//                    requestFocus();
//                    start(30,320.9);
//                    while (isRunning) {
//                        update();
//                        draw2();
//                        if(!readExcel.forSave){
//                            readExcel.forSave = true;
//                            saveButton2();}
//                    }
//                }
            //}

            // FOURTH STATION
            // 160.95 , 558.05
            //((playerX >= 160.94 && playerX <= 160.99)  && (playerY >=558.53 && playerY <= 558.57))
            //((playerX >= 160.93 && playerX <= 161.01)  && (playerY >=557.98 && playerY <= 560))
            if(((playerX >= 160.93 && playerX <= 161.01)  && (playerY >=558.08 && playerY <= 558.12))){

                System.out.println("\n****************************\nFOURTH STATION\n****************************\n");
               MainReverse.main(null);
                if(ReversePvP.status==-1) {
                     if (!readExcel.match){
                        requestFocus();
                         start(112.91,368.90);

                         saveButton.incrementLoss();
                         saveButton.steps = 30;

                        while (isRunning) {
                            update();
                            draw();
                        }
                     }
                    // if got match in database
                    else if (readExcel.match){
                        requestFocus();
                        start(112.91,368.90);

                        saveButton2.incrementLoss();
                        BombMap.steps = 30;

                        while (isRunning) {
                             update();
                            draw2();
                            if(!readExcel.forSave){
                             readExcel.forSave = true;
                                saveButton2();}
                        }
                    }
                }
            }
            coordinateX.add(playerX);
            coordinateY.add(playerY);
        }


        if(moving){
            framePlayer++;
            if(framePlayer > intervalPlayer){
                framePlayer = 0;
                indexAnimPlayer++;
                if(indexAnimPlayer > 2)
                    indexAnimPlayer = 0;
            }
        }

        if(right)
            player = playerAnimRight[indexAnimPlayer];
       else if(left)
            player = playerAnimLeft[indexAnimPlayer];
        else if(up)
            player = playerAnimUp[indexAnimPlayer];
        else if(down)
            player = playerAnimDown[indexAnimPlayer];
        else
            player = playerAnimDown[1];
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            right = true;
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
            left = true;
        if(e.getKeyCode() == KeyEvent.VK_UP)
            up = true;
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
            down = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            right = false;
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
            left = false;
        if(e.getKeyCode() == KeyEvent.VK_UP)
            up = false;
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
            down = false;
    }

    public void pop(){
        click = new Clicks();
    }

    public void loginPrompt(){
        login = new Login();
        while(login.flag!=1){
            System.out.print("");
        }
    }

    public void saveButton(){
        saveButton = new SaveButton();
        ArrayList<Integer> pressedKeys = new ArrayList<>() ;
        ArrayList<Integer> releasedKeys = new ArrayList<>();
        ArrayList<Integer> at30 = new ArrayList<>();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {

                try{
                    if(saveButton.steps==62){

                    }
                }
                catch(IndexOutOfBoundsException exception){

                }
                int keyCode = e.getKeyCode();
                if(saveButton.steps==20){

                    releasedKeys.add(e.getKeyCode());
                    if(releasedKeys.size()==2){
                        saveButton.switchDirection();
                        releasedKeys.clear();
                    }
                }

                else if (saveButton.steps == 26) {
                    if(click.clickTimes()>=2 && click.clickTimes()<=4){
                        pressedKeys.add(e.getKeyCode());
                        if (pressedKeys.size() == 2){
                            saveButton.switchDirection();
                            pressedKeys.clear();
                        }
                    }
                    else
                        saveButton.switchDirection();
                }
                else if (saveButton.steps == 30) {
                    if(click.clickTimes()>=2 && click.clickTimes()<=4){
                        at30.add(e.getKeyCode());
                        if (at30.size() == 2){
                            saveButton.switchDirection();
                            at30.clear();
                        }
                    }
                    else
                        saveButton.switchDirection();
                }

                else if (e.getKeyCode()==37) { // left
                    saveButton.switchDirection();


                } else if (e.getKeyCode()==38) {   // up
                    saveButton.switchDirection();

                } else if (e.getKeyCode()==39) {   // right
                    saveButton.switchDirection();

                } else if (e.getKeyCode()==40) {   //down
                    saveButton.switchDirection();

                }

            }
        });
    }
    public void saveButton2(){
        saveButton2 = new SaveButton2();
        ArrayList<Integer> pressedKeys = new ArrayList<>() ;
        ArrayList<Integer> releasedKeys = new ArrayList<>();
        ArrayList<Integer> at30 = new ArrayList<>();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {

                try{
                    if(BombMap.steps==62){

                    }
                }
                catch(IndexOutOfBoundsException exception){

                }
                int keyCode = e.getKeyCode();
                if(BombMap.steps==20){
                    releasedKeys.add(e.getKeyCode());
                    if(releasedKeys.size()==2){
                        saveButton2.switchDirection();
                        releasedKeys.clear();
                    }
                }

                else if (BombMap.steps == 26) {
                    if(ReadExcel.pathNumber.equals("1") || ReadExcel.pathNumber.equals("2") || ReadExcel.pathNumber.equals("3")){
                        pressedKeys.add(e.getKeyCode());
                        if (pressedKeys.size() == 2){
                            saveButton2.switchDirection();
                            pressedKeys.clear();
                        }
                    }
                    else
                        saveButton2.switchDirection();
                }
                else if (BombMap.steps == 30) {
                    if(ReadExcel.pathNumber.equals("1") || ReadExcel.pathNumber.equals("2") || ReadExcel.pathNumber.equals("3")){
                        at30.add(e.getKeyCode());
                        if (at30.size() == 2){
                            saveButton2.switchDirection();
                            at30.clear();
                        }
                    }
                    else
                        saveButton2.switchDirection();
                }
                else if (e.getKeyCode()==37) { // left
                    saveButton2.switchDirection();


                } else if (e.getKeyCode()==38) {   // up
                    saveButton2.switchDirection();

                } else if (e.getKeyCode()==39) {   // right
                    saveButton2.switchDirection();

                } else if (e.getKeyCode()==40) {   //down
                    saveButton2.switchDirection();

                }
            }
        });
    }

    public void readCSV(){
        readExcel = new ReadExcel();
    }

    public void errorMessage(){
        errorMessage = new ErrorMessage();
    }

    public void showLeaderboard(){
        leaderboard = new Leaderboard();
    }

    public void writeLeaderboard(){
        try (FileWriter writer = new FileWriter(BombMap.leaderboardDatabase, true)) {
            // Append data
            if(!readExcel.match)
                writer.append(Login.username).append(",").append(String.valueOf(saveButton.loss)).append(System.lineSeparator());
            else if(readExcel.match)
                writer.append(Login.username).append(",").append(String.valueOf(BombMap.loss)).append(System.lineSeparator());
        } catch (IOException x) {
            x.printStackTrace();
        }
    }

    public void countSteps(){
        ArrayList<Integer> pressedKeys = new ArrayList<>() ;
        ArrayList<Integer> releasedKeys = new ArrayList<>();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {

                try{
                    if(stepsCount.currentStepCount==62){

                    }
                }
                catch(IndexOutOfBoundsException exception){

                }
                int keyCode = e.getKeyCode();
                if(stepsCount.currentStepCount==20){
                    releasedKeys.add(e.getKeyCode());
                    if(releasedKeys.size()==2)
                        stepsCount.switchDirection();
                }
                else if(stepsCount.currentStepCount==26){
                    pressedKeys.add(e.getKeyCode());
                    if(pressedKeys.size()==2)
                        stepsCount.switchDirection();
                }
                else if (e.getKeyCode()==37) { // left
                    stepsCount.switchDirection();


                } else if (e.getKeyCode()==38) {   // up
                    stepsCount.switchDirection();

                } else if (e.getKeyCode()==39) {   // right
                    stepsCount.switchDirection();

                } else if (e.getKeyCode()==40) {   //down
                    stepsCount.switchDirection();

                }

            }
        });
    }
}


class DirectionFrame extends JFrame {
    private JLabel directionLabel;
    private ArrayList<String> directions;
    public int currentDirectionIndex;
    private int mouseX, mouseY;
    private ArrayList<Integer> keycode;

    public DirectionFrame(ArrayList<String> directions) {
        this.directions = directions;
        this.currentDirectionIndex = 0;

        directionLabel = new JLabel(directions.get(currentDirectionIndex));
        add(directionLabel);


        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 100);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("Direction");
        // Add mouse listeners for dragging
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                int deltaX = e.getX() - mouseX;
                int deltaY = e.getY() - mouseY;
                setLocation(getX() + deltaX, getY() + deltaY);
            }
        });


    }

    public void switchDirection() {
        // currentDirectionIndex++;
        if (currentDirectionIndex == 61) {
            directionLabel.setText("Destination reached!"+" ("+(currentDirectionIndex+1)+" Steps)");
        }
        else {
            currentDirectionIndex++;
            directionLabel.setText(directions.get(currentDirectionIndex) + " (" + currentDirectionIndex + " Steps)");
        }

    }

}

class Clicks implements ActionListener{
    private JLabel label;
    public int i = 1;
    private boolean confirmMou = false;
    private boolean flag;
    Clicks(){
        JDialog dialog = new JDialog();
        dialog.setTitle("Shortest Paths");
        dialog.setSize(200,200);

        label= new JLabel("ARE YOU READY?");
        label.setBounds(45, 50, 200, 30);

        JButton button = new JButton("Show Path");
        button.setBounds(45, 80, 100, 30);
        button.addActionListener(this);

        JButton confirm = new JButton("Confirm Path");
        confirm.setBounds(30, 120, 130, 30);
        confirm.addActionListener(e -> {
            try (FileWriter writer = new FileWriter(BombMap.mainDatabase, true)) {
                // Append data
                writer.append(String.valueOf(i-1)).append(",");
                //System.out.println("Data appended to the CSV file.");
            } catch (IOException x) {
                x.printStackTrace();
            }
            confirmMou = true;
            dialog.dispose();
        });

        dialog.add(confirm);
        dialog.add(label);
        dialog.add(button);

        dialog.setLayout(null);
        dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //dialog.setLayout(new FlowLayout());
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);


    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(i==7)
            i = 1;
        flag = true;
        label.setText("  PATH "+i+" ( 62 steps )");
        i++;

        isClicked();
    }

    public boolean isClicked(){
        return flag;
    }
    public int clickTimes(){
        return i;
    }

    public boolean getConfirmMou(){
        return confirmMou;
    }

    public void setConfirmMou(boolean confirmMou){
        this.confirmMou = confirmMou;
    }


}



class Login{
    public int flag = 0;
    public static  String username;
    public static  String password;
    public static boolean invalid;
    private JLabel error = new JLabel("");
    Login(){
        if(invalid){
            delete();
            invalid = false;

            error = new JLabel("Wrong Password!");

        }
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Create a JPanel to contain the login components
        JPanel panel = new JPanel();

        // Create JLabels for username and password
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        // Create JTextFields for username and password input
        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        // Create a JButton for login
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            username = usernameField.getText();
            password = new String(passwordField.getPassword());
            flag = 1;
            if(!username.equals("") && !password.equals(""))
                write(username,password);
            frame.dispose();


        });

        // Add the labels, fields, and button to the panel
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);

        panel.add(error);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Add the panel to the frame
        frame.add(panel);


        // Set the size and visibility of the frame
        frame.setSize(300, 150);
        frame.setVisible(true);
    }

    public void write(String username, String password){
        try (FileWriter writer = new FileWriter(BombMap.mainDatabase, true)) {
            // Append data

            writer.append(username).append(",").append(password).append(",");

           // System.out.println("Data appended to the CSV file.");
        } catch (IOException e) {
            e.printStackTrace();
        }


        try (FileWriter writer = new FileWriter(BombMap.stepsLossDatabase, true)) {
            // Append data
            writer.append(username).append(",");
            // System.out.println("Data appended to the CSV file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void delete(){
        String entryToDelete = username+","+password; // Specify the entry you want to delete

        // Read the CSV file and store its content in a list
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(BombMap.mainDatabase))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if(data.length!=2)
                    lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Remove the desired entry from the list
        //lines.remove(entryToDelete);

        // Write the updated content back to the CSV file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BombMap.mainDatabase))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();

        }



        // Read the CSV file and store its content in a list
        List<String> linesSteps = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(BombMap.stepsLossDatabase))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if(data.length!=1)
                    linesSteps.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Write the updated content back to the CSV file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BombMap.stepsLossDatabase))) {
            for (String line : linesSteps) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

// if no match, new game
class SaveButton implements ActionListener{
    public boolean pressed;
    private JDialog dialog;
    private JLabel label1;
    private JLabel label2;
    private JLabel stepLoss;
    public int steps;
    public int loss;
    SaveButton(){
        dialog = new JDialog();
        dialog.setTitle(Login.username);
        dialog.setSize(200,200);

        stepLoss = new JLabel();
        stepLoss.setText(steps+ " steps, "+loss+" loss");
        stepLoss.setBounds(60,50,100,30);
        dialog.add(stepLoss);


        JButton button = new JButton("SAVE");
        button.setBounds(45, 120, 100, 30);
        button.addActionListener(this);

        dialog.add(button);
        dialog.setLayout(null);
        dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //dialog.setLayout(new FlowLayout());
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        write();
        BombMap.isRunning = false;

       dialog.dispose();
       BombMap.leaderboard.close();
        BombMap.w.dispose();

    }

    public void write(){
        try (FileWriter writer = new FileWriter(BombMap.mainDatabase, true)) {
            // Append data
            Double y =  BombMap.coordinateY.get(BombMap.coordinateY.size()-1);
            Double x =  BombMap.coordinateX.get(BombMap.coordinateX.size()-1);

            writer.append(x.toString()).append(",").append(y.toString()).append(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }


        try (FileWriter writer = new FileWriter(BombMap.stepsLossDatabase, true)) {
            // Append data
            writer.append(String.valueOf(String.valueOf(loss))).append(",").append(String.valueOf(steps)).append(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void incrementLoss() {
        loss++;
        stepLoss.setText(steps+" steps,"+loss+" loss");
    }

    public void switchDirection() {
        // currentDirectionIndex++;
        if (steps == 62) {
            stepLoss.setText("Game Ended!");
        }
        else {
            steps++;
            stepLoss.setText(steps+" steps,"+loss+" loss");
        }
    }


}

// if username and password match database
class SaveButton2 implements ActionListener{
    public boolean pressed;
    private JDialog dialog;
    private JLabel stepLoss;
    SaveButton2(){
        dialog = new JDialog();
        dialog.setTitle(ReadExcel.username);
        dialog.setSize(200,200);

        stepLoss = new JLabel();
        stepLoss.setText(BombMap.steps+ " steps, "+BombMap.loss+" loss");
        stepLoss.setBounds(60,50,100,30);
        dialog.add(stepLoss);


        JButton button = new JButton("SAVE");
        button.setBounds(45, 120, 100, 30);
        button.addActionListener(this);

        dialog.add(button);
        dialog.setLayout(null);
        dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //dialog.setLayout(new FlowLayout());
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
        write();
        BombMap.isRunning = false;
        dialog.dispose();
        BombMap.leaderboard.close();
        BombMap.w.dispose();
        }
        catch(IndexOutOfBoundsException exception){
           // System.out.println("nono");
            try (FileWriter writer = new FileWriter(BombMap.mainDatabase, true)) {
                // Append data
                Double y =  623.00;
                Double x =  302.96;

                writer.append(ReadExcel.pathNumber).append(",").append(x.toString()).append(",").append(y.toString()).append(System.lineSeparator());
            } catch (IOException exception1) {
                exception1.printStackTrace();
            }


            try (FileWriter writer = new FileWriter(BombMap.stepsLossDatabase, true)) {
                // Append data
                writer.append(String.valueOf(String.valueOf(BombMap.loss))).append(",").append(String.valueOf(BombMap.steps)).append(System.lineSeparator());
            } catch (IOException exception2) {
                exception2.printStackTrace();
            }
            BombMap.isRunning = false;
            dialog.dispose();
            BombMap.leaderboard.close();
            BombMap.w.dispose();
        }

    }

    public void write(){
        try (FileWriter writer = new FileWriter(BombMap.mainDatabase, true)) {
            // Append data
            Double y =  BombMap.coordinateY.get(BombMap.coordinateY.size()-1);
            Double x =  BombMap.coordinateX.get(BombMap.coordinateX.size()-1);

            writer.append(ReadExcel.pathNumber).append(",").append(x.toString()).append(",").append(y.toString()).append(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter(BombMap.stepsLossDatabase, true)) {
            // Append data
            writer.append(String.valueOf(String.valueOf(BombMap.loss))).append(",").append(String.valueOf(BombMap.steps)).append(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void incrementLoss() {
        BombMap.loss++;
        stepLoss.setText(BombMap.steps+" steps,"+BombMap.loss+" loss");
    }

    public void switchDirection() {
        // currentDirectionIndex++;
        if (BombMap.steps == 62) {
            stepLoss.setText("Game Ended!");
        }
        else {
            BombMap.steps++;
            stepLoss.setText(BombMap.steps+" steps,"+BombMap.loss+" loss");
        }
    }
}

class ReadExcel{
    public  boolean invalidPassword = false;
    public static String username;
    public static String password;
    public static String pathNumber;
    public static double xCoordinate;
    public static double yCoordinate;
    public static int steps;
    public static int loss;
    public static String usernameSteps;
    public boolean forSave = false;
    public boolean match = false;
    ArrayList<String> lines = new ArrayList<>();
    ArrayList<String> stepsAndLoss = new ArrayList<>();
    ReadExcel(){
        try (BufferedReader reader = new BufferedReader(new FileReader(BombMap.mainDatabase))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
               lines.add(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        Collections.reverse(lines);
        for(String line:lines){
            String[] data = line.split(",");
                if (data.length == 5){
                    username = data[0];
                     password = data[1];
                     pathNumber = data[2];
                    xCoordinate = Double.parseDouble(data[3]);
                     yCoordinate = Double.parseDouble(data[4]);
                    if (username.equals(Login.username) && password.equals(Login.password)) {
                        invalidPassword = false;
                        match = true;
                        break;
                    // match found, load the saved game
                    }
                    else if(username.equals(Login.username) && !password.equals(Login.password)){
                        invalidPassword = true;
                        Login.invalid = true;
                    }
            }
                else
                    continue;
        }
        // no match found, load new game

        try (BufferedReader reader = new BufferedReader(new FileReader(BombMap.stepsLossDatabase))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                stepsAndLoss.add(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Collections.reverse(stepsAndLoss);
        for(String line:stepsAndLoss){
            String[] data = line.split(",");
            if(data.length==3){
                usernameSteps = data[0];
                loss = Integer.parseInt(data[1]);
                steps = Integer.parseInt(data[2]);
                if(usernameSteps.equals(Login.username)){
                    BombMap.loss = loss;
                    BombMap.steps = steps;
                    break;
                }
            }
        }
    }

}


class ErrorMessage{
    public static JDialog dialog;
    ErrorMessage(){
        dialog = new JDialog();
        dialog.setTitle("!");

        JLabel label = new JLabel("Wrong Password");
        dialog.add(label);
        dialog.setVisible(true);
    }
}

class StepsCount extends JFrame{
    private JLabel count;
    public int currentStepCount;
    public int loss;
    StepsCount(){
        count = new JLabel();
        add(count);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 100);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("Steps & Loss");
    }
    public void switchDirection() {
        // currentDirectionIndex++;
        if (currentStepCount == 62) {
            count.setText("Game Ended!");
        }
        else {
            currentStepCount++;
            count.setText(currentStepCount+" steps,"+loss+" loss");

        }
    }

    public void incrementLoss() {
        loss++;
        count.setText(currentStepCount+" steps,"+loss+" loss");

    }

    public void close(){
        dispose();
    }


}

class Leaderboard{
    public JFrame frame;
    Leaderboard(){
        frame = new JFrame("Leaderboard");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JTable table = new JTable();
        table.setAutoCreateRowSorter(true);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(BombMap.leaderboardDatabase));
            String line;
            DefaultTableModel tableModel = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Make all cells non-editable
                }
            };

            // Read the header row
            if ((line = reader.readLine()) != null) {
                String[] headers = line.split(",");
                tableModel.setColumnIdentifiers(headers);
            }

            // Read data rows
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");
                tableModel.addRow(rowData);
            }

            table.setModel(tableModel);

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);
        frame.pack();
        frame.setVisible(true);
    }

    public void close(){
        frame.dispose();
    }
}