package ui;

import utils.ThemeManager;
import database.DBConnection;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ViewStudentsFrame extends javax.swing.JFrame {
    
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ViewStudentsFrame.class.getName());

    public ViewStudentsFrame() {
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
        loadStudents();
    }
    
    void loadStudents() {
    try {
        Connection con = DBConnection.getConnection();
        String sql = "SELECT * FROM students";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

       DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // clear table

        while (rs.next()) {
            int roll = rs.getInt("roll_no");
            String name = rs.getString("name");
            String dept = rs.getString("department");
            double attendance = rs.getDouble("attendance");

            String status = attendance >= 75 ? "Allowed" : "Not Allowed";

            model.addRow(new Object[]{
                roll, name, dept, attendance, status
            });
        }

        con.close();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
}
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnDarkMode = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Roll No", "Name", "Department", "Attendance", "Status"
            }
        ));
        jTable1.setName("tblStudents"); // NOI18N
        jScrollPane1.setViewportView(jTable1);

        btnDarkMode.setText("Dark Mode");
        btnDarkMode.addActionListener(this::btnDarkModeActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnDarkMode))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(btnDarkMode)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDarkModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDarkModeActionPerformed
    ThemeManager.setDarkMode(!ThemeManager.isDarkMode());
    ThemeManager.applyTheme(this);
    }//GEN-LAST:event_btnDarkModeActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDarkMode;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
