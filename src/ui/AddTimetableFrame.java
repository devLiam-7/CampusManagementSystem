package ui;

import utils.ThemeManager;
import database.DBConnection;
import java.sql.*;
import javax.swing.JOptionPane;

public class AddTimetableFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AddTimetableFrame.class.getName());

    public AddTimetableFrame() {
        initComponents();
        for (java.awt.Component c : getContentPane().getComponents()) {
        if (c instanceof javax.swing.JButton b) {
        b.setFocusPainted(false);
        b.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
    }
}
        setLocationRelativeTo(null);
        ThemeManager.applyTheme(this);
        
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmbDay = new javax.swing.JComboBox<>();
        JLableTime = new javax.swing.JLabel();
        jLabelSubject = new javax.swing.JLabel();
        JLabelFaculty = new javax.swing.JLabel();
        JLabelDept = new javax.swing.JLabel();
        bntSave = new javax.swing.JButton();
        txtTime = new javax.swing.JTextField();
        txtSubject = new javax.swing.JTextField();
        txtFaculty = new javax.swing.JTextField();
        txtDept = new javax.swing.JTextField();
        btnDarkMode = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        cmbDay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" }));
        cmbDay.addActionListener(this::cmbDayActionPerformed);

        JLableTime.setText("Time Slot  : ");

        jLabelSubject.setText("Subject     :");

        JLabelFaculty.setText("Faculty     :");

        JLabelDept.setText("Department  :");

        bntSave.setText("Save TimeTable");
        bntSave.addActionListener(this::bntSaveActionPerformed);

        btnDarkMode.setText("Dark Mode");
        btnDarkMode.addActionListener(this::btnDarkModeActionPerformed);

        btnBack.setText("Back");
        btnBack.addActionListener(this::btnBackActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(JLabelDept)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDept, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabelSubject)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtSubject, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(bntSave)
                                .addComponent(cmbDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(JLableTime)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(JLabelFaculty)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtFaculty, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 139, Short.MAX_VALUE)
                        .addComponent(btnBack))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnDarkMode)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(btnDarkMode)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBack))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JLableTime)
                    .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelSubject)
                    .addComponent(txtSubject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JLabelFaculty)
                    .addComponent(txtFaculty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JLabelDept)
                    .addComponent(txtDept, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addComponent(bntSave)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDayActionPerformed
        
        
    }//GEN-LAST:event_cmbDayActionPerformed

    private void bntSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntSaveActionPerformed
   
    if (txtTime.getText().isEmpty() ||
    txtSubject.getText().isEmpty() ||
    txtFaculty.getText().isEmpty() ||
    txtDept.getText().isEmpty()) {

    JOptionPane.showMessageDialog(this, "All fields are required");
    return;
}

    String day = cmbDay.getSelectedItem().toString();
    String time = txtTime.getText();                  
    String subject = txtSubject.getText();
    String faculty = txtFaculty.getText();
    String dept = txtDept.getText();

        try {
             Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO timetable(day, time_slot, subject, faculty, department) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, day);
            pst.setString(2, time);
            pst.setString(3, subject);
            pst.setString(4, faculty);
            pst.setString(5, dept);

            pst.executeUpdate();

    JOptionPane.showMessageDialog(this, "Timetable saved successfully");

    con.close();

} catch (Exception e) {
    JOptionPane.showMessageDialog(this, e.getMessage());
}
    }//GEN-LAST:event_bntSaveActionPerformed

    private void btnDarkModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDarkModeActionPerformed
    ThemeManager.setDarkMode(!ThemeManager.isDarkMode());
    ThemeManager.applyTheme(this);
    }//GEN-LAST:event_btnDarkModeActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
       new Timetable().setVisible(true);
    }//GEN-LAST:event_btnBackActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel JLabelDept;
    private javax.swing.JLabel JLabelFaculty;
    private javax.swing.JLabel JLableTime;
    private javax.swing.JButton bntSave;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDarkMode;
    private javax.swing.JComboBox<String> cmbDay;
    private javax.swing.JLabel jLabelSubject;
    private javax.swing.JTextField txtDept;
    private javax.swing.JTextField txtFaculty;
    private javax.swing.JTextField txtSubject;
    private javax.swing.JTextField txtTime;
    // End of variables declaration//GEN-END:variables
}
