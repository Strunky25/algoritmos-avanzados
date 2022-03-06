/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capitulo1.view;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 *
 * @author elsho
 */
public class AnimationPanel extends JPanel {

    public static final int HEIGHT = 350, WIDTH = 350;
    
    private static final int pointDiameter = 4;
    
    private int[] lastPoint;
    
    public AnimationPanel() {
        setOrigin();
    }
    
    public void animatePoint(int[] newPoint){
        //System.out.println(Arrays.toString(newPoint));
        Graphics2D graph = (Graphics2D) this.getGraphics();
        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
        graph.drawLine(lastPoint[0], HEIGHT - lastPoint[1], newPoint[0], HEIGHT - newPoint[1]);
        graph.fillOval(newPoint[0] - pointDiameter/2, HEIGHT - (newPoint[1] + pointDiameter/2), pointDiameter, pointDiameter);
        lastPoint = newPoint;
    }
    
    public final void setOrigin(){
        this.lastPoint = new int[]{0, 0};
    }
    
}
