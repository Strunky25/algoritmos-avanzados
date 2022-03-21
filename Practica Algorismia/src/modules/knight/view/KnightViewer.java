/*
 * Authors:
 * Jonathan Salisbury
 * Joan Vilella
 * #SomUIB - Algoritmia
 */
package modules.knight.view;

import core.model.chesspieces.Chesspiece;
import core.model.chesspieces.Knight;
import core.view.ProblemViewer;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.LayoutStyle;

/**
 * Class used to visualize the knight problem.
 */
public class KnightViewer extends ProblemViewer<Integer>{
    
     /* Static Variables */
    public static final String DESCRIPTION_TEXT = "Click a Square to place the knight.\n\nThen click solve.";
    private static final Image knightIcon = new Knight(Chesspiece.Color.white).getImage();
    
    /* Class Variables */
    private int knightRow, knightColumn;   
    
    /* Swing Variables */
    private JButton animateButton;

    public KnightViewer(){
        super();
        knightRow = -1;
        knightColumn = -1;      
        initComponents();
    }
    
    /**
     * Method that initializes all swing components.
     */
    @Override
    protected void initComponents(){
        super.initComponents();
        setTitle("Knight Tour Problem");
        setIconImage(new ImageIcon("resources/swing/knight.png").getImage());
        board.addMouseAdapter(new PlaceKnight());        
        animateButton = new JButton("Animate");
        animateButton.setBackground(new java.awt.Color(19, 16, 200));
        animateButton.setFont(new java.awt.Font("Ubuntu", 1, 13)); // NOI18N
        animateButton.setForeground(new java.awt.Color(242, 242, 242));
        animateButton.setEnabled(false);    
        animateButton.setActionCommand("animate");
        addComponents();
        setResizable(false);
    }
    
    /**
     * Method that manages the frame layout.
     */
    @Override
    protected void addComponents(){
        GroupLayout optionsPaneLayout = new GroupLayout(optionsPane);
        optionsPane.setLayout(optionsPaneLayout);
        optionsPaneLayout.setHorizontalGroup(
            optionsPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(optionsPaneLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(optionsPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGroup(optionsPaneLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(optionsPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(sizeLabel)
                            .addComponent(sizeChooser, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        optionsPaneLayout.setVerticalGroup(
            optionsPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(optionsPaneLayout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addComponent(sizeLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sizeChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(optionsPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(animateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(computeButton, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(board, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(optionsPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(computeButton)
                        .addGap(30, 30, 30)
                        .addComponent(animateButton))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(board, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }
    
    /**
     * Method used to paint the board on the chesspanel.
     * @param board board to be painted.
     */
    @Override
    public void paintBoard(Integer[][] board){    
        this.board.unpaintSquare(knightRow, knightColumn);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                this.board.paintNumber(i, j, board[i][j]);  
            }
        }
        animateButton.setEnabled(true);
    }

    /**
     * Method used to animate knight tour on the chesspanel.
     * @param board board to be animated.
     */
    public void animateBoard(Integer[][] board){
        int num = -1;
        int[] thisPoint = getNextSquare(board, num), nextPoint = getNextSquare(board, ++num);
        while(nextPoint != null){
            this.board.paintLine(thisPoint[1], thisPoint[0], nextPoint[1], nextPoint[0]);
            thisPoint = nextPoint;
            nextPoint = getNextSquare(board, ++num);
        }
        animateButton.setEnabled(false);
    }
    
    /**
     * Method that returns the array index of the square with the following number.
     * @param board board to be searched
     * @param num   number of which we need to find the next.
     * @return 
     */
    private int[] getNextSquare(Integer[][] board, int num){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if(board[i][j] == num + 1){
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }
    
    /**
     * method that ads an Actionlistener to the frame.
     * @param listener
     */
    public void attachAnimateActionListener(ActionListener listener){
        this.animateButton.addActionListener(listener);
    }
    
    /**
     * This method is performed when the user changes the board size.
     */
    @Override
    protected void sizeChange(){
        super.sizeChange();
        this.board.addMouseAdapter(new PlaceKnight());
    }
    
    /**
     * Knight initial row getter
     * @return Knight initial row
     */
    public int getKnightRow(){
        return this.knightRow;
    }
    
    /**
     * Knight initial column getter
     * @return Knight initial column
     */
    public int getKnightColumn(){
        return this.knightColumn;
    }
    
    /**
     * Subclass that is used to place the Knight on the chessboard.
     */
    public class PlaceKnight extends MouseAdapter{
        
        @Override
        public void mousePressed(MouseEvent e) {
            JLabel[][] squares = board.getSquares();
            JLabel selectedSquare = (JLabel) e.getComponent();
            for (int i = 0; i < squares.length; i++) {
                for (int j = 0; j < squares.length; j++) {
                    board.unpaintSquare(i, j);
                    if(squares[i][j] == selectedSquare){
                        knightRow = i;
                        knightColumn = j;
                        board.paintSquare(knightIcon, i, j);
                        printMessage(informationArea.getText(), 0);     
                    }
                }
            }
        }       
    }
}
