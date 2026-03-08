package ui;

import utils.ThemeManager;
import ui.Timetable;
import database.DBConnection;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ViewTimetableFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ViewTimetableFrame.class.getName());

    public ViewTimetableFrame() {
        initComponents();
        for (java.awt.Component c : getContentPane().getComponents()) {
        if (c instanceof javax.swing.JButton b) {
        b.setFocusPainted(false);
        b.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
    }
}
        setLocationRelativeTo(null);
         setSize(500, 350);
         
         ThemeManager.applyTheme(this);
         
    }
    void loadTimetable() {
    try {
        Connection con = DBConnection.getConnection();

        String dept = cmbDept.getSelectedItem().toString();

        String sql;
        PreparedStatement pst;

        if (dept.equals("ALL")) {
            sql = "SELECT day, time_slot, subject, faculty, department FROM timetable";
            pst = con.prepareStatement(sql);
        } else {
            sql = "SELECT day, time_slot, subject, faculty, department FROM timetable WHERE department = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, dept);
        }

        ResultSet rs = pst.executeQuery();

        DefaultTableModel model = (DefaultTableModel) tblTimetable.getModel();
        model.setRowCount(0);

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("day"),
                rs.getString("time_slot"),
                rs.getString("subject"),
                rs.getString("faculty"),
                rs.getString("department")
            });
        }

        con.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
}

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmbDept = new javax.swing.JComboBox<>();
        bntLoad = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTimetable = new javax.swing.JTable();
        btnDarkMode = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        cmbDept.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ALL", "B.SC-CA", "BCA", "IT", " " }));

        bntLoad.setText("Load TimeTable");
        bntLoad.addActionListener(this::bntLoadActionPerformed);

        tblTimetable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Day", "Time", "Subject", "Faculty", "Department"
            }
        ));
        jScrollPane1.setViewportView(tblTimetable);

        btnDarkMode.setText("Dark Mode");
        btnDarkMode.addActionListener(this::btnDarkModeActionPerformed);

        btnBack.setText("Back");
        btnBack.addActionListener(this::btnBackActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(cmbDept, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDarkMode))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(bntLoad)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBack)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbDept, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDarkMode))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bntLoad)
                    .addComponent(btnBack))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bntLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntLoadActionPerformed
     loadTimetable();
    }//GEN-LAST:event_bntLoadActionPerformed

    private void btnDarkModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDarkModeActionPerformed
       ThemeManager.setDarkMode(!ThemeManager.isDarkMode());
        ThemeManager.applyTheme(this);
    }//GEN-LAST:event_btnDarkModeActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        new Timetable().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntLoad;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDarkMode;
    private javax.swing.JComboBox<String> cmbDept;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblTimetable;
    // End of variables declaration//GEN-END:variables
}
