/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import model.Model;

/**
 *
 * @author elsho
 */
public class CompareFrame extends JFrame {

    /* Constants */
    private final static int PANEL_SIZE = 400, TEXT_PADDING = 5;

    /* Swing Components */
    private JPanel animationPanel;
    private JButton startBtn, stopBtn;
    private JProgressBar progressBar;

    private int size, lastX, lastY1, lastY2, lastY3;

    public CompareFrame() {
        size = Model.N_TESTS;
        initComponents();
    }

    private void initComponents() {
        setTitle("Compare Algorithms");
        // setIconImage(new ImageIcon("resources/icon.png").getImage());
        //setDefaultCloseOperation(EXIT_ON_CLOSE);

        animationPanel = new JPanel();
        animationPanel.setBackground(Color.lightGray);

        startBtn = new JButton("Start");
        stopBtn = new JButton("Stop");

        progressBar = new JProgressBar();

        addComponents();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void addComponents() {
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(animationPanel);
        animationPanel.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, PANEL_SIZE, Short.MAX_VALUE));
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(animationPanel, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(58, 58, 58))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(134, 134, 134)
                                .addComponent(startBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 90,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 259,
                                        Short.MAX_VALUE)
                                .addComponent(stopBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 90,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(127, 127, 127)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(animationPanel, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21,
                                        Short.MAX_VALUE)
                                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(stopBtn)
                                        .addComponent(startBtn))
                                .addGap(25, 25, 25)));
        pack();
    }

    public void animate(int n, long[] times) {
        int x = n * (animationPanel.getWidth() / size);
        int y1 = (int) times[0] / 100000;
        int y2 = (int) times[1] / 100000;
        int y3 = (int) times[1] / 100000;
        System.out.println("x:" + x + ", y1: " + y1 + ", y2: " + y2);
        Graphics2D graph = (Graphics2D) animationPanel.getGraphics();
        graph.setColor(Color.blue);
        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graph.drawLine(lastX, PANEL_SIZE - lastY1, x, PANEL_SIZE - y1);
        graph.setColor(Color.red);
        graph.drawLine(lastX, PANEL_SIZE - lastY2, x, PANEL_SIZE - y2);
        graph.setColor(Color.black);
        graph.drawLine(lastX, PANEL_SIZE - lastY3, x, PANEL_SIZE - y3);
        // graph.fillOval(x - POINT_DIAMETER/2, PANEL_SIZE - (y + POINT_DIAMETER/2),
        // POINT_DIAMETER, POINT_DIAMETER);
        lastX = x;
        lastY1 = y1;
        lastY2 = y2;
        lastY3 = y3;
    }

    public void drawlastPointTexts() {
        // animationPanel.writePoint(text);
        Graphics2D graph = (Graphics2D) animationPanel.getGraphics();
        graph.setColor(Color.blue);
        int y1 = Math.max(TEXT_PADDING, PANEL_SIZE - lastY1);
        graph.drawString("Traditional", lastX + TEXT_PADDING, y1 + TEXT_PADDING);
        graph.setColor(Color.red);
        int y2 = Math.max(TEXT_PADDING, PANEL_SIZE - lastY2);
        graph.drawString("Karatsuba", lastX + TEXT_PADDING, y2 + TEXT_PADDING);
        graph.setColor(Color.black);
        int y3 = Math.max(TEXT_PADDING, PANEL_SIZE - lastY3);
        graph.drawString("Mixed", lastX + TEXT_PADDING, y3 + TEXT_PADDING);
    }

    public boolean getInputSize() {
        JSpinner spin = new JSpinner(new SpinnerNumberModel(200, 0, 1000, 50));
        JPanel content = new JPanel();
        content.add(new JLabel("Test different algorithms with numbers up to N digits:"));
        content.add(spin);
        int option = JOptionPane.showOptionDialog(null, content, "Choose Test Size", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (option == JOptionPane.CANCEL_OPTION) {
            return false;
        }
        this.size = (int) spin.getValue();
        return true;
    }

    public void setProgress(int progress) {
        this.progressBar.setValue(progress);
    }

    public void addActionListener(ActionListener listener) {
        startBtn.addActionListener(listener);
        stopBtn.addActionListener(listener);
    }

    public int getTestSize() {
        return this.size;
    }
}
