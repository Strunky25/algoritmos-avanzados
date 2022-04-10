/*
    Algoritmes Avançats - Capitulo 3
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package view;

import java.awt.event.ActionListener;
import java.math.BigInteger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Class that contains the Swing GUI for the project.
 */
public class View extends JFrame{
    
    /* Constants */
    
    /* Variables */
    
    /* Swing Components */
    private JLabel firstNumLbl, secNumLbl, resLbl, mulLbl, eqLbl;
    private JTextField firstNumFld, secNumFld, resFld;
    private JButton tradBtn, karaBtn, mixBtn;

    
    public View(){
        initComponents();
    }
    
    /**
     * Method that initializes swing components, and sets JFrame properties.
     */
    private void initComponents(){
        setTitle("Capitulo 3");
        //setIconImage(new ImageIcon("resources/icon.png").getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        /* Init Components */
        firstNumLbl = new JLabel("First Number");
        secNumLbl = new JLabel("Second Number");
        resLbl = new JLabel("Result");
        mulLbl = new JLabel("X");
        eqLbl = new JLabel("=");
        
        firstNumFld = new JTextField();
        secNumFld = new JTextField();
        resFld = new JTextField();
        
        tradBtn = new JButton("Traditional");
        karaBtn = new JButton("Karatsuba");
        mixBtn = new JButton("Mixed");
        
        addComponents();
        setResizable(false);
        setLocationRelativeTo(null);
    }
    
    /**
     * Method that creates the frame layout with all its components.
     */
    private void addComponents(){
        /* Add Layout */
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(firstNumLbl)
                        .addComponent(firstNumFld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tradBtn))
                .addGap(16, 16, 16)
                .addComponent(mulLbl)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(secNumLbl)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(secNumFld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(eqLbl))
                    .addComponent(karaBtn))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(resLbl)
                    .addComponent(resFld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mixBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstNumLbl)
                    .addComponent(secNumLbl)
                    .addComponent(resLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstNumFld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(secNumFld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mulLbl)
                    .addComponent(eqLbl)
                    .addComponent(resFld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(karaBtn)
                    .addComponent(mixBtn)
                    .addComponent(tradBtn))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        pack();
    }

    /* METHODS */
    public String getFirstNumber(){
        return firstNumFld.getText();
    }
    
    public String getSecondNumber(){
        return secNumFld.getText();
    }
    
    public void setResult(String value){
        this.resFld.setText(value);
    }
    
    
    // Add Listeners
    public void addTraditionalListener(ActionListener listener){
        this.tradBtn.addActionListener(listener);
    }
}
