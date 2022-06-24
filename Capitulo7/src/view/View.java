/*
    Algoritmes Avançats - Capitulo 7
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import model.FlagColor;

/**
 * Class that contains the Swing GUI for the project.
 */
public class View extends JFrame{
    
    /* Constants */
    private static final int PANEL_WIDTH = 350, PANEL_HEIGHT = 250;
    private static final FlagColor[] COLORS = FlagColor.values();
    
    /* Variables */
    
    /* Swing Components */
    private JPanel imgPanel, guessPanel;
    private JLabel flagImgLbl, countryLbl, guessLbl, guessImgLbl, guessCountryLbl;
    private JButton rndFlagBtn, guessBtn;
    private JLabel[] colorLbl;
    private JProgressBar[] colorPb;
    
    public View(){
        initComponents();
    }
    
    /**
     * Method that initializes swing components, and sets JFrame properties.
     */
    private void initComponents(){
        setTitle("Capitulo 7");
        setIconImage(new ImageIcon("resources/icon.png").getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        /* Init Components */
        imgPanel = new JPanel();
        imgPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        
        flagImgLbl = new JLabel();
        countryLbl = new JLabel("Country:");
        
        rndFlagBtn = new JButton("Random Flag");
        guessBtn = new JButton("Guess Country");
        
        guessPanel = new JPanel();
        //guessPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        
        guessLbl = new JLabel("Guess:");
        guessImgLbl = new JLabel();
        guessCountryLbl = new JLabel();
        
        colorLbl = new JLabel[COLORS.length];
        colorPb = new JProgressBar[COLORS.length];
        for (int i = 0; i < COLORS.length; i++) {
            colorLbl[i] = new JLabel(COLORS[i].toString());
            colorPb[i] = new JProgressBar();
        }     
        
        addComponents();
        setResizable(false);
        setLocationRelativeTo(null);
    }
    
    /**
     * Method that creates the frame layout with all its components.
     */
    private void addComponents(){
        /* Add Layout */
        javax.swing.GroupLayout imgPanelLayout = new javax.swing.GroupLayout(imgPanel);
        imgPanel.setLayout(imgPanelLayout);
        imgPanelLayout.setHorizontalGroup(
            imgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(flagImgLbl, javax.swing.GroupLayout.DEFAULT_SIZE, PANEL_WIDTH, Short.MAX_VALUE)
        );
        imgPanelLayout.setVerticalGroup(
            imgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(flagImgLbl, javax.swing.GroupLayout.DEFAULT_SIZE, PANEL_HEIGHT, Short.MAX_VALUE)
        );
        
        BoxLayout guessPanelLayout = new BoxLayout(guessPanel, BoxLayout.PAGE_AXIS);
        guessPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        guessPanel.setLayout(guessPanelLayout);
        guessPanel.add(Box.createVerticalGlue());
        guessPanel.add(guessLbl);
        guessPanel.add(Box.createVerticalGlue());
        guessPanel.add(guessImgLbl);
        guessPanel.add(Box.createVerticalGlue());
        guessPanel.add(guessCountryLbl);
        for (int i = 0; i < COLORS.length; i++) {
            guessPanel.add(Box.createVerticalGlue());
            guessPanel.add(colorLbl[i]);
            guessPanel.add(Box.createVerticalGlue());
            guessPanel.add(colorPb[i]);
        }
        guessPanel.add(Box.createVerticalGlue());
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(imgPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(rndFlagBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(guessBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(137, 137, 137)
                        .addComponent(countryLbl)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(guessPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(countryLbl)
                        .addGap(18, 18, 18)
                        .addComponent(imgPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(guessPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rndFlagBtn)
                    .addComponent(guessBtn))
                .addGap(16, 16, 16))
        );
        pack();
    }

    /* METHODS */
    public void setFlagImage(BufferedImage flagImage, String countryName){  
        float ratio = (float) flagImage.getWidth()/flagImage.getHeight();
        Image tmp = flagImage.getScaledInstance(PANEL_WIDTH, (int) (PANEL_WIDTH/ratio), Image.SCALE_SMOOTH);
        if(tmp.getHeight(null) > (PANEL_HEIGHT + 25)){
            tmp = tmp.getScaledInstance((int) (PANEL_HEIGHT/ratio), PANEL_HEIGHT, Image.SCALE_SMOOTH);
        }
        imgPanel.setPreferredSize(new Dimension(tmp.getWidth(null), tmp.getHeight(null)));
        flagImgLbl.setIcon(new ImageIcon(tmp));
        countryLbl.setText("Country: " + countryName);
    }
    
    public int setGuessImage(BufferedImage flagImage, String countryName, double[] percentages){
        int ret = 0;
        float ratio = (float) flagImage.getWidth()/flagImage.getHeight();
        Image tmp = flagImage.getScaledInstance(70, (int) (70/ratio), Image.SCALE_SMOOTH);
        if(tmp.getHeight(null) > (PANEL_HEIGHT/10 + 25)){
            tmp = tmp.getScaledInstance((int) (50/ratio), 50, Image.SCALE_SMOOTH);
        }
        //imgPanel.setPreferredSize(new Dimension(tmp.getWidth(null), tmp.getHeight(null)));
        guessImgLbl.setIcon(new ImageIcon(tmp));
        guessCountryLbl.setText(countryName);
        guessCountryLbl.setFont(new Font("Courier", Font.BOLD, 12));
        if(countryLbl.getText().contains(countryName)){
            guessCountryLbl.setForeground(Color.green);
            ret = 1;
        } else {
            guessCountryLbl.setForeground(Color.red);
            ret = 0;
        }
        if(percentages.length != COLORS.length) return ret;
        for (int i = 0; i < percentages.length; i++) {
            colorPb[i].setValue((int) (percentages[i]*100));
        }
        return ret;
    }
    
    // Add Listeners
    public void addListeners(ActionListener listener){
        rndFlagBtn.addActionListener(listener);
        guessBtn.addActionListener(listener);
    }
}
