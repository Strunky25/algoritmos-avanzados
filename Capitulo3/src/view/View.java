/*
    Algoritmes Avançats - Capitulo 3
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package view;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Class that contains the Swing GUI for the project.
 */
public class View extends JFrame {

    /* Constants */

    /* Variables */

    /* Swing Components */
    private JLabel num1Lbl, num2Lbl, resLbl;
    private JTextArea num1TxtArea, num2TxtArea, resTxtArea;
    private JScrollPane num1Scroll, num2Scroll, resScroll;
    private JButton tradBtn, karaBtn, mixBtn, cmpBtn;

    public View() {
        initComponents();
    }

    /**
     * Method that initializes swing components, and sets JFrame properties.
     */
    private void initComponents() {
        setTitle("Capitulo 3");
        // setIconImage(new ImageIcon("resources/icon.png").getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        /* Init Components */
        num1Lbl = new JLabel("First Number");
        num2Lbl = new JLabel("Second Number");
        resLbl = new JLabel("Result");

        num1TxtArea = new JTextArea(5, 5);
        num1Scroll = new JScrollPane(num1TxtArea);
        
        num2TxtArea = new JTextArea(5, 5);
        num2Scroll = new JScrollPane(num2TxtArea);
        
        resTxtArea = new JTextArea(5, 20);
        resScroll = new JScrollPane(resTxtArea);

        tradBtn = new JButton("Traditional");
        karaBtn = new JButton("Karatsuba");
        mixBtn = new JButton("Mixed");
        cmpBtn = new JButton("Comparison");

        addComponents();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    /**
     * Method that creates the frame layout with all its components.
     */
    private void addComponents() {
        /* Add Layout */
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(resScroll)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(tradBtn)
                        .addGap(75, 75, 75)
                        .addComponent(karaBtn)
                        .addGap(75, 75, 75)
                        .addComponent(mixBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(resLbl)
                            .addComponent(num1Lbl)
                            .addComponent(num1Scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(num2Scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(num2Lbl))))
                .addContainerGap(50, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cmpBtn)
                .addGap(185, 185, 185))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(num1Lbl)
                    .addComponent(num2Lbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(num1Scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(num2Scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(resLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(karaBtn)
                    .addComponent(mixBtn)
                    .addComponent(tradBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmpBtn)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        pack();
    }

    /* METHODS */
    public String getFirstNumber() {
        return num1TxtArea.getText();
    }

    public String getSecondNumber() {
        return num2TxtArea.getText();
    }

    public void setResult(String value) {
        this.resTxtArea.setText(value);
    }

    // Add Listeners
    public void addButtonsListener(ActionListener listener) {
        this.tradBtn.addActionListener(listener);
        this.karaBtn.addActionListener(listener);
        this.mixBtn.addActionListener(listener);
        this.cmpBtn.addActionListener(listener);
    }
}
