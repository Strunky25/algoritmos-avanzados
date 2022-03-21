/*
 * Authors:
 * Jonathan Salisbury
 * Joan Vilella
 * #SomUIB - Algoritmia
 */
package modules.saveboard.view;

import core.model.chesspieces.*;
import core.view.ProblemViewer;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;

/**
 * Class used to visualize the Saveboard problem.
 */
public class SaveboardViewer extends ProblemViewer <Chesspiece>{
    
    /* Static Variables */
    public static final String DESCRIPTION_TEXT = "Add pieces to the list.\n\nThen click compute.";

    /* Swing Components */
    private JComboBox pieceChooser;
    private JButton addButton, clearButton;
    private JList piecesList;
    private JScrollPane scrollPane2;
    
    /* Class Variables */
    private ArrayList pieces;
    
    public SaveboardViewer(){
        super();
        pieces = new ArrayList<Chesspiece>();
        initComponents();
    }
    
    /**
     * Method that initializes all swing components.
     */
    @Override
    protected void initComponents(){
        super.initComponents();
        setTitle("N Queens Problem");
        setIconImage(new ImageIcon("resources/swing/pieces.png").getImage());
        Chesspiece.Color white = Chesspiece.Color.white;
        Chesspiece[] options = new Chesspiece[]{new Queen(white), new Rook(white), new Bishop(white),
            new Knight(white), new King(white), new Pawn(white), new Ferz(white), new Dabbaba(white)};
        pieceChooser = new JComboBox(options);
        addButton = new JButton("Add Piece");
        addButton.addActionListener((e) -> addPiece());
        
        clearButton = new JButton("Clear Pieces");
        clearButton.addActionListener((e) -> clearPieces());
        
        informationArea.setText(DESCRIPTION_TEXT);
        
        piecesList = new JList(pieces.toArray());
        scrollPane2 = new JScrollPane(piecesList);
        addComponents();
    }
    
    /**
     * Method that manages the frame layout.
     */
    @Override
    protected void addComponents(){
        /* Option Pane Layout */
        javax.swing.GroupLayout panel_optionsLayout = new javax.swing.GroupLayout(optionsPane);
        optionsPane.setLayout(panel_optionsLayout);
        panel_optionsLayout.setHorizontalGroup(
            panel_optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_optionsLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(panel_optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sizeLabel)
                    .addComponent(sizeChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        panel_optionsLayout.setVerticalGroup(
            panel_optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_optionsLayout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addComponent(sizeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sizeChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        /* Frame Layout */
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(optionsPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(pieceChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(scrollPane2)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(computeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(board, javax.swing.GroupLayout.PREFERRED_SIZE, 601, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(optionsPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pieceChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(scrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(clearButton)
                        .addGap(27, 27, 27)
                        .addComponent(computeButton))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(board, javax.swing.GroupLayout.PREFERRED_SIZE, 634, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
    public void paintBoard(Chesspiece[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if(board[i][j] != null){
                    this.board.paintSquare(board[i][j].getImage(), i, j);
                }
            }          
        }
    }
    
    /**
     * This method is performed when the user clicks the add Piece button.
     * Adds current selected piece to the piece list.
     */
    private void addPiece(){
        switch(pieceChooser.getSelectedIndex()){
            case 0 -> pieces.add(new Queen(Chesspiece.Color.white));
            case 1 -> pieces.add(new Rook(Chesspiece.Color.white));
            case 2 -> pieces.add(new Bishop(Chesspiece.Color.white));
            case 3 -> pieces.add(new Knight(Chesspiece.Color.white));
            case 4 -> pieces.add(new King(Chesspiece.Color.white));
            case 5 -> pieces.add(new Pawn(Chesspiece.Color.white));
            case 6 -> pieces.add(new Ferz(Chesspiece.Color.white));
            case 7 -> pieces.add(new Dabbaba(Chesspiece.Color.white));
        }
        piecesList.setListData(pieces.toArray());
    }
    
    /**
     * Clears the piece list.
     */
    private void clearPieces(){
        pieces = new ArrayList<Chesspiece>();
        piecesList.setListData(pieces.toArray());
    }
    
    
    /**
     * Getter of the piece list.
     * @return the piece list.
     */
    public Chesspiece[] getSelectedPieces(){
        if(pieces.isEmpty()){
            return null;
        }
        Chesspiece[] array = new Chesspiece[pieces.size()];
        pieces.toArray(array);
        return array;
    }
}
