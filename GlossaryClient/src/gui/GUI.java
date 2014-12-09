package gui;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import main.Client;

/**
 *
 * @author Gian
 */
public class GUI extends javax.swing.JFrame implements ListSelectionListener {

    private UserManual manualWindow;

    /**
     * Creates the GUI
     */
    public GUI() {
        initComponents();
        // Display the window at the center of the screen (fixes error on linux)
        setLocationRelativeTo(null);
        // Set JList selection event:
        entryList.addListSelectionListener(this);
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
        jMenuBar1 = new javax.swing.JMenuBar();
        file = new javax.swing.JMenu();
        manual = new javax.swing.JMenuItem();
        about = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        exit = new javax.swing.JMenuItem();
        edit = new javax.swing.JMenu();
        settings = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Glossary");
        setMinimumSize(new java.awt.Dimension(400, 300));
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        entryList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(entryList);

        bNew.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        bNew.setText("Create");
        bNew.setMaximumSize(new java.awt.Dimension(65, 23));
        bNew.setMinimumSize(new java.awt.Dimension(65, 23));
        bNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bNewActionPerformed(evt);
            }
        });

        entries.setText("Entries:");

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
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(bNew, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bSave, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE))))
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
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE))
                .addContainerGap())
        );

        getContentPane().add(jPanel1);

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

        settings.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        settings.setText("Settings");
        settings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsActionPerformed(evt);
            }
        });
        edit.add(settings);

        jMenuBar1.add(edit);

        setJMenuBar(jMenuBar1);

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
     * @param evt
     */
    private void settingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_settingsActionPerformed

    /**
     * Event handler for the about button it opens the about window
     *
     * @param evt
     */
    private void aboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_aboutActionPerformed

    /**
     * Event handler for the user manual button. It opens the user manual window
     *
     * @param evt
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
     * @param evt
     */
    private void bNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bNewActionPerformed
        String term = JOptionPane.showInputDialog("Insert term");
        String meaning = JOptionPane.showInputDialog("Insert initial meaning");
        if(term != null && meaning != null && term != "" && meaning != "")
            Client.getConnection().send(term+":"+meaning);
    }//GEN-LAST:event_bNewActionPerformed
    /**
     * Event handler for the Save button. It allows the user to save the meaning
     * he's writing.
     *
     * @param evt
     */
    private void bSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSaveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bSaveActionPerformed
    /**
     * Recalculates the value of the current meaning
     */
    public void setCurrentMeaning() {
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

    /**
     * Returns the JList with the entries of the Glossary.
     *
     * @return a JList
     */
    public JList getEntryList() {
        return entryList;
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
    private javax.swing.JMenuItem settings;
    // End of variables declaration//GEN-END:variables

    @Override
    /**
     * This function gets called when the selection of the list changes.
     * It resets the value of the TextArea.
     */
    public void valueChanged(ListSelectionEvent lse) {
       setCurrentMeaning();
    }
}
