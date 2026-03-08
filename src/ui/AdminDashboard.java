package ui;


import utils.ThemeManager;
import ui.AddStudentFrame;

public class AdminDashboard extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AdminDashboard.class.getName());

    public AdminDashboard() {
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

    public AdminDashboard(String role) {
    initComponents();
    setLocationRelativeTo(null);
   

    if (!role.equals("ADMIN")) {
       bntAddStudent.setEnabled(false);
   }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bntAddStudent = new javax.swing.JButton();
        bntViewStudent = new javax.swing.JButton();
        btnUpdateAttendance = new javax.swing.JButton();
        btnDarkMode = new javax.swing.JButton();
        btnFees = new javax.swing.JButton();
        btnTimeTable = new javax.swing.JButton();
        btnDashboard = new javax.swing.JButton();
        btnCritical = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ADMIN PANEL");

        bntAddStudent.setText("Add Student");
        bntAddStudent.addActionListener(this::bntAddStudentActionPerformed);

        bntViewStudent.setText("View Student");
        bntViewStudent.addActionListener(this::bntViewStudentActionPerformed);

        btnUpdateAttendance.setText("Update Attendance");
        btnUpdateAttendance.addActionListener(this::btnUpdateAttendanceActionPerformed);

        btnDarkMode.setText("Dark Mode");
        btnDarkMode.addActionListener(this::btnDarkModeActionPerformed);

        btnFees.setText("Fee Management");
        btnFees.addActionListener(this::btnFeesActionPerformed);

        btnTimeTable.setText("TimeTable");
        btnTimeTable.addActionListener(this::btnTimeTableActionPerformed);

        btnDashboard.setText("Admin Dashboard");
        btnDashboard.addActionListener(this::btnDashboardActionPerformed);

        btnCritical.setText("Critical Students");
        btnCritical.addActionListener(this::btnCriticalActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnDarkMode))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(bntAddStudent)
                                    .addComponent(bntViewStudent)
                                    .addComponent(btnUpdateAttendance))
                                .addGap(0, 256, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnTimeTable)
                            .addComponent(btnFees)
                            .addComponent(btnDashboard)
                            .addComponent(btnCritical))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(btnDarkMode)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bntAddStudent)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bntViewStudent)
                .addGap(12, 12, 12)
                .addComponent(btnUpdateAttendance)
                .addGap(12, 12, 12)
                .addComponent(btnFees)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTimeTable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCritical)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDashboard)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bntAddStudentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntAddStudentActionPerformed
        System.out.println("Button clicked");
        new AddStudentFrame().setVisible(true);
    }//GEN-LAST:event_bntAddStudentActionPerformed

    private void bntViewStudentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntViewStudentActionPerformed
    new ViewStudentsFrame().setVisible(true);
    }//GEN-LAST:event_bntViewStudentActionPerformed

    private void btnUpdateAttendanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateAttendanceActionPerformed
        new UpdateAttendanceFrame().setVisible(true);
    }//GEN-LAST:event_btnUpdateAttendanceActionPerformed

    private void btnDarkModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDarkModeActionPerformed
    ThemeManager.setDarkMode(!ThemeManager.isDarkMode());
    ThemeManager.applyTheme(this);
    }//GEN-LAST:event_btnDarkModeActionPerformed

    private void btnFeesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFeesActionPerformed
        new FeeManagementFrame().setVisible(true);
    }//GEN-LAST:event_btnFeesActionPerformed

    private void btnTimeTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimeTableActionPerformed
        new Timetable().setVisible(true);
    }//GEN-LAST:event_btnTimeTableActionPerformed

    private void btnDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDashboardActionPerformed
       new AdminDashboardFrame().setVisible(true);
    }//GEN-LAST:event_btnDashboardActionPerformed

    private void btnCriticalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCriticalActionPerformed
       new CriticalStudentsFrame().setVisible(true);
    }//GEN-LAST:event_btnCriticalActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntAddStudent;
    private javax.swing.JButton bntViewStudent;
    private javax.swing.JButton btnCritical;
    private javax.swing.JButton btnDarkMode;
    private javax.swing.JButton btnDashboard;
    private javax.swing.JButton btnFees;
    private javax.swing.JButton btnTimeTable;
    private javax.swing.JButton btnUpdateAttendance;
    // End of variables declaration//GEN-END:variables
}
