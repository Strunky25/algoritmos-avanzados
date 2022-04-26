/*
    Algoritmes Avançats - Capitulo 4
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package view;

import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Class that contains the Swing GUI for the project.
 */
public class View extends JFrame {

    /* Constants */

    /* Variables */
    private File selectedFile;

    /* Swing Components */
    private JScrollPane scrollPane;
    private JTextArea infoArea;
    private JButton fileBtn, compressBtn, decompressBtn;
    private JProgressBar progressBar;
    private JFileChooser fileChooser;

    public View() {
        initComponents();
    }

    /**
     * Method that initializes swing components, and sets JFrame properties.
     */
    private void initComponents() {
        setTitle("Capitulo 4");
        setIconImage(new ImageIcon("resources/icon.png").getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        /* Init Components */
        infoArea = new JTextArea(
                "Program that compresses or decompresses a choosen file \nwith Huffman Coding.\n\nSelect a File to Continue",
                5, 20);
        scrollPane = new JScrollPane(infoArea);

        fileBtn = new JButton("Choose File");
        fileBtn.addActionListener((e) -> chooseFile());
        compressBtn = new JButton("Compress");
        decompressBtn = new JButton("Decompress");

        progressBar = new JProgressBar();

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
                                .addGap(66, 66, 66)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(scrollPane)
                                        .addGroup(layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(fileBtn)
                                                        .addGap(85, 85, 85)
                                                        .addComponent(compressBtn)
                                                        .addGap(77, 77, 77)
                                                        .addComponent(decompressBtn))))
                                .addContainerGap(71, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 123,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39,
                                        Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(fileBtn)
                                        .addComponent(compressBtn)
                                        .addComponent(decompressBtn))
                                .addGap(30, 30, 30)
                                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47)));
        pack();
    }

    /* METHODS */
    private void chooseFile() {
        FileSelectorFrame fsFrame = new FileSelectorFrame();
        fsFrame.setVisible(true);
    }

    public File getSelectedFile() {
        return selectedFile;
    }

    public void setInfoArea(String text) {
        infoArea.setText(text);
    }

    // Add Listeners
    public void addListeners(ActionListener listener) {
        compressBtn.addActionListener(listener);
        decompressBtn.addActionListener(listener);
    }
}
