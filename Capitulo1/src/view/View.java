/*
    Algoritmes Avançats - Capitulo 1
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package view;

import model.Model;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * Class that contains the Swing GUI for the project.
 */
public class View extends JFrame{
    
    /* Constants */
    private static final int POINT_DIAMETER = 4;
    private static final int TEXT_PADDING = 5;
    private static final int GRAPH_PADDING = 50;
    public static final int PANEL_HEIGHT = 350, PANEL_WIDTH = 350;
    
    private static final Color[] COLORS = new Color[]{
                            Color.red, Color.blue, Color.darkGray, Color.magenta, Color.black};
    
    /* Variables */
    private int color, lastX, lastY;
    
    /* Swing Components */
    private JPanel animationPanel;
    private JComboBox picker;
    private JButton animateBut, stopBut, clearBut;
    private JLabel infoLab;
    private JProgressBar progressBar;
    
    public View(){
        initComponents();
        lastX = lastY = color = 0;
    }
    
    /**
     * Method that initializes swing components, and sets JFrame properties.
     */
    private void initComponents(){
        setTitle("Capitulo 1");
        setIconImage(new ImageIcon("resources/icon.png").getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        animationPanel = new JPanel();
        animationPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
        
        picker = new JComboBox(Model.COMPLEXITIES);
        
        animateBut = new JButton("Animate");
        stopBut = new JButton("Stop");
        stopBut.setEnabled(false);
        clearBut = new JButton("Clear");
        clearBut.setEnabled(false);
        
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
            .addGap(0, PANEL_WIDTH, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, PANEL_HEIGHT, Short.MAX_VALUE)
        );
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(animationPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(picker, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(infoLab))
                        .addGap(18, 18, 18)
                        .addComponent(animateBut, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(stopBut, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(clearBut, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(70, Short.MAX_VALUE))
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
                    .addComponent(stopBut)
                    .addComponent(clearBut))
                .addGap(8, 8, 8)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pack();
    }

    /**
     * Method that sets a listener for a particular button.
     * @param listener 
     */
    public void addAnimateListener(ActionListener listener){
        this.animateBut.addActionListener(listener);
    }
    
    /**
     * Method that sets a listener for a particular button.
     * @param listener 
     */
    public void addStopListener(ActionListener listener){
        this.stopBut.addActionListener(listener);
    }
    
    /**
     * Method that sets a listener for a particular button.
     * @param listener 
     */
    public void addClearListener(ActionListener listener){
        this.clearBut.addActionListener(listener);
    }
    
    /**
     * Method that returns the complexity chosen by the user.
     * @return String representation of the Complexity.
     */
    public String getChosenComplexity(){
        return (String) this.picker.getSelectedItem();
    }
    
    /**
     * Method that tells the UI that the simulation is running
     * and makes the necessary changes.
     * @param running boolean that represents if the simulation is running.
     */
    public void setRunning(boolean running){
        this.animateBut.setEnabled(!running);
        this.stopBut.setEnabled(running);
        if(!running){
            lastX = lastY = 0;
            this.color++;
            this.clearBut.setEnabled(true);
        }
    }
    
    /**
     * Method that sets the current simulations progress updating the progressbar.
     * @param progress int between 0 and 100 that represents current progress.
     */
    public void setProgress(int progress){
        this.progressBar.setValue(progress);
    }
    
    /**
     * Method that creates a new point at a given coordinate, and draws a line 
     * between the last point and the new one.
     * @param pos double X coordinate scaled to Sizes length.
     * @param time double Y time coordinate.
     */
    public void animate(double pos, double time){
        int x = (int)(pos*(PANEL_WIDTH - GRAPH_PADDING));
        int y = (int) time;
        //System.out.println("time:" + time + ", y: " + y);
        Graphics2D graph = (Graphics2D) animationPanel.getGraphics();
        graph.setColor(COLORS[color % COLORS.length]);
        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
        graph.drawLine(lastX, PANEL_HEIGHT - lastY, x, PANEL_HEIGHT - y);
        graph.fillOval(x - POINT_DIAMETER/2, PANEL_HEIGHT - (y + POINT_DIAMETER/2), POINT_DIAMETER, POINT_DIAMETER);
        lastX = x;
        lastY = y;
    }
    
    /**
     * Method that draws the given text on the last drawn point location.
     * @param text String of text to draw.
     */
    public void drawlastPointText(String text){
        //animationPanel.writePoint(text);
        Graphics2D graph = (Graphics2D) animationPanel.getGraphics();
        graph.setColor(COLORS[color%COLORS.length]);
        int y = Math.max(TEXT_PADDING, PANEL_HEIGHT - lastY);
        graph.drawString(text, lastX + TEXT_PADDING, y + TEXT_PADDING);
    }

    /**
     * Method that clears the Animation panel and resets global variables.
     */
    public void clearAnimationPanel() {
        lastX = lastY = 0;
        this.color = 0;
        animationPanel.removeAll();
        animationPanel.revalidate();
        animationPanel.repaint();
        this.clearBut.setEnabled(false);
    }
}
