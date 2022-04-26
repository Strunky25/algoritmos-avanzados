/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
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

/**
 *
 * @author elsho
 */
public class FileSelectorFrame extends JFrame{
    
    private static final Image fileIcon = new ImageIcon("resources/file.png").getImage();
    
    private JPanel dragPanel;
    private JButton fileBtn, confirmBtn;
    private JLabel infoLabel, dragLabel;
    private File selectedFile;
    
    public FileSelectorFrame(){
        initComponents();
    }
    
    private void initComponents(){
        setTitle("Select or Drop a File");

        /* Init Components */
        dragPanel = new JPanel();
        dragPanel.setBorder(BorderFactory.createDashedBorder(
                Color.blue, 1.5f, 4, 4, false));
        dragLabel = new JLabel("drag in here");
        dragLabel.setForeground(Color.blue);
        dragLabel.setHorizontalAlignment(JLabel.CENTER);
        dragLabel.setHorizontalTextPosition(JLabel.CENTER);
        dragLabel.setVerticalTextPosition(JLabel.CENTER);
        infoLabel = new JLabel("Drag and Drop a File or Select One:");
        
        fileBtn = new JButton("Select File");
        fileBtn.addActionListener((e) -> openFile());
        confirmBtn = new JButton("Confirm");
        confirmBtn.addActionListener((e) -> confirmSelection());

        DragDropListener dragListener = new DragDropListener();
        new DropTarget(dragPanel, dragListener);
        
        addComponents();
        setResizable(false);
        setLocationRelativeTo(null);
    }
    
    private void addComponents(){
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(dragPanel);
        dragPanel.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(101, Short.MAX_VALUE)
                .addComponent(dragLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(108, Short.MAX_VALUE)
                .addComponent(dragLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100))
        );
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(dragPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(infoLabel)
                        .addGap(18, 18, 18)
                        .addComponent(fileBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(50, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(confirmBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(201, 201, 201))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(dragPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(infoLabel)
                    .addComponent(fileBtn))
                .addGap(18, 18, 18)
                .addComponent(confirmBtn)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        pack();
    }
    
    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        switch (fileChooser.showOpenDialog(this)) {
            case JFileChooser.APPROVE_OPTION -> {
                selectedFile = fileChooser.getSelectedFile();
            }
            case JFileChooser.CANCEL_OPTION -> {
                return;
            }
            case JFileChooser.ERROR_OPTION -> {
                return;
            }
        }
    }
    
    private void confirmSelection(){
        if(selectedFile == null) return;
        this.dispose();
    }

    public void addConfirmListener(ActionListener listener){
        confirmBtn.addActionListener(listener);
    }

    public File getSelectedFile() {
        return selectedFile;
    }

    private class DragDropListener implements DropTargetListener {

        @Override
        public void dragEnter(DropTargetDragEvent dtde) {}

        @Override
        public void dragOver(DropTargetDragEvent dtde) {
            dragPanel.setBackground(new Color(184, 212, 214));
        }

        @Override
        public void dropActionChanged(DropTargetDragEvent dtde) {}

        @Override
        public void dragExit(DropTargetEvent dte) {
            dragPanel.setBackground(Color.white);
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
                        selectedFile = (File) files.get(0);
                        dragLabel.setIcon(new ImageIcon(fileIcon.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH)));
                        dragLabel.setHorizontalTextPosition(JLabel.CENTER);
                        dragLabel.setVerticalTextPosition(JLabel.BOTTOM);
                        dragLabel.setText(selectedFile.getName());
                    }
                } catch (UnsupportedFlavorException | IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            event.dropComplete(true);
        }
    }
}
