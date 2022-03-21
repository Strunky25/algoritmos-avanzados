/*
 * Authors:
 * Jonathan Salisbury
 * Joan Vilella
 * #SomUIB - Algoritmia
 */
package core.view;

import java.awt.Color;
import javax.swing.DefaultListCellRenderer;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * GUI class used to choose the problem to solve.
 */
public class Menu extends JFrame{ 
    
    /* Static Variables */
    private static final String[] options = new String[]{"-- Problems --", "User Management", "N Queens Problem", "Saveboard Problem", "Knight's Tour problem"};
    
    /* Swing Components */
    private JLabel label;
    private JComboBox optionList;
    private JTextArea information;
    private JScrollPane scrollpane;
    private JButton confirm;

    public Menu(){
        initComponents();
    }
    
    /*
     * Method that initializes and adds the swing components.
     */
    private void initComponents(){
        setTitle("Choose an option");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("resources/swing/selector.png").getImage());
        
        label = new JLabel("Choose an option and click Confirm");

        optionList = new JComboBox(options);
        optionList.addActionListener((e) -> optionDescription());
        DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
        optionList.setRenderer(listRenderer);
        
        information = new JTextArea(5,20);
        information.setText("Select a problem to view its description");
        information.setEditable(false);
        information.setLineWrap(true);
        information.setWrapStyleWord(true);
        information.setBackground(Color.white);
        scrollpane = new JScrollPane(information);
        
        confirm = new JButton("Confirm");
        confirm.addActionListener(e -> confirmActionPerformed());
        
        addComponents();      
        pack();
        setLocationRelativeTo(null);
    }
    
    /*
     * method that returns the description of the selected problem.
     */
    private void optionDescription() {
        switch(optionList.getSelectedIndex()){
            case 0 -> information.setText("Select a problem to view its description");
            case 1 -> information.setText("Access to user Management and Statistics");
            case 2 -> information.setText("Place N Queens in a NxN Chessboard so that no two queens threaten each other.");
            case 3 -> information.setText("Place M Chesspieces in a NxN board so that none can threaten each other.");
            case 4 -> information.setText("Calculate the movements that a Knight needs to perform a tour of all squares.");
        }
    }
    
    /*
     * Method that is performed when the Confirm button is pressed.
     * Creates new bootrap of selected problem and disposes the menu.
     */
    private void confirmActionPerformed(){
        switch(optionList.getSelectedIndex()){
            case 0 -> {return;}
            case 1 -> new modules.usuaris.Bootstrap().run();
            case 2 -> new modules.queens.Bootstrap().run();
            case 3 -> new modules.saveboard.Bootstrap().run();
            case 4 -> new modules.knight.Bootstrap().run();
        }
        this.dispose();
    }

    /*
     * Method that handles the layout of the frame.
     */
    private void addComponents() {
        GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(optionList, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(confirm, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(50, Short.MAX_VALUE))
            );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(optionList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(confirm)
                .addGap(25, 25, 25))
        );
    }
    
}
