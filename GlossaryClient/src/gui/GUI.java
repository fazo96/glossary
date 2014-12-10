package gui;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import main.Client;
import util.FileUtil;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

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
        bDelete = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        file = new javax.swing.JMenu();
        manual = new javax.swing.JMenuItem();
        about = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        bImport = new javax.swing.JMenuItem();
        bExport = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        exit = new javax.swing.JMenuItem();
        edit = new javax.swing.JMenu();
        settings = new javax.swing.JMenuItem();
        network = new javax.swing.JMenu();
        net = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Glossary");
        setMinimumSize(new java.awt.Dimension(400, 300));

        entryList.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
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
        currentMeaning.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
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
        search.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                searchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                searchFocusLost(evt);
            }
        });
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });

        bDelete.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        bDelete.setText("Delete");
        bDelete.setMaximumSize(new java.awt.Dimension(75, 23));
        bDelete.setMinimumSize(new java.awt.Dimension(75, 23));
        bDelete.setPreferredSize(new java.awt.Dimension(75, 23));
        bDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDeleteActionPerformed(evt);
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
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(bNew, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bSave, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(search))
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

        bImport.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        bImport.setText("Import");
        bImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bImportActionPerformed(evt);
            }
        });
        file.add(bImport);

        bExport.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        bExport.setText("Export");
        bExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bExportActionPerformed(evt);
            }
        });
        file.add(bExport);
        file.add(jSeparator2);

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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            // Upsert to glossary.
            Client.getGlossary().upsert(term, meaning);
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
        // Upsert to glossary
        Client.getGlossary().upsert((String) entryList.getSelectedValue(), currentMeaning.getText());
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
     * Event handler for Import button. It imports records from a file into the
     * Glossary.
     *
     * @param evt the event
     */
    private void bImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bImportActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(true);
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            for (File f : fc.getSelectedFiles()) // Import from string
            {
                Client.getGlossary().fromString(FileUtil.readFile(f));
            }
        }
    }//GEN-LAST:event_bImportActionPerformed
    /**
     * Event handler for the Export button. It exports records from a file into
     * the Glossary
     *
     * @param evt the event
     */
    private void bExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bExportActionPerformed
        JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            FileUtil.writeFile(fc.getSelectedFile(), Client.getGlossary().asString());
        }
    }//GEN-LAST:event_bExportActionPerformed
    /**
     * When the search box gains focus, if it's displaying the default text then
     * clear it
     *
     * @param evt the event
     */
    private void searchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchFocusGained
        if (search.getText().equals(defaultSearchFieldValue)) {
            search.setText("");
            search.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            search.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_searchFocusGained
    /**
     * When the search box loses focus, if it's empty then put the default value
     * back in
     *
     * @param evt
     */
    private void searchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchFocusLost
        if (search.getText().equals("")) {
            search.setText(defaultSearchFieldValue);
            search.setFont(new Font("Segoe UI", Font.ITALIC, 10));
            search.setForeground(Color.GRAY);
        }
    }//GEN-LAST:event_searchFocusLost

    private void bDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDeleteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bDeleteActionPerformed
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
            currentMeaning.setText(Client.getGlossary().meaningOf((String) entryList.getSelectedValue()));
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
    private javax.swing.JButton bDelete;
    private javax.swing.JMenuItem bExport;
    private javax.swing.JMenuItem bImport;
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
    private javax.swing.JPopupMenu.Separator jSeparator2;
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
