/*
 * Authors:
 * Jonathan Salisbury
 * Joan Vilella
 * #SomUIB - Algoritmia
 */
package modules.queens.view;

import core.model.chesspieces.Chesspiece;
import core.model.chesspieces.Queen;
import core.view.ProblemViewer;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 * Class used to visualize the N Queens problem.
 */
public class QueenViewer extends ProblemViewer <Boolean>{
    
    /* Static Variables */
    public static final String DESCRIPTION_TEXT = "Click a Square to place the first queen.\n\nThen click compute.";
    private static final Image queenIcon = new Queen(Chesspiece.Color.white).getImage();
    
    /* Class Variables */
    private int queenRow, queenColumn;   
    
    public QueenViewer(){
        super();
        queenRow = -1;
        queenColumn = -1;      
        initComponents();
    }
    
    /**
     * Method that initializes all swing components.
     */
    @Override
    protected void initComponents(){
        super.initComponents();
        setTitle("N Queens Problem");
        setIconImage(new ImageIcon("resources/swing/queen.png").getImage());       
        board.addMouseAdapter(new FirstQueen());                  
        informationArea.setText(DESCRIPTION_TEXT);
        super.addComponents();
    }
    
    /**
     * Method used to paint the board on the chesspanel.
     * @param board board to be painted.
     */
    @Override
    public void paintBoard(Boolean[][] board){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if(board[i][j] != null){
                    if(i == queenRow && j == queenColumn){
                        this.board.paintSquare(new Queen(Chesspiece.Color.black).getImage(), i, j);
                    } else {                      
                        this.board.paintSquare(queenIcon, i, j);
                    }
                }
            }
        }
    }
    
    /**
     * This method is performed when the user changes the board size.
     */
    @Override
    protected void sizeChange(){
        super.sizeChange();
        this.board.addMouseAdapter(new QueenViewer.FirstQueen());
    }
    
    /**
     * Queen initial row getter
     * @return Queen initial row
     */
    public int getQueenRow(){
        return this.queenRow;
    }
    
    /**
     * Queen initial column getter
     * @return Queen initial column.
     */
    public int getQueenColumn(){
        return this.queenColumn;
    }
    
    /**
     * Subclass that is used to place the Knight on the chessboard.
     */
    public class FirstQueen extends MouseAdapter{

        @Override
        public void mousePressed(MouseEvent e) {
            JLabel[][] squares = board.getSquares();
            JLabel selectedSquare = (JLabel) e.getComponent();
            for (int i = 0; i < squares.length; i++) {
                for (int j = 0; j < squares.length; j++) {
                    if(squares[i][j] == selectedSquare){
                        queenRow = i;
                        queenColumn = j;
                        board.paintSquare(new Queen(Chesspiece.Color.black).getImage(), i, j);
                        printMessage(informationArea.getText(), 0);
                    } else {
                        board.unpaintSquare(i, j);
                    }                      
                }
            }
        }       
    }
}
