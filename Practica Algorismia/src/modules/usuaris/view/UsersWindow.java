/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules.usuaris.view;

import core.view.Menu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import modules.usuaris.model.EstatTauler;
import modules.usuaris.model.*;
import modules.usuaris.repository.UsersRepository;

/**
 * Classe que representa la finestra per a gestionar els usuaris de l'aplicació.
 *
 * @author Bernat Galmés Rubert
 */
public class UsersWindow extends javax.swing.JFrame {

    private final UsersRepository users;
    private final ChessBoardWindow chessWindow;

    /**
     * Creates new form UsersWindow
     *
     * @param users Repository to query app users.
     */
    public UsersWindow(UsersRepository users) {
        this.users = users;

        initComponents();
        setLocationRelativeTo(null);
        addExitListener();

        TableCellRenderer buttonRenderer = new JTableButtonRenderer();
        jTable1.getColumn("botó").setCellRenderer(buttonRenderer);
        jTable1.addMouseListener(new JTableUsersButtonMouseListener(jTable1));
        chessWindow = new ChessBoardWindow(EstatTauler.SIZE);//edited to use board size.
        chessWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public javax.swing.table.TableModel getTableData() {
        return new JTableUsersModel();
    }

    private void addExitListener() {
        WindowAdapter exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(
                     null, "Would you like to try another problem?", 
                     "Exit Confirmation", JOptionPane.YES_NO_OPTION, 
                     JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 1) {
                   System.exit(0);
                } else {
                    java.awt.EventQueue.invokeLater(() -> {
                        new Menu().setVisible(true);
                    });
                }
            }
        };
        addWindowListener(exitListener);
    }

    /**
     * Classe que representa la visualització dels usuaris en forma de taula.
     */
    private class JTableUsersModel extends AbstractTableModel {

        private static final long serialVersionUID = 1L;
        private final String[] COLUMN_NAMES = new String[]{"ID", "Nom", "Mail", "Tipus usuari", "botó"};
        private final Class<?>[] COLUMN_TYPES = new Class<?>[]{java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, JButton.class};

        @Override
        public int getColumnCount() {
            return COLUMN_NAMES.length;
        }

        @Override
        public int getRowCount() {
            return users.findAll().size();
        }

        @Override
        public String getColumnName(int columnIndex) {
            return COLUMN_NAMES[columnIndex];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return COLUMN_TYPES[columnIndex];
        }

        /**
         * gets the user from rowIndex from the repository
         * and the data from that user specified by the columnIndex.
         * If columnIndex == 4, creates a JButton to show additional information
         * about the user.
         * @param rowIndex index of the user in the repository.
         * @param columnIndex index of the data from the user.
         * @return the specified data.
         */
        @Override
        public Object getValueAt(final int rowIndex, final int columnIndex) {
            User user = users.findAll().get(rowIndex);
            // TODO: implement it
            switch (columnIndex) {
                case 0:
                    return user.getId();
                case 1:
                    return user.getName();
                case 2:
                    return user.getEmail();
                case 3:
                    return user.getType();
                case 4:
                    JButton button = new JButton("deshablitat");
                    button.setEnabled(false);
                    if (user instanceof Player) {
                        button.setText("Resum");
                        button.addMouseListener(new JTableUsersButtonMouseListener(jTable1));
                        button.setEnabled(true);
                    } else if(user instanceof Referee){
                        button.setText("Info");
                        button.setEnabled(true);
                    }
                    return button;
                default:
                    return null;
            }
        }

    }

    /**
     * Classe que implementa l'escoltador del mouse quan es clica al botó d'un
     * usuari concret de la taula.
     */
    private class JTableUsersButtonMouseListener extends MouseAdapter {

        private final JTable table;

        public JTableUsersButtonMouseListener(JTable table) {
            this.table = table;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int column = table.getColumnModel().getColumnIndexAtX(e.getX()); // get the column of the button
            int row = e.getY() / table.getRowHeight(); //get the row of the button
            /*Checking the row or column is valid or not*/
            if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
                Object value = table.getValueAt(row, column);

                if (value instanceof JButton) {
                    /*perform a click event*/
                    //users.findAll().get(row);
                    // TODO: consulta usuari seleccionar, adaptar a la teva implementació
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            // TODO: implement it
                            User user = users.findAll().get(row); //gets the user.
                            if (user instanceof Player) { // if its a player:
                                Player player = (Player)user;
                                Partida partida = player.getNPartida(player.getPlayedGames() - 1);// get his first game
                                chessWindow.reset();//reset before displaying new game
                                chessWindow.setVisible(true); //show current player chessWindow
                                if (partida != null) { //if there is a first game
                                    chessWindow.colocaPeces(partida.getTauler().getTiles()); //display the current state of the game
                                    String text = "Numero de partidas jugadas: " +  
                                            String.valueOf(player.getPlayedGames()) + "\n" +
                                            "Numero de partidas ganadas: " +
                                            String.valueOf(player.getWonGames()) + "\n" + 
                                            "Numero de partidas no finalizadas: " +         
                                            String.valueOf(player.getUnfinishedGames()) + "\n";
                                    chessWindow.putTextAreaText(text); //display info about player stats
                                } else { //if there is no first game
                                    chessWindow.putTextAreaText("Este usuario no tiene ninguna partida."); //inform
                                    chessWindow.reset();
                                }
                            } else if (user instanceof Referee) { //if the user is referee
                                Referee referee = (Referee) user; //display info about his stats
                                JOptionPane.showMessageDialog(chessWindow, "Numero de partidas arbitradas: " + String.valueOf(referee.getRefereedGames()));
                            }

                        }
                    });
                }
            }
        }
    }

    private static class JTableButtonRenderer implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JButton button = (JButton) value;
            return button;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTable1.setModel(getTableData());
        jTable1.setShowGrid(false);
        jTable1.setShowVerticalLines(true);
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Veure resum");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(219, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(126, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        float[] accesosperclasse = users.meanAccessByClass();
        String text = "* Nombre mitj accessos arbitres: " + accesosperclasse[0];
        text += "\n* Nombre mitj accessos socis: " + accesosperclasse[1];
        text += "\n* Nombre mitj accessos jugadors: " + accesosperclasse[2];
        text += "\n* Usuari amb més accessos: " + users.findUserWithMaxAccess();
        text += "\n* Usuari amb més partides guanyades: " + users.findUserWithMaxWins();
        text += "\n* Usuari que no han accedit mai: " + String.join(", ", users.findUsersWithNoAccess());
        JOptionPane.showMessageDialog(this, text);
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
