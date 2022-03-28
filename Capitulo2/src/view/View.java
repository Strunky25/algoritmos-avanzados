/*
    Algoritmes Avançats - Capitulo 2
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package view;

import controller.Message;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import model.chesspieces.Chesspiece;

/**
 * Class that contains the Swing GUI for the project.
 */
public class View extends JFrame{
    
    /* Constants */
    public static final int PANEL_SIZE = 500;
    public static final int INIT_SIZE = 8;
    public static final Color GREEN = new Color(0, 153, 51);
    
    /* Variables */
    private Integer pieceCol, pieceRow;
    private Chesspiece selectedPiece;
    private int squareSize;
    private boolean  painted;
    
    /* Swing Components */
    private JPanel boardPanel;
    private JLabel[][] tile;
    private JButton computeBut, animateBut, stopBut;
    private JLabel sizeLab, pìeceLab;
    private JComboBox pieceCombo;
    private JScrollPane scrollPanel;
    private JTextArea infoArea;
    private JProgressBar progressBar;
    private JSpinner sizeSpinner;
    
    
    public View(){
        painted = false;
        initComponents();
    }
    
    /**
     * Method that initializes swing components, and sets JFrame properties.
     */
    private void initComponents(){
        setTitle("Capitulo 2");
        setIconImage(new ImageIcon("resources/icon.png").getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        this.boardPanel = new JPanel();
        this.boardPanel.setPreferredSize(new Dimension(PANEL_SIZE, PANEL_SIZE));
        this.boardPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        initBoardPanel(INIT_SIZE);
        
        computeBut = new JButton("Compute");
        animateBut = new JButton("Animate");
        stopBut = new JButton("Stop");
        
        sizeLab = new JLabel("Select size:");
        pìeceLab = new JLabel("Select piece:");
        
        infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setBackground(Color.white);
        scrollPanel = new JScrollPane();
        scrollPanel.setViewportView(infoArea);
        
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        //progressBar.setStringPainted(true);
        
        sizeSpinner = new JSpinner(new SpinnerNumberModel(INIT_SIZE, 3, 25, 1));
        sizeSpinner.addChangeListener((e) -> changeBoardSize());
        
        pieceCombo = new JComboBox(Chesspiece.Type.values());//array piezas.
        pieceCombo.addActionListener((e) -> selectChesspiece());
        
        addComponents();
        setResizable(false);
        setLocationRelativeTo(null);
    }
    
    private void initBoardPanel(int boardSize){
        this.boardPanel.setLayout(new GridLayout(boardSize, boardSize));
        this.boardPanel.addMouseListener(new TileChooseAdapter());
        //int squareSize = (int) Math.ceil((float) PANEL_SIZE/boardSize);
        squareSize = PANEL_SIZE/boardSize;
        this.tile = new JLabel[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                tile[i][j] = new JLabel();
                tile[i][j].setOpaque(true);
                tile[i][j].setSize(squareSize, squareSize);
                //tile[i][j].addMouseListener(new TileChooseAdapter());
                tile[i][j].setBackground((i + j) % 2 == 0 ? Color.white: Color.black);
                this.boardPanel.add(tile[i][j]);
            }
        }
    }
    
    /**
     * Method that creates the frame layout with all its components.
     */
    private void addComponents(){
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(computeBut, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(animateBut, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(stopBut, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(scrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                                    .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(sizeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(pieceCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(sizeLab, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(pìeceLab)))
                        .addGap(61, 61, 61))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(boardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(boardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sizeLab)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(sizeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pìeceLab)
                        .addGap(8, 8, 8)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(computeBut)
                            .addComponent(animateBut)
                            .addComponent(stopBut))
                        .addGap(21, 21, 21))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pieceCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        pack();
    }

    // Add Listeners
    public void addComputeListener(ActionListener listener){
        this.computeBut.addActionListener(listener);
    }
    
    public void addStopListener(ActionListener listener){
        this.stopBut.addActionListener(listener);
    }
    
    public void addAnimateListener(ActionListener listener){
        this.animateBut.addActionListener(listener);
    }
    
    public Integer[] getPiecePosition(){
        if(pieceRow == null) return null;
        return new Integer[]{pieceRow, pieceCol};
    }
    
    public Chesspiece getSelectedPiece(){
        return selectedPiece;
    }
    
    public void enableSpinner(boolean enabled) {
        this.sizeSpinner.setEnabled(enabled);
    }
    
    public void setProgress(int progress){
        this.progressBar.setValue(progress);
    }
    
    public int getBoardSize(){
        return (int) sizeSpinner.getValue();
    }
    
    public void paintBoardNumbers(int[][] board){
        this.painted = true;
        tile[pieceRow][pieceCol].setIcon(null);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                tile[i][j].setFont(new Font("Calibri", Font.BOLD, 20));
                tile[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                tile[i][j].setForeground(tile[i][j].getBackground() == Color.black ? Color.white: Color.black);
                tile[i][j].setText(String.valueOf(board[i][j]));
                tile[i][j].repaint();
            }
        }
    }
    
    public void paintLine(int x1, int y1, int x2, int y2){
        Graphics2D g = (Graphics2D) boardPanel.getGraphics();  
        g.setColor(Color.red);
        g.setStroke(new BasicStroke(2));
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
        g.drawLine(x1*squareSize + squareSize/2, y1*squareSize + squareSize/2, x2*squareSize + squareSize/2, y2*squareSize + squareSize/2);
    }
    
    private void unpaintTile(int i, int j){
        tile[i][j].setIcon(null);
        tile[i][j].setText(null);
        tile[i][j].repaint();
    }
    
    public void printMessagge(String message, Message.Type type){
        switch(type){
            case INFO -> {this.infoArea.setForeground(Color.black);}
            case SUCCESS -> {this.infoArea.setForeground(GREEN);}
            case ERROR -> {this.infoArea.setForeground(Color.red);}
        }
        this.infoArea.setText(message);
    }
    
    private void changeBoardSize() {
        this.boardPanel.removeAll();
        initBoardPanel((int) sizeSpinner.getValue());
        this.boardPanel.updateUI();
        this.boardPanel.repaint();
        pieceRow = pieceCol = null;
    }
    
    private void selectChesspiece() {
        selectedPiece = ((Chesspiece.Type) pieceCombo.getSelectedItem()).getInstance();
    }
    
    private class TileChooseAdapter extends MouseAdapter{

        @Override
        public void mouseReleased(MouseEvent e) {
            progressBar.setValue(0);
            if(painted){
                for (int i = 0; i < tile.length; i++) {
                    for (int j = 0; j < tile.length; j++) {
                        unpaintTile(i, j);
                    }
                }
                painted = false;
            }
            if(pieceRow != null) tile[pieceRow][pieceCol].setIcon(null);
            pieceRow = e.getY()/squareSize;
            pieceCol = e.getX()/squareSize;
            if(selectedPiece == null) selectChesspiece();
            ImageIcon icon = new ImageIcon(selectedPiece.getImage().getScaledInstance(squareSize, squareSize, Image.SCALE_SMOOTH));
            tile[pieceRow][pieceCol].setIcon(icon);
        }
        
    }
}
