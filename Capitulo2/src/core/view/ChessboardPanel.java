/*
 * Authors:
 * Jonathan Salisbury
 * Joan Vilella
 * #SomUIB - Algoritmia
 */
package core.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Class that visualizes a chessboard. 
 */
public class ChessboardPanel extends JPanel{
    
    /* Static Variables */
    private final static int SIZE = 600;
    
    /* Class Variables */
    private int n, squareSize;
    
    /* Swing Variables */
    private JLabel[][] squares;
    
    public ChessboardPanel(int n){
        this.n = n;
        squareSize = SIZE/n;
        initComponents();
    }
    
    /* 
     * Class that initializes and adds the swing components
     */
    private void initComponents(){
        setPreferredSize(new Dimension(SIZE, SIZE)); 
        setLayout(new GridLayout(n, n));
        setBorder(BorderFactory.createLineBorder(Color.black, 2));
        squares = new JLabel[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) { 
                squares[i][j] = new JLabel();
                squares[i][j].setSize(squareSize, squareSize);
                squares[i][j].setOpaque(true);
                if ((i + j) % 2 == 0) {
                    squares[i][j].setBackground(Color.white);
                    //squares[i][j].setBorder(BorderFactory.createLineBorder(Color.black));                  
                } else {
                    squares[i][j].setBackground(Color.black);
                }   
                add(squares[i][j]);
                squares[i][j].repaint();
            }
        }
    }
    
    /**
     * Method that paints a chesspiece image on a given square.
     * @param image of the piece
     * @param i row position
     * @param j column position
     */
    public void paintSquare(Image image, int i, int j){
        if(image != null){
            image = image.getScaledInstance(squareSize - 1, squareSize - 1, Image.SCALE_SMOOTH);
            squares[i][j].setIcon(new ImageIcon(image));
            squares[i][j].repaint();
        }
    }
    
    /**
     * Method that removes an icon from a given square.
     * @param i row position
     * @param j column position.
     */
    public void unpaintSquare(int i, int j){
        squares[i][j].setIcon(null);
        squares[i][j].setText(null);
        squares[i][j].repaint();
    }
    
    /**
     * Method that paints a given number on a given square.
     * @param i row position
     * @param j column position
     * @param number number to paint.
     */
    public void paintNumber(int i, int j, int number){
        squares[i][j].setFont(new Font("Calibri", Font.BOLD, 20));
        squares[i][j].setHorizontalAlignment(SwingConstants.CENTER);
        if(squares[i][j].getBackground() == Color.black){
            squares[i][j].setForeground(Color.white);
        } else {
            squares[i][j].setForeground(Color.black);
        }
        squares[i][j].setText(String.valueOf(number));
        squares[i][j].repaint();
    }
    
    /**
     * Method that paints a red line from a given square to another one.
     * @param x1 row position of starting square.
     * @param y1 column position of starting square.
     * @param x2 row position of finishing square.
     * @param y2 column position of finishing square.
     */
    public void paintLine(int x1, int y1, int x2, int y2){
        Graphics2D g = (Graphics2D) this.getGraphics();  
        g.setColor(Color.red);
        g.setStroke(new BasicStroke(2));
        g.drawLine(x1*squareSize + squareSize/2, y1*squareSize + squareSize/2, x2*squareSize + squareSize/2, y2*squareSize + squareSize/2);
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(ChessboardPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Method that adds a given mouseadapter to the cheesboard panel.
     * @param adapter adapter to be set.
     */
    public void addMouseAdapter(MouseAdapter adapter){
        for (JLabel[] row : squares) {
            for (int j = 0; j < squares.length; j++) {
                row[j].addMouseListener(adapter);
            }
        }
    }
    
    /**
     * Method that changes the size of the chessboard.
     * @param n new size
     */
    public void changeSize(int n){
        this.n = n;
        this.squareSize = SIZE/n;
        this.reset();
    }
    
    /**
     * Resets the chessboard.
     */
    public void reset(){
        this.removeAll();
        this.initComponents();
        this.updateUI();
        this.repaint(); 
    }
    
    /**
     * Method to get the current board size.
     * @return board size.
     */
    public int getN(){
        return this.n;
    }
    
    /**
     * Method to get the squares array.
     * @return squares array.
     */
    public JLabel[][] getSquares(){
        return this.squares;
    }
}
