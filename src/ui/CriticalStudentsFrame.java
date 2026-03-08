package ui;

import utils.ThemeManager;
import database.DBConnection;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;

public class CriticalStudentsFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CriticalStudentsFrame.class.getName());

    public CriticalStudentsFrame() {
        initComponents();
        for (java.awt.Component c : getContentPane().getComponents()) {
        if (c instanceof javax.swing.JButton b) {
        b.setFocusPainted(false);
        b.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
    }
}
        setLocationRelativeTo(null);
         setSize(500, 350);
        loadCriticalStudents();
        applyRiskColors();
        ThemeManager.applyTheme(this);
    }

    void loadCriticalStudents() {
    try {
        Connection con = DBConnection.getConnection();

        String sql = """
            SELECT s.roll_no, s.name, s.department, s.attendance,
                   f.due_fee, f.status
            FROM students s
            JOIN fees f ON s.roll_no = f.roll_no
            WHERE s.attendance < 75 OR f.status != 'PAID'
        """;

        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        DefaultTableModel model =
            (DefaultTableModel) tblCritical.getModel();
        model.setRowCount(0);

        while (rs.next()) {

            int roll = rs.getInt("roll_no");
            String name = rs.getString("name");
            String dept = rs.getString("department");
            double attendance = rs.getDouble("attendance");
            double dueFee = rs.getDouble("due_fee");
            String feeStatus = rs.getString("status");

            String risk;
            if (attendance < 75 && dueFee > 0) {
                risk = "CRITICAL";
            } else if (attendance < 75) {
                risk = "ATTENDANCE RISK";
            } else {
                risk = "FEE RISK";
            }

            model.addRow(new Object[]{
                roll, name, dept, attendance, dueFee, feeStatus, risk
            });
        }

        con.close();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
}
    void applyRiskColors() {
    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {

        @Override
        public java.awt.Component getTableCellRendererComponent(
                javax.swing.JTable table, Object value,
                boolean isSelected, boolean hasFocus,
                int row, int column) {

            java.awt.Component c = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);

            String risk = table.getValueAt(row, 6).toString();

            if (risk.equals("CRITICAL")) {
                c.setBackground(new Color(255, 180, 180));
            } else if (risk.contains("RISK")) {
                c.setBackground(new Color(255, 255, 180));
            } else {
                c.setBackground(new Color(180, 255, 180));
            }

            return c;
        }
    };

    for (int i = 0; i < tblCritical.getColumnCount(); i++) {
        tblCritical.getColumnModel().getColumn(i).setCellRenderer(renderer);
    }
}
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblCritical = new javax.swing.JTable();
        btnDarkMode = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CriticalS tudents");

        tblCritical.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Roll no", "Name", "Deapartment", "Attendance", "Due Fees", "Fee Status", "Risk Level"
            }
        ));
        jScrollPane1.setViewportView(tblCritical);

        btnDarkMode.setText("Dark Mode");
        btnDarkMode.addActionListener(this::btnDarkModeActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
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
    private javax.swing.JTable tblCritical;
    // End of variables declaration//GEN-END:variables
}
