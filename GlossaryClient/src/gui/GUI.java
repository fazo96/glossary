package gui;

import java.util.Arrays;
import java.util.Collections;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import main.Client;

/**
 * Main User Interface of the Glossary
 *
 * @author Gian
 */
public class GUI extends javax.swing.JFrame implements ListSelectionListener {

    private UserManual manualWindow;
    private About aboutWindow;
    private String defaultSearchFieldValue;

    /**
     * Creates the GUI
     */
    public GUI() {
        initComponents();
        defaultSearchFieldValue = search.getText();
        // Display the window at the center of the screen (fixes error on linux)
        setLocationRelativeTo(null);
        // Set JList selection event:
        entryList.addListSelectionListener(this);
        // When the content of the Search bar changes, update the list of terms
        search.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTermList();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTermList();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateTermList();
            }
        });
    }

    /**
     * Update the list of terms in the GUI.
     */
    public void updateTermList() {
        if (search.getText() != null && !search.getText().equals(defaultSearchFieldValue)) {
            entryList.setListData(Client.getGlossary().getSortedWordList(search.getText()));
        } else {
            entryList.setListData(Client.getGlossary().getSortedWordList());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        entryList = new javax.swing.JList();
        bNew = new javax.swing.JButton();
        entries = new javax.swing.JLabel();
        meaning = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        currentMeaning = new javax.swing.JTextArea();
        bSave = new javax.swing.JButton();
        search = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        file = new javax.swing.JMenu();
        manual = new javax.swing.JMenuItem();
        about = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        exit = new javax.swing.JMenuItem();
        edit = new javax.swing.JMenu();
        settings = new javax.swing.JMenuItem();
        network = new javax.swing.JMenu();
        net = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Glossary");
        setMinimumSize(new java.awt.Dimension(400, 300));

        entryList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(entryList);

        bNew.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        bNew.setText("Create");
        bNew.setMaximumSize(new java.awt.Dimension(75, 23));
        bNew.setMinimumSize(new java.awt.Dimension(75, 23));
        bNew.setPreferredSize(new java.awt.Dimension(75, 23));
        bNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bNewActionPerformed(evt);
            }
        });

        entries.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        entries.setText("Entries:");

        meaning.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        meaning.setText("Meaning:");

        jScrollPane2.setMaximumSize(new java.awt.Dimension(170, 100));
        jScrollPane2.setMinimumSize(new java.awt.Dimension(170, 100));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(170, 100));

        currentMeaning.setColumns(20);
        currentMeaning.setRows(5);
        jScrollPane2.setViewportView(currentMeaning);

        bSave.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        bSave.setText("Save");
        bSave.setMaximumSize(new java.awt.Dimension(65, 23));
        bSave.setMinimumSize(new java.awt.Dimension(65, 23));
        bSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSaveActionPerformed(evt);
            }
        });

        search.setFont(new java.awt.Font("Segoe UI", 2, 10)); // NOI18N
        search.setForeground(new java.awt.Color(153, 153, 153));
        search.setText("Search...");
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(entries)
                        .addGap(135, 135, 135)
                        .addComponent(meaning)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(search, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(bNew, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bSave, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(entries)
                    .addComponent(meaning))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(search, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(bNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        file.setText("File");

        manual.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        manual.setText("User Manual");
        manual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manualActionPerformed(evt);
            }
        });
        file.add(manual);

        about.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        about.setText("About");
        about.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutActionPerformed(evt);
            }
        });
        file.add(about);
        file.add(jSeparator1);

        exit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        exit.setText("Exit");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });
        file.add(exit);

        jMenuBar1.add(file);

        edit.setText("Edit");

        settings.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        settings.setText("Settings");
        settings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsActionPerformed(evt);
            }
        });
        edit.add(settings);

        jMenuBar1.add(edit);

        network.setText("Network");

        net.setText("Connect");
        net.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                netActionPerformed(evt);
            }
        });
        network.add(net);

        jMenuBar1.add(network);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Event handler for the exit button it exits the program
     *
     * @param evt the event
     */
    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        this.dispose();
    }//GEN-LAST:event_exitActionPerformed

    /**
     * Event handler for the settings button it opens the settings window
     *
     * @param evt the event
     */
    private void settingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_settingsActionPerformed

    /**
     * Event handler for the about button it opens the about window
     *
     * @param evt the event
     */
    private void aboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutActionPerformed
        if (aboutWindow == null) {
            aboutWindow = new About();
        }
        aboutWindow.setVisible(true);
    }//GEN-LAST:event_aboutActionPerformed

    /**
     * Event handler for the user manual button. It opens the user manual window
     *
     * @param evt the event
     */
    private void manualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manualActionPerformed
        if (manualWindow == null) {
            manualWindow = new UserManual();
        }
        manualWindow.setVisible(true);
    }//GEN-LAST:event_manualActionPerformed
    /**
     * Event handler for the New button. It allows the user to add a term
     *
     * @param evt the event
     */
    private void bNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bNewActionPerformed
        String term = JOptionPane.showInputDialog("Insert term");
        String meaning = JOptionPane.showInputDialog("Insert initial meaning");
        if (term != null && meaning != null && term != "" && meaning != "") {
            Client.getConnection().send(term + ":" + meaning);
        }
    }//GEN-LAST:event_bNewActionPerformed
    /**
     * Event handler for the Save button. It allows the user to save the meaning
     * he's writing.
     *
     * @param evt the event
     */
    private void bSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSaveActionPerformed
        if (entryList.getSelectedIndex() < 0 || entryList.getSelectedValue() == "") {
            JOptionPane.showMessageDialog(this, "You must select a Term to save.");
            return;
        }
        if (currentMeaning.getText() == "") {
            JOptionPane.showMessageDialog(this, "Can't set an empty Meaning for a Term");
            return;
        }
        Client.getConnection().send((String) entryList.getSelectedValue() + ":" + currentMeaning.getText());
    }//GEN-LAST:event_bSaveActionPerformed
    /**
     * Event handler for the Connect/Disconnect button. It connects or
     * disconnects from the Server.
     *
     * @param evt the event
     */
    private void netActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_netActionPerformed
        if (Client.getConnection().isConnected()) {
            Client.getConnection().disconnect();
            net.setText("Connect");
        } else {
            if (Client.getConnection().connect()) {
                // If connection is successfull
                net.setText("Disconnect");
            }
        }
    }//GEN-LAST:event_netActionPerformed

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchActionPerformed
    /**
     * Recalculates the value of the current meaning in the text area
     */
    public void resetCurrentMeaning() {
        if (entryList.getSelectedIndex() < 0) {
            currentMeaning.setEditable(false);
            bSave.setEnabled(false);
            currentMeaning.setText("Select a term");
        } else {
            currentMeaning.setEditable(true);
            bSave.setEnabled(true);
            currentMeaning.setText(Client.getGlossary().meaningOf(entryList.getSelectedIndex()));
        }
    }

    /**
     * Returns the Text Area in which the current meaning is written
     *
     * @return a JTextArea
     */
    public JTextArea getCurrentMeaning() {
        return currentMeaning;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem about;
    private javax.swing.JButton bNew;
    private javax.swing.JButton bSave;
    private javax.swing.JTextArea currentMeaning;
    private javax.swing.JMenu edit;
    private javax.swing.JLabel entries;
    private javax.swing.JList entryList;
    private javax.swing.JMenuItem exit;
    private javax.swing.JMenu file;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenuItem manual;
    private javax.swing.JLabel meaning;
    private javax.swing.JMenuItem net;
    private javax.swing.JMenu network;
    private javax.swing.JTextField search;
    private javax.swing.JMenuItem settings;
    // End of variables declaration//GEN-END:variables

    @Override
    /**
     * This function gets called when the selection of the list changes. It
     * resets the value of the TextArea.
     */
    public void valueChanged(ListSelectionEvent lse) {
        resetCurrentMeaning();
    }
}
