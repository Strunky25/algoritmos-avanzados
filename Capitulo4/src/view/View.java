/*
    Algoritmes Avançats - Capitulo 4
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package view;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * Class that contains the Swing GUI for the project.
 */
public class View extends JFrame {
    
    /* Variables */
    private File selectedFile;
    
    /* Swing Components */
    private JPanel dndPanel, filePanel;
    private JLabel iconLbl, dndInfoLbl, dndOrLbl, fileIconLbl, fileNameLbl, fileSizeLbl;
    private JButton browseBtn, compressBtn, decompressBtn;
    private JProgressBar progressBar;

    /**
     * Constructor
     */
    public View() {
        initComponents();
    }
    
    /**
     * Method that initializes all Swing Components
     */
    private void initComponents(){
        setTitle("Capitulo 4");
        setIconImage(new ImageIcon("resources/icon.png").getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        /* Init Components */
        dndPanel = new JPanel();
        dndPanel.setBackground(Color.white);
        dndPanel.setBorder(BorderFactory.createDashedBorder(
                Color.blue, 1.5f, 4, 4, false));
        DragDropListener dragListener = new DragDropListener();
        new DropTarget(dndPanel, dragListener);
        
        filePanel = new JPanel();
 
        iconLbl = new JLabel();
        iconLbl.setIcon(new ImageIcon(new ImageIcon("resources/upload.png").getImage().getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH)));
        
        dndInfoLbl = new JLabel("Drag & Drop file here");
        dndOrLbl = new JLabel("or");
        
        fileIconLbl = new JLabel();
        fileNameLbl = new JLabel();
        fileNameLbl.setVerticalTextPosition(JLabel.CENTER);
        
        fileSizeLbl = new JLabel();
        fileSizeLbl.setHorizontalAlignment(JLabel.RIGHT);
        fileSizeLbl.setVerticalTextPosition(JLabel.CENTER);
        
        browseBtn = new JButton("Browse Files");
        browseBtn.addActionListener((e) -> browseFile());
        
        compressBtn = new JButton("Compress");
        compressBtn.setEnabled(false);
        
        decompressBtn = new JButton("Decompress");
        decompressBtn.setEnabled(false);

        progressBar = new JProgressBar();

        addComponents();
        setResizable(false);
        setLocationRelativeTo(null);
    }
    
    /**
     * Method that sets the layout of the frame adding all components
     */
    private void addComponents(){
        javax.swing.GroupLayout dndPanelLayout = new javax.swing.GroupLayout(dndPanel);
        dndPanel.setLayout(dndPanelLayout);
        dndPanelLayout.setHorizontalGroup(
            dndPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dndPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(dndPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dndPanelLayout.createSequentialGroup()
                        .addComponent(dndOrLbl)
                        .addGap(195, 195, 195))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dndPanelLayout.createSequentialGroup()
                        .addComponent(dndInfoLbl)
                        .addGap(138, 138, 138))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dndPanelLayout.createSequentialGroup()
                        .addComponent(browseBtn)
                        .addGap(153, 153, 153))))
            .addGroup(dndPanelLayout.createSequentialGroup()
                .addGap(173, 173, 173)
                .addComponent(iconLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        dndPanelLayout.setVerticalGroup(
            dndPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dndPanelLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(iconLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dndInfoLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dndOrLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(browseBtn)
                .addGap(50, 50, 50))
        );

        javax.swing.GroupLayout filePanelLayout = new javax.swing.GroupLayout(filePanel);
        filePanel.setLayout(filePanelLayout);
        filePanelLayout.setHorizontalGroup(
            filePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filePanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(fileIconLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(fileNameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(fileSizeLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        filePanelLayout.setVerticalGroup(
            filePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(filePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(filePanelLayout.createSequentialGroup()
                        .addComponent(fileSizeLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(fileNameLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fileIconLbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(compressBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(decompressBtn)
                        .addGap(100, 100, 100))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(filePanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(progressBar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dndPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(50, 50, 50))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(dndPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(filePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(compressBtn)
                    .addComponent(decompressBtn))
                .addGap(25, 25, 25)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        pack();
    }
    
    /**
     * Method that shows the user a file selection panel.
     */
    private void browseFile(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            this.selectedFile = fileChooser.getSelectedFile();
            showFilePanel();
        }
    }
    
    /**
     * Method that shows the current selected file on the frame.
     */
    private void showFilePanel(){
        fileNameLbl.setText(selectedFile.getName());
        fileIconLbl.setIcon(new ImageIcon(new ImageIcon("resources/file.png").getImage().getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH)));
        fileSizeLbl.setText(selectedFile.length() > 1000000 ? selectedFile.length()/1000000 + " mB": selectedFile.length()/1000 + " kB");
        dndPanel.setBackground(Color.white);
        filePanel.setBackground(Color.white);
        filePanel.setBorder(BorderFactory.createDashedBorder(
                Color.blue, 1.5f, 4, 4, false));
        compressBtn.setEnabled(true);
        decompressBtn.setEnabled(true);
    }
    
    /**
     * Method that returns the file selected by the user.
     * @return File current selected file.
     */
    public File getSelectedFile() {
        return selectedFile;
    }

    /**
     * Method that adds a listener to the GUI Buttons.
     * @param listener ActionListener to add.
     */
    public void addListeners(ActionListener listener) {
        compressBtn.addActionListener(listener);
        decompressBtn.addActionListener(listener);
    }
    
    /**
     * Method that updates the progress bar value.
     * @param progress int value to set on progress bar.
     */
    public void setProgress(int progress){
        progressBar.setValue(progress);
    }
    
    /**
     * Nested class to implement drag and drop functionality.
     */
    private class DragDropListener implements DropTargetListener {

        @Override
        public void dragEnter(DropTargetDragEvent dtde) {}

        @Override
        public void dragOver(DropTargetDragEvent dtde) {
            dndPanel.setBackground(new Color(225, 247, 246));
        }

        @Override
        public void dropActionChanged(DropTargetDragEvent dtde) {}

        @Override
        public void dragExit(DropTargetEvent dte) {
            dndPanel.setBackground(Color.white);
        }

        @Override
        public void drop(DropTargetDropEvent event) {
            event.acceptDrop(DnDConstants.ACTION_COPY);
            Transferable transferable = event.getTransferable();
            DataFlavor[] flavors = transferable.getTransferDataFlavors();
            for (DataFlavor flavor : flavors) {
                try {
                    if (flavor.isFlavorJavaFileListType()) {
                        List files = (List) transferable.getTransferData(flavor); 
                        File file = (File) files.get(0);
                        if(file.isDirectory()){
                            dndPanel.setBackground(Color.white);
                            Runnable sound = (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.default");
                            if(sound != null) sound.run();
                            return;
                        }
                        selectedFile = file;
                        showFilePanel();
                    }
                } catch (UnsupportedFlavorException | IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            event.dropComplete(true);
        }
    }
}
