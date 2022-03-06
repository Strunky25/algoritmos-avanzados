/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capitulo1.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 *
 * @author elsho
 */
public class AnimationPanel extends JPanel {

    public static final int HEIGHT = 350, WIDTH = 350;
    
    private static final int POINT_DIAMETER = 4;
    private static final Color[] COLORS = new Color[]{
                            Color.red, Color.blue, Color.black, Color.green};
    
    private int[] lastPoint;
    private int color;
    
    public AnimationPanel() {
        setOrigin();
        color = 0;
    }
    
    public void animatePoint(int[] newPoint){
        System.out.println("size: " + newPoint[0] + ", time: " + newPoint[1]);
        Graphics2D graph = (Graphics2D) this.getGraphics();
        graph.setColor(COLORS[color%COLORS.length]);
        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
        graph.drawLine(lastPoint[0], HEIGHT - lastPoint[1], newPoint[0], HEIGHT - newPoint[1]);
        graph.fillOval(newPoint[0] - POINT_DIAMETER/2, HEIGHT - (newPoint[1] + POINT_DIAMETER/2), POINT_DIAMETER, POINT_DIAMETER);
        lastPoint = newPoint;
    }
    
    public final void setOrigin(){
        this.lastPoint = new int[]{0, 0};
        this.color++;
    }
    
}
