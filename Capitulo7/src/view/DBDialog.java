/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

/**
 *
 * @author elsho
 */
public class DBDialog {

    private JOptionPane pane;
    private JDialog dialog;
    private JProgressBar progressBar;

    public DBDialog() {
        initComponents();
    }

    private void initComponents() {
        progressBar = new JProgressBar(1, 100);
        SwingUtilities.invokeLater(() -> {
            pane = new JOptionPane("", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
            pane.setMessage("Creating DataBase...");
            pane.add(progressBar, 1);
            dialog = pane.createDialog("Information message");
            dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            dialog.setVisible(true);
        });

    }

    public void setProgress(int value) {
        progressBar.setValue(value);
    }

    public void dispose() {
        dialog.setVisible(false);
        dialog.dispose();
    }
}
