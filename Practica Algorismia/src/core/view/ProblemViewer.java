/*
 * Authors:
 * Jonathan Salisbury
 * Joan Vilella
 * #SomUIB - Algoritmia
 */
package core.view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerNumberModel;

/**
 * GUI class that represents visually a chess problem
 * @param <T> the type in which chesspieces will be represented on the board.
 */
public abstract class ProblemViewer <T> extends JFrame {
    
    /* Static Variables */
    public static final Color GREEN = new Color(0, 153, 51),RED = new Color(204, 0, 0);
    public static final int INIT_SIZE = 8, ERROR = 1, MESSAGE = 2;
    
    /* Swing Variables */
    protected ChessboardPanel board;
    protected JPanel optionsPane;
    protected JLabel sizeLabel;
    protected JSpinner sizeChooser;
    protected JTextArea informationArea;
    protected JScrollPane scrollPane;
    protected JButton computeButton;
    
    /* Class Variables */
    protected int n;
    
    public ProblemViewer(){
        n = INIT_SIZE;
    }
    
    /**
     * Method that initializes swing components.
     */
    protected void initComponents(){
        WindowAdapter exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(
                     null, "Would you like to try another problem?", 
                     "Exit Confirmation", JOptionPane.YES_NO_OPTION, 
                     JOptionPane.QUESTION_MESSAGE, null, null, null);
                switch(confirm){
                    case 0 -> {
                        java.awt.EventQueue.invokeLater(() -> {
                            new Menu().setVisible(true);
                        });
                    } case 1 -> System.exit(0);
                }
            }
        };
        addWindowListener(exitListener);
        
        board = new ChessboardPanel(n);
        
        optionsPane = new JPanel();
        
        sizeLabel = new JLabel("Select board size:");
        sizeChooser = new JSpinner(new SpinnerNumberModel(n, 3, 50, 1));
        sizeChooser.addChangeListener((e) -> sizeChange());
        
        informationArea = new JTextArea(5, 20);
        informationArea.setEditable(false);
        informationArea.setLineWrap(true);
        informationArea.setWrapStyleWord(true);
        informationArea.setBackground(Color.white);
        scrollPane = new JScrollPane(informationArea);
        
        computeButton = new JButton("Compute");
        computeButton.setActionCommand("compute");
        computeButton.setBackground(new java.awt.Color(19, 16, 200));
        computeButton.setFont(new java.awt.Font("Ubuntu", 1, 13)); // NOI18N
        computeButton.setForeground(new java.awt.Color(242, 242, 242));
        //addComponents();
    }
    
    /**
     * Method that manages the layout of the frame.
     */
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
                        .addComponent(optionsPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(computeButton, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(board, GroupLayout.PREFERRED_SIZE, 600, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(optionsPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(computeButton))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(board, GroupLayout.PREFERRED_SIZE, 600, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }
    
    /**
     * Method that paints the given board on the chesspanel
     * @param board to be painted
     */
    public abstract void paintBoard(T[][] board);
    
    /**
     * method that is performed when the user changes the board size.
     */
    protected void sizeChange(){
        n = (int) sizeChooser.getValue();
        if(this.n != board.getN()){
            board.changeSize(n);
        }
    }
    
    /**
     * Method used to print messages on the information area.
     * @param message String to be printed.
     * @param type Type of message to be printed.
     */
    public void printMessage(String message, int type){
        switch(type){
            case ERROR -> informationArea.setForeground(RED);
            case MESSAGE -> informationArea.setForeground(GREEN);
            default -> informationArea.setForeground(Color.black);
        }
        informationArea.setText(message);
    }
    
    /**
     * Method that adds a actionListener to the frame.
     * @param listener
     */
    public void attachActionListener(ActionListener listener){
        this.computeButton.addActionListener(listener);
    }
    
    /**
     * Method that resets all squares from the board.
     */
    public void reset(){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board.unpaintSquare(i, j);
            }
        }
    }
    
    /**
     * Method that returns the size of the board.
     * @return
     */
    public int getN(){
        return this.n;
    }
}
