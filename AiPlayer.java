
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Stack;
import javax.swing.JOptionPane;


public class AiPlayer extends javax.swing.JFrame {

    static class Move
    {
        int row, col;
    };
    
    private static String aiPlayer = "X";
    private static String opponent = "O";
    private Stack<Integer> slots = new Stack<>();
    
    public AiPlayer() {
        initComponents();
    }

    private String[][] board = {{"", "", ""}, {"", "", ""}, {"", "", ""}};
    // This function returns true if there are moves
    // remaining on the board. It returns false if
    // there are no moves left to play.
    private Boolean isMovesLeft(String board[][])
    {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j].isBlank())
                    return true;
        return false;
    }

    // This is the evaluation function as discussed
    // in the previous article ( http://goo.gl/sJgv68 )
    private int evaluate(String b[][])
    {
        // Checking for Rows for X or O victory.
        for (int row = 0; row < 3; row++)
        {
            if (b[row][0].equalsIgnoreCase(b[row][1]) &&
                    b[row][1].equalsIgnoreCase(b[row][2]))
            {
                if (b[row][0].equalsIgnoreCase(aiPlayer))
                    return -10;
                else if (b[row][0].equalsIgnoreCase(opponent))
                    return +10;
            }
        }

        // Checking for Columns for X or O victory.
        for (int col = 0; col < 3; col++)
        {
            if (b[0][col].equalsIgnoreCase(b[1][col]) &&
                    b[1][col].equalsIgnoreCase(b[2][col]))
            {
                if (b[0][col].equalsIgnoreCase(aiPlayer))
                    return -10;

                else if (b[0][col].equalsIgnoreCase(opponent))
                    return +10;
            }
        }

        // Checking for Diagonals for X or O victory.
        if (b[0][0].equalsIgnoreCase(b[1][1]) && b[1][1].equalsIgnoreCase(b[2][2]))
        {
            if (b[0][0].equalsIgnoreCase(aiPlayer))
                return -10;
            else if (b[0][0].equalsIgnoreCase(opponent))
                return +10;
        }

        if (b[0][2].equalsIgnoreCase(b[1][1]) && b[1][1].equalsIgnoreCase(b[2][0]))
        {
            if (b[0][2].equalsIgnoreCase(aiPlayer))
                return -10;
            else if (b[0][2].equalsIgnoreCase(opponent))
                return +10;
        }

        // Else if none of them have won then return 0
        return 0;
    }

    // This is the minimax function. It considers all
    // the possible ways the game can go and returns
    // the value of the board
    private int minimax(String board[][],
                       int depth, Boolean isMax)
    {
        int score = evaluate(board);

        // If Maximizer has won the game
        // return his/her evaluated score
        if (score == 10)
            return score;

        // If Minimizer has won the game
        // return his/her evaluated score
        if (score == -10)
            return score;

        // If there are no more moves and
        // no winner then it is a tie
        if (isMovesLeft(board) == false)
            return 0;

        // If this maximizer's move
        if (isMax)
        {
            int best = -1000;

            // Traverse all cells
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    // Check if cell is empty
                    if (board[i][j].isBlank())
                    {
                        // Make the move
                        board[i][j] = aiPlayer;

                        // Call minimax recursively and choose
                        // the maximum value
                        best = Math.max(best, minimax(board,
                                depth + 1, !isMax));

                        // Undo the move
                        board[i][j]="";
                    }
                }
            }
            return best;
        }

        // If this minimizer's move
        else
        {
            int best = 1000;

            // Traverse all cells
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    // Check if cell is empty
                    if (board[i][j].isBlank())
                    {
                        // Make the move
                        board[i][j] = opponent;

                        // Call minimax recursively and choose
                        // the minimum value
                        best = Math.min(best, minimax(board,
                                depth + 1, !isMax));

                        // Undo the move
                        board[i][j] = "";
                    }
                }
            }
            return best;
        }
    }

    private int getRandom(String mode){
        ArrayList<Integer> list = new ArrayList<>();
        if(mode.equalsIgnoreCase("hard")){
            list.addAll(Arrays.asList(1,1,1,1,1,1,1,1,1,-1));
        }
        else if (mode.equalsIgnoreCase("medium")){
            list.addAll(Arrays.asList(1,1,1,1,1,-1,-1,-1,-1,-1));
        }
        else {
            list.addAll(Arrays.asList(-1,-1,-1,-1,-1,-1,-1,-1,1,-1));
        }
        int a = new Random().nextInt(list.size());
        return list.get(a);
    }

    // This will return the best possible
    // move for the player
    private Move findBestMove(String board[][], String mode)
    {
        int bestVal = -1000;
        Move bestMove = new Move();
        bestMove.row = -1;
        bestMove.col = -1;

        // Traverse all cells, evaluate minimax function
        // for all empty cells. And return the cell
        // with optimal value.
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                // Check if cell is empty
                if (board[i][j].isBlank())
                {
                    // Make the move
                    board[i][j] = aiPlayer;

                    // compute evaluation function for this
                    // move.
                    int moveVal = minimax(board, 0, false);
                    moveVal *= getRandom(mode);

                    // Undo the move
                    board[i][j] = "";

                    // If the value of the current move is
                    // more than the best value, then update
                    // best/
                    if (moveVal > bestVal)
                    {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }

        //System.out.printf("The value of the best Move " +
        //        "is : %d\n\n", bestVal);

        return bestMove;
    }
    private void aiMakeMove(Move bestMove){
        if(bestMove.row == 0 && bestMove.col == 0){
            board[0][0] = aiPlayer;
            jButton1.setText(aiPlayer);
            slots.push(1);
            winningGame();
        }
        else if (bestMove.row == 0 && bestMove.col == 1){
            board[0][1] = aiPlayer;
            jButton2.setText(aiPlayer);
            slots.push(2);
            winningGame();
        }
        else if (bestMove.row == 0 && bestMove.col == 2){
            board[0][2] = aiPlayer;
            jButton3.setText(aiPlayer);
            slots.push(3);
            winningGame();
        }
        else if (bestMove.row == 1 && bestMove.col == 0){
            board[1][0] = aiPlayer;
            jButton4.setText(aiPlayer);
            slots.push(4);
            winningGame();
        }
        else if (bestMove.row == 1 && bestMove.col == 1){
            board[1][1] = aiPlayer;
            jButton5.setText(aiPlayer);
            slots.push(5);
            winningGame();
        }
        else if (bestMove.row == 1 && bestMove.col == 2){
            board[1][2] = aiPlayer;
            jButton6.setText(aiPlayer);
            slots.push(6);
            winningGame();
        }
        else if (bestMove.row == 2 && bestMove.col == 0){
            board[2][0] = aiPlayer;
            jButton7.setText(aiPlayer);
            slots.push(7);
            winningGame();
        }
        else if (bestMove.row == 2 && bestMove.col == 1){
            board[2][1] = aiPlayer;
            jButton8.setText(aiPlayer);
            slots.push(8);
            winningGame();
        }
        else if (bestMove.row == 2 && bestMove.col == 2){
            board[2][2] = aiPlayer;
            jButton9.setText(aiPlayer);
            slots.push(9);
            winningGame();
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
                    JOptionPane.showMessageDialog(this, "Player Wins~", "Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
                    return true;
                }else{
                    JOptionPane.showMessageDialog(this, "CPU Wins~", "Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
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
                    JOptionPane.showMessageDialog(this, "Player Wins~", "Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
                    return true;
                }else{
                    JOptionPane.showMessageDialog(this, "CPU Wins~", "Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
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
                JOptionPane.showMessageDialog(this, "Player Wins~", "Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }else{
                JOptionPane.showMessageDialog(this, "CPU Wins~", "Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
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
                JOptionPane.showMessageDialog(this, "Player Wins~", "Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
                return true;
                
            }else{
                JOptionPane.showMessageDialog(this, "CPU Wins~", "Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
        }
        
        if(slots.size() == 9){
            JOptionPane.showMessageDialog(this, "TIE~", "Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        reset = new javax.swing.JButton();
        backMove = new javax.swing.JButton();
        friendMode = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(95, 158, 160));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(95, 158, 160));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(95, 158, 160));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 60)); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 150, 110));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 60)); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 150, 110));

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 60)); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, 150, 110));

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 60)); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 130, 150, 110));

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 60)); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 130, 150, 110));

        jButton6.setFont(new java.awt.Font("Tahoma", 1, 60)); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 130, 150, 110));

        jButton7.setFont(new java.awt.Font("Tahoma", 1, 60)); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 250, 150, 110));

        jButton8.setFont(new java.awt.Font("Tahoma", 1, 60)); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 250, 150, 110));

        jButton9.setFont(new java.awt.Font("Tahoma", 1, 60)); // NOI18N
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 250, 150, 110));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 600, 370));

        jPanel3.setBackground(new java.awt.Color(95, 158, 160));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 45)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(240, 240, 240));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Reverse / Misere Tic Tae Toe");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(219, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 783, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(174, 174, 174))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 1180, 70));

        jPanel4.setBackground(new java.awt.Color(95, 158, 160));
        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel5.setBackground(new java.awt.Color(95, 158, 160));
        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jComboBox1.setFont(new java.awt.Font("Tahoma", 1, 50)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Easy", "Medium", "Hard" }));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 50)); // NOI18N
        jLabel2.setText("Mode: ");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(94, 94, 94))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32))
        );

        jPanel6.setBackground(new java.awt.Color(95, 158, 160));
        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        reset.setFont(new java.awt.Font("Tahoma", 1, 40)); // NOI18N
        reset.setText("Reset");
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });

        backMove.setFont(new java.awt.Font("Tahoma", 1, 40)); // NOI18N
        backMove.setText("Back Move");
        backMove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backMoveActionPerformed(evt);
            }
        });

        friendMode.setFont(new java.awt.Font("Tahoma", 1, 40)); // NOI18N
        friendMode.setText("Challenge Friends");
        friendMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                friendModeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(friendMode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(backMove)
                        .addGap(11, 11, 11)
                        .addComponent(reset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(friendMode, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(reset, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(backMove, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 90, 560, 370));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 470));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(jButton1.getText().isBlank()){
            jButton1.setText(opponent);
            board[0][0] = opponent;
            slots.push(1);
            if (winningGame()== false)
                aiMakeMove(findBestMove(board, (String)jComboBox1.getSelectedItem()));
            else{
                enableButton(false);
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "Please choose another box", "Reverse Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(jButton2.getText().isBlank()){
            jButton2.setText(opponent);
            board[0][1] = opponent;
            slots.push(2);
            if (winningGame()== false){
                aiMakeMove(findBestMove(board, (String)jComboBox1.getSelectedItem()));
            }
            else{
                enableButton(false);
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "Please choose another box", "Reverse Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if(jButton3.getText().isBlank()){
            jButton3.setText(opponent);
            board[0][2] = opponent;
            slots.push(3);
            if (winningGame()== false){
                aiMakeMove(findBestMove(board, (String)jComboBox1.getSelectedItem()));
            }
            else{
                enableButton(false);
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "Please choose another box", "Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if(jButton4.getText().isBlank()){
            jButton4.setText(opponent);
            board[1][0] = opponent;
            slots.push(4);
            if (winningGame()== false){
                aiMakeMove(findBestMove(board, (String)jComboBox1.getSelectedItem()));
            }
            else{
                enableButton(false);
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "Please choose another box", "Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if(jButton5.getText().isBlank()){
            jButton5.setText(opponent);
            board[1][1] = opponent;
            slots.push(5);
            if (winningGame()== false){
                aiMakeMove(findBestMove(board, (String)jComboBox1.getSelectedItem()));
            }
            else{
                enableButton(false);
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "Please choose another box", "Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if(jButton6.getText().isBlank()){
            jButton6.setText(opponent);
            board[1][2] = opponent;
            slots.push(6);
            if (winningGame()== false){
                aiMakeMove(findBestMove(board, (String)jComboBox1.getSelectedItem()));
            }
            else{
                enableButton(false);
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "Please choose another box", "Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if(jButton7.getText().isBlank()){
            jButton7.setText(opponent);
            board[2][0] = opponent;
            slots.push(7);
            if (winningGame()== false){
                aiMakeMove(findBestMove(board, (String)jComboBox1.getSelectedItem()));
            }
            else{
                enableButton(false);
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "Please choose another box", "Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        if(jButton8.getText().isBlank()){
            jButton8.setText(opponent);
            board[2][1] = opponent;
            slots.push(8);
            if (winningGame()== false){
                aiMakeMove(findBestMove(board, (String)jComboBox1.getSelectedItem()));
            }
            else{
                enableButton(false);
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "Please choose another box", "Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        if(jButton9.getText().isBlank()){
            jButton9.setText(opponent);
            board[2][2] = opponent;
            slots.push(9);
            if (winningGame()== false){
                aiMakeMove(findBestMove(board, (String)jComboBox1.getSelectedItem()));
            }
            else{
                enableButton(false);
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "Please choose another box", "Tic Tae Toe", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
        enableButton(true);
        
        slots.removeAllElements();
        
        for (int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                board[i][j] = "";
            }
        }
       
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
    }//GEN-LAST:event_resetActionPerformed

    private void backMoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backMoveActionPerformed
        takeBackMove();
    }//GEN-LAST:event_backMoveActionPerformed

    private void friendModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_friendModeActionPerformed
        TicTaeToe tictaetoe = new TicTaeToe();
        tictaetoe.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_friendModeActionPerformed

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
            java.util.logging.Logger.getLogger(AiPlayer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AiPlayer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AiPlayer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AiPlayer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AiPlayer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backMove;
    private javax.swing.JButton friendMode;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JButton reset;
    // End of variables declaration//GEN-END:variables
}
