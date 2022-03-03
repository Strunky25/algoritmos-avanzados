/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capitulo1.view;

import capitulo1.model.Model;
import java.awt.event.ActionListener;
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
    
    private JComboBox picker;
    private JButton animateBut, stopBut;
    private JLabel infoLab;
    private JProgressBar progressBar;
    
    public View(){
        initComponents();
    }
    
    private void initComponents(){
        setTitle("Capitulo 1");
        setIconImage(new ImageIcon("resources/icon.png").getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        
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
    }
    
    public void setProgress(int progress){
        this.progressBar.setValue(progress);
    }
    
    private void addComponents(){
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(picker, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(animateBut, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(stopBut, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(infoLab)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(81, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(364, Short.MAX_VALUE)
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

}
