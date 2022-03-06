/*
    Algoritmes Avançats - Capitulo 1
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package capitulo1.view;

import capitulo1.model.Model;
import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 *
 * @author elsho
 */
public class View extends JFrame{
    
    /* Swing Components */
    private AnimationPanel animationPanel;
    private JComboBox picker;
    private JButton animateBut, stopBut;
    private JLabel infoLab;
    private JProgressBar progressBar;
    
    public View(){
        initComponents();
    }
    
    /**
     * Method that initializes swing components, and sets JFrame properties.
     */
    private void initComponents(){
        setTitle("Capitulo 1");
        setIconImage(new ImageIcon("resources/icon.png").getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        animationPanel = new AnimationPanel();
        animationPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
        
        picker = new JComboBox(Model.COMPLEXITIES);
        
        animateBut = new JButton("Animate");
        stopBut = new JButton("Stop");
        stopBut.setEnabled(false);
        
        infoLab = new JLabel("Select Complexity:");
        
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        
        addComponents();
        setResizable(false);
        setLocationRelativeTo(null);
    }
    
    /**
     * Method that creates the frame layout with all its components.
     */
    private void addComponents(){
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(animationPanel);
        animationPanel.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, AnimationPanel.WIDTH, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, AnimationPanel.HEIGHT, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(picker, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(animateBut, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(stopBut, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(infoLab)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(animationPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(75, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(animationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(infoLab)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(picker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(animateBut)
                    .addComponent(stopBut))
                .addGap(8, 8, 8)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pack();
    }

    public void addAnimateListener(ActionListener listener){
        this.animateBut.addActionListener(listener);
    }
    
    public void addStopListener(ActionListener listener){
        this.stopBut.addActionListener(listener);
    }
    
    public String getChosenComplexity(){
        return (String) this.picker.getSelectedItem();
    }
    
    public void setRunning(boolean running){
        this.animateBut.setEnabled(!running);
        this.stopBut.setEnabled(running);
        if(!running){
            animationPanel.setOrigin();
        }
    }
    
    public void setProgress(int progress){
        this.progressBar.setValue(progress);
    }
    
    public void animate(int size, long time){
            System.out.println(time);
        int tim = (int) (time/1000000000);
        int[] point = new int[]{size, tim};
        this.animationPanel.animatePoint(point);
    }
    
}
