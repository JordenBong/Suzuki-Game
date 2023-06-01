
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author USER
 */
public class TicTaeToe extends javax.swing.JFrame {

    private String startGame = "X";
    private int xCount = 0;
    private int oCount = 0;
    boolean checker;
    Stack <Integer> slots = new Stack<>();
    String [][] board = {{"","",""},{"","",""},{"","",""}};
    public TicTaeToe() {
        initComponents();
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                int x = JOptionPane.showConfirmDialog(null, "do you really want close app? ",
                        "close", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
                if (x == JOptionPane.YES_OPTION){
                    e.getWindow().dispose();
                }
            }
            
        });
    }

    
    private void gameScore(){
        jLabelPlayerX.setText(String.valueOf(xCount));
        jLabelPlayerO.setText(String.valueOf(oCount));
    }
    
    private void choose_a_player(){
        if(startGame.equalsIgnoreCase("X")){
            startGame = "O";
        }
        else{
            startGame = "X";
        }
    }
    
    
    
    private boolean winningGame(){
        String b1 = jButton1.getText();
        String b2 = jButton2.getText();
        String b3 = jButton3.getText();
        String b4 = jButton4.getText();
        String b5 = jButton5.getText();
        String b6 = jButton6.getText();
        String b7 = jButton7.getText();
        String b8 = jButton8.getText();
        String b9 = jButton9.getText();
        
        String temp="";
        
        String[][] board = {{b1,b2,b3}, {b4,b5,b6}, {b7,b8,b9}};
        for(int row=0; row<3; row++){
            if((board[row][0].equals("X") && board[row][1].equals("X") && board[row][2].equals("X")) 
                    ||(board[row][0].equals("O") && board[row][1].equals("O") && board[row][2].equals("O"))){
                if(row == 0){
                    jButton1.setBackground(Color.ORANGE);
                    jButton2.setBackground(Color.ORANGE);
                    jButton3.setBackground(Color.ORANGE);
                    temp = b1;
                }else if (row == 1){
                    jButton4.setBackground(Color.ORANGE);
                    jButton5.setBackground(Color.ORANGE);
                    jButton6.setBackground(Color.ORANGE);
                    temp = b4;
                }else{
                    jButton7.setBackground(Color.ORANGE);
                    jButton8.setBackground(Color.ORANGE);
                    jButton9.setBackground(Color.ORANGE);
                    temp = b7;
                }
                
                if(temp.equals("X")){
                    JOptionPane.showMessageDialog(this, "Player O Wins~", "Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
                    oCount++;
                    gameScore();
                    return true;
                }else{
                    JOptionPane.showMessageDialog(this, "Player X Wins~", "Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
                    xCount++;
                    gameScore();
                    return true;
                }
                
            }
        }
        for(int col=0; col<3; col++){
            if((board[0][col].equals("X") && board[1][col].equals("X") && board[2][col].equals("X")) 
                    ||(board[0][col].equals("O") && board[1][col].equals("O") && board[2][col].equals("O"))){
                if(col == 0){
                    jButton1.setBackground(Color.ORANGE);
                    jButton4.setBackground(Color.ORANGE);
                    jButton7.setBackground(Color.ORANGE);
                    temp = b1;
                }else if (col == 1){
                    jButton2.setBackground(Color.ORANGE);
                    jButton5.setBackground(Color.ORANGE);
                    jButton8.setBackground(Color.ORANGE);
                    temp = b2;
                }else{
                    jButton3.setBackground(Color.ORANGE);
                    jButton6.setBackground(Color.ORANGE);
                    jButton9.setBackground(Color.ORANGE);
                    temp = b3;
                }
               
                
                if(temp.equals("X")){
                    JOptionPane.showMessageDialog(this, "Player O Wins~", "Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
                    oCount++;
                    gameScore();
                    return true;
                }else{
                    JOptionPane.showMessageDialog(this, "Player X Wins~", "Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
                    xCount++;
                    gameScore();
                    return true;
                }
            }
        }
        
        if ((board[0][0].equals("X") && board[1][1].equals("X") && board[2][2].equals("X")) 
                || (board[0][0].equals("O") && board[1][1].equals("O") && board[2][2].equals("O"))){
            jButton1.setBackground(Color.ORANGE);
            jButton5.setBackground(Color.ORANGE);
            jButton9.setBackground(Color.ORANGE);
            temp = b1;
            
            if(temp.equals("X")){
                JOptionPane.showMessageDialog(this, "Player O Wins~", "Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
                oCount++;
                gameScore();
                return true;
            }else{
                JOptionPane.showMessageDialog(this, "Player X Wins~", "Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
                xCount++;
                gameScore();
                return true;
            }
            
        }
        
        if ((board[0][2].equals("X") && board[1][1].equals("X") && board[2][0].equals("X")) 
                || (board[0][2].equals("O") && board[1][1].equals("O") && board[2][0].equals("O"))){
            jButton3.setBackground(Color.ORANGE);
            jButton5.setBackground(Color.ORANGE);
            jButton7.setBackground(Color.ORANGE);
            temp = b3;
            
            if(temp.equals("X")){
                JOptionPane.showMessageDialog(this, "Player O Wins~", "Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
                oCount++;
                gameScore();
                return true;
            }else{
                JOptionPane.showMessageDialog(this, "Player X Wins~", "Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
                xCount++;
                gameScore();
                return true;
            }
        }
        
        if(slots.size() == 9){
            JOptionPane.showMessageDialog(this, "TIE~", "Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }
    
    public void enableButton(boolean ans){
        jButton1.setEnabled(ans);
        jButton2.setEnabled(ans);
        jButton3.setEnabled(ans);
        jButton4.setEnabled(ans);
        jButton5.setEnabled(ans);
        jButton6.setEnabled(ans);
        jButton7.setEnabled(ans);
        jButton8.setEnabled(ans);
        jButton9.setEnabled(ans);
    }
    public void removeMove(Integer pos){
        switch(pos){
            case 1:
                board[0][0] = "";
                jButton1.setText("");
                break;
            case 2:
                board[0][1] = "";
                jButton2.setText("");
                break;
            case 3:
                board[0][2] = "";
                jButton3.setText("");
                break;
            case 4:
                board[1][0] = "";
                jButton4.setText("");
                break;
            case 5:
                board[1][1] = "";
                jButton5.setText("");
                break;
            case 6:
                board[1][2] = "";
                jButton6.setText("");
                break;
            case 7:
                board[2][0] = "";
                jButton7.setText("");
                break;
            case 8:
                board[2][1] = "";
                jButton8.setText("");
                break;
            case 9:
                board[2][2] = "";
                jButton9.setText("");
                break;
        }
    }
    
    public void takeBackMove(){
        if (slots.isEmpty()){
            JOptionPane.showMessageDialog(this, "You can't make the back move anymore.", "Reverse Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            Integer pos = slots.pop();
            removeMove(pos);
            pos = slots.pop();
            removeMove(pos);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabelPlayerX = new javax.swing.JLabel();
        jLabelPlayerO = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        Reset = new javax.swing.JButton();
        Exit = new javax.swing.JButton();
        backMove = new javax.swing.JButton();
        jPanel24 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(95, 158, 160));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(95, 158, 160));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(95, 158, 160));
        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(95, 158, 160));
        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel5.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, 570, 380));

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, 570, 380));

        jPanel7.setBackground(new java.awt.Color(95, 158, 160));
        jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel8.setBackground(new java.awt.Color(95, 158, 160));
        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel7.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, 570, 380));

        jPanel2.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, 570, 380));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 70)); // NOI18N
        jButton1.setToolTipText("");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 150, 110));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 70)); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, 150, 110));

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 70)); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 20, 150, 110));

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 70)); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 150, 110));

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 70)); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 140, 150, 110));

        jButton6.setFont(new java.awt.Font("Tahoma", 1, 70)); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 140, 150, 110));

        jButton7.setFont(new java.awt.Font("Tahoma", 1, 70)); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, 150, 110));

        jButton9.setFont(new java.awt.Font("Tahoma", 1, 70)); // NOI18N
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 260, 150, 110));

        jButton8.setFont(new java.awt.Font("Tahoma", 1, 70)); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 260, 150, 110));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 570, 380));

        jPanel3.setBackground(new java.awt.Color(95, 158, 160));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(95, 158, 160));
        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, 570, 380));

        jPanel9.setBackground(new java.awt.Color(95, 158, 160));
        jPanel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel10.setBackground(new java.awt.Color(95, 158, 160));
        jPanel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel11.setBackground(new java.awt.Color(95, 158, 160));
        jPanel11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel10.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, 570, 380));

        jPanel9.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, 570, 380));

        jPanel12.setBackground(new java.awt.Color(95, 158, 160));
        jPanel12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel13.setBackground(new java.awt.Color(95, 158, 160));
        jPanel13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel12.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, 570, 380));

        jPanel9.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, 570, 380));

        jPanel3.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(-310, -60, 520, 60));

        jPanel14.setBackground(new java.awt.Color(95, 158, 160));
        jPanel14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel15.setBackground(new java.awt.Color(95, 158, 160));
        jPanel15.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel16.setBackground(new java.awt.Color(95, 158, 160));
        jPanel16.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel15.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, 570, 380));

        jPanel14.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, 570, 380));

        jPanel17.setBackground(new java.awt.Color(95, 158, 160));
        jPanel17.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel18.setBackground(new java.awt.Color(95, 158, 160));
        jPanel18.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel17.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, 570, 380));

        jPanel14.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, 570, 380));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel2.setText("Player O:");
        jPanel14.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 220, 50));

        jLabelPlayerX.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabelPlayerX.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelPlayerX.setText("0");
        jLabelPlayerX.setOpaque(true);
        jPanel14.add(jLabelPlayerX, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 30, 220, 50));

        jLabelPlayerO.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabelPlayerO.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelPlayerO.setText("0");
        jLabelPlayerO.setOpaque(true);
        jPanel14.add(jLabelPlayerO, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 90, 220, 50));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel3.setText("Player X:");
        jPanel14.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 220, 50));

        jPanel3.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 550, 170));

        jPanel19.setBackground(new java.awt.Color(95, 158, 160));
        jPanel19.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel19.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel20.setBackground(new java.awt.Color(95, 158, 160));
        jPanel20.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel20.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel21.setBackground(new java.awt.Color(95, 158, 160));
        jPanel21.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel21.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel20.add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, 570, 380));

        jPanel19.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, 570, 380));

        jPanel22.setBackground(new java.awt.Color(95, 158, 160));
        jPanel22.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel22.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel23.setBackground(new java.awt.Color(95, 158, 160));
        jPanel23.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel23.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel22.add(jPanel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, 570, 380));

        jPanel19.add(jPanel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, 570, 380));

        Reset.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        Reset.setText("Reset");
        Reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetActionPerformed(evt);
            }
        });
        jPanel19.add(Reset, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 270, 70));

        Exit.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        Exit.setText("Exit");
        Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitActionPerformed(evt);
            }
        });
        jPanel19.add(Exit, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 100, 250, 70));

        backMove.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        backMove.setText("Back Move");
        backMove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backMoveActionPerformed(evt);
            }
        });
        jPanel19.add(backMove, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 530, 70));

        jPanel3.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 550, 180));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, 570, 380));

        jPanel24.setBackground(new java.awt.Color(95, 158, 160));
        jPanel24.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel24.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel25.setBackground(new java.awt.Color(95, 158, 160));
        jPanel25.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel25.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel26.setBackground(new java.awt.Color(95, 158, 160));
        jPanel26.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel26.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel25.add(jPanel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, 570, 380));

        jPanel24.add(jPanel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, 570, 380));

        jPanel27.setBackground(new java.awt.Color(95, 158, 160));
        jPanel27.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel27.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel28.setBackground(new java.awt.Color(95, 158, 160));
        jPanel28.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel28.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel27.add(jPanel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, 570, 380));

        jPanel24.add(jPanel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, 570, 380));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(240, 240, 240));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Java Advanced Tic Tae Toe Game");
        jLabel1.setToolTipText("");
        jPanel24.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 1150, 50));

        jPanel1.add(jPanel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 1170, 60));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 470));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(jButton2.getText().isBlank()){    
            jButton2.setText(startGame);
            if(startGame.equalsIgnoreCase("X")){
                checker = false;
                board[0][1] = "X";
            }else{
                checker = true;
                board[0][1] = "O";
            }
            slots.push(2);
            choose_a_player();
            if(winningGame()){
                enableButton(false);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Please choose another box", "Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if(jButton4.getText().isBlank()){    
            jButton4.setText(startGame);
            if(startGame.equalsIgnoreCase("X")){
                checker = false;
                board[1][0] = "X";
            }else{
                checker = true;
                board[1][0] = "X";
            }
            slots.push(4);
            choose_a_player();
            if(winningGame()){
                enableButton(false);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Please choose another box", "Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if(jButton5.getText().isBlank()){    
            jButton5.setText(startGame);
            if(startGame.equalsIgnoreCase("X")){
                checker = false;
                board[1][1] = "X";
            }else{
                checker = true;
                board[1][1] = "O";
            }
            slots.push(5);
            choose_a_player();
            if(winningGame()){
                enableButton(false);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Please choose another box", "Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if(jButton6.getText().isBlank()){    
            jButton6.setText(startGame);
            if(startGame.equalsIgnoreCase("X")){
                checker = false;
                board[1][2] = "X";
            }else{
                checker = true;
                board[1][2] = "O";
            }
            slots.push(6);
            choose_a_player();
            if(winningGame()){
                enableButton(false);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Please choose another box", "Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if(jButton7.getText().isBlank()){    
            jButton7.setText(startGame);
            if(startGame.equalsIgnoreCase("X")){
                checker = false;
                board[2][0] = "X";
            }else{
                checker = true;
                board[2][0] = "O";
            }
            slots.push(7);
            choose_a_player();
            if(winningGame()){
                enableButton(false);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Please choose another box", "Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        if(jButton8.getText().isBlank()){    
            jButton8.setText(startGame);
            if(startGame.equalsIgnoreCase("X")){
                checker = false;
                board[2][1] = "X";
            }else{
                checker = true;
                board[2][1] = "O";
            }
            slots.push(8);
            choose_a_player();
            if(winningGame()){
                enableButton(false);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Please choose another box", "Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        if(jButton9.getText().isBlank()){
            jButton9.setText(startGame);
            if(startGame.equalsIgnoreCase("X")){
                checker = false;
                board[2][2] = "X";
            }else{
                checker = true;
                board[2][2] = "O";
            }
            slots.push(9);
            choose_a_player();
            if(winningGame()){
                enableButton(false);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Please choose another box", "Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(jButton1.getText().isBlank()){    
            jButton1.setText(startGame);
            if(startGame.equalsIgnoreCase("X")){
                checker = false;
                board[0][0] = "X";
            }else{
                checker = true;
                board[0][0] = "O";
            }
            slots.push(1);
            choose_a_player();
            if(winningGame()){
                enableButton(false);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Please choose another box", "Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private JFrame frame;
    private void ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitActionPerformed
        AiPlayer ai = new AiPlayer();
        ai.setVisible(true);
        this.setVisible(false);
//        frame = new JFrame("Exit");
//        if(JOptionPane.showConfirmDialog(frame, "Confirm if you want to exit", "Tic Tae Toe", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION){
//            System.exit(0);
//        }
    }//GEN-LAST:event_ExitActionPerformed

    private void ResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetActionPerformed
        
        slots.clear();
        enableButton(true);
        
        jButton1.setText("");
        jButton2.setText("");
        jButton3.setText("");
        jButton4.setText("");
        jButton5.setText("");
        jButton6.setText("");
        jButton7.setText("");
        jButton8.setText("");
        jButton9.setText("");
        
        jButton1.setBackground(Color.LIGHT_GRAY);
        jButton2.setBackground(Color.LIGHT_GRAY);
        jButton3.setBackground(Color.LIGHT_GRAY);
        jButton4.setBackground(Color.LIGHT_GRAY);
        jButton5.setBackground(Color.LIGHT_GRAY);
        jButton6.setBackground(Color.LIGHT_GRAY);
        jButton7.setBackground(Color.LIGHT_GRAY);
        jButton8.setBackground(Color.LIGHT_GRAY);
        jButton9.setBackground(Color.LIGHT_GRAY);
    }//GEN-LAST:event_ResetActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if(jButton3.getText().isBlank()){    
            jButton3.setText(startGame);
            if(startGame.equalsIgnoreCase("X")){
                checker = false;
                board[0][2] = "X";
            }else{
                checker = true;
                board[0][2] = "O";
            }
            slots.push(3);
            choose_a_player();
            if(winningGame()){
                enableButton(false);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Please choose another box", "Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void backMoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backMoveActionPerformed
        takeBackMove();
    }//GEN-LAST:event_backMoveActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TicTaeToe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TicTaeToe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TicTaeToe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TicTaeToe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TicTaeToe().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Exit;
    private javax.swing.JButton Reset;
    private javax.swing.JButton backMove;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelPlayerO;
    private javax.swing.JLabel jLabelPlayerX;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    // End of variables declaration//GEN-END:variables
}
