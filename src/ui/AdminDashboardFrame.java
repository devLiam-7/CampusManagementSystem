package ui;

import utils.ThemeManager;
import database.DBConnection;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class AdminDashboardFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AdminDashboardFrame.class.getName());
    
    public AdminDashboardFrame() {
        initComponents();
        for (java.awt.Component c : getContentPane().getComponents()) {
        if (c instanceof javax.swing.JButton b) {
        b.setFocusPainted(false);
        b.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
    }
}
        setLocationRelativeTo(null);
        ThemeManager.applyTheme(this);
        
        setSize(1000, 650);
        setLocationRelativeTo(null);
        pnlChart.setPreferredSize(new java.awt.Dimension(800, 300));
        loadDashboardData();
        loadCriticalStudentDetails();
        showCriticalAlertIfNeeded();
        loadAttendanceChart();
    }

    void loadDashboardData() {
    try {
        Connection con = DBConnection.getConnection();
        Statement st = con.createStatement();

        ResultSet rs;

        rs = st.executeQuery("SELECT COUNT(*) FROM students");
        rs.next();
        lblTotal.setText("Total Students: " + rs.getInt(1));

        rs = st.executeQuery("SELECT COUNT(*) FROM students WHERE attendance >= 75");
        rs.next();
        lblAllowed.setText("Allowed: " + rs.getInt(1));

        rs = st.executeQuery("SELECT COUNT(*) FROM students WHERE attendance < 75");
        rs.next();
        lblNotAllowed.setText("Not Allowed: " + rs.getInt(1));

        rs = st.executeQuery("SELECT COUNT(*) FROM fees WHERE status != 'PAID'");
        rs.next();
        lblDefaulters.setText("Fee Defaulters: " + rs.getInt(1));

        rs = st.executeQuery("""
            SELECT COUNT(*)
            FROM students s
            JOIN fees f ON s.roll_no = f.roll_no
            WHERE s.attendance < 75 AND f.due_fee > 0
        """);
        rs.next();
        lblCritical.setText("Critical Students: " + rs.getInt(1));

        con.close();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
}
    
    void loadCriticalStudentDetails() {
    try {
        Connection con = DBConnection.getConnection();

        String sql = """
            SELECT s.roll_no, s.name, s.attendance, f.due_fee
            FROM students s
            JOIN fees f ON s.roll_no = f.roll_no
            WHERE s.attendance < 75 AND f.due_fee > 0
        """;

        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        StringBuilder sb = new StringBuilder();

        while (rs.next()) {
              sb.append("• ")
              .append(rs.getInt("roll_no"))
              .append(" | Name: ")
              .append(rs.getString("name"))
              .append(" | Attendance: ")
              .append(rs.getDouble("attendance"))
              .append(" | Due: ")
              .append(rs.getDouble("due_fee"))
              .append("\n");
        }

        if (sb.length() == 0) {
            sb.append("No critical students 🎉");
        }

        txtCriticalList.setText(sb.toString());

        con.close();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
}
    
    void showCriticalAlertIfNeeded() {
    try {
        Connection con = DBConnection.getConnection();

        String sql = """
            SELECT COUNT(*)
            FROM students s
            JOIN fees f ON s.roll_no = f.roll_no
            WHERE s.attendance < 75 AND f.due_fee > 0
        """;

        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        if (rs.next() && rs.getInt(1) > 0) {
            JOptionPane.showMessageDialog(
                this,
                "⚠ Critical students detected!\nPlease review immediately.",
                "Warning",
                JOptionPane.WARNING_MESSAGE
            );
        }

        con.close();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
}
    
void loadAttendanceChart() {
    try {
        Connection con = DBConnection.getConnection();
        Statement st = con.createStatement();

        ResultSet rs1 = st.executeQuery(
            "SELECT COUNT(*) FROM students WHERE attendance >= 75"
        );
        rs1.next();
        int allowed = rs1.getInt(1);

        ResultSet rs2 = st.executeQuery(
            "SELECT COUNT(*) FROM students WHERE attendance < 75"
        );
        rs2.next();
        int notAllowed = rs2.getInt(1);

        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Allowed", allowed);
        dataset.setValue("Not Allowed", notAllowed);

        JFreeChart chart = ChartFactory.createPieChart(
            "Attendance Status",
            dataset,
            true,
            true,
            false
        );

        ChartPanel cp = new ChartPanel(chart);

        pnlChart.removeAll();
        pnlChart.setLayout(new BorderLayout());
        pnlChart.add(cp, BorderLayout.CENTER);
        pnlChart.validate();

        con.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
}
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTotal = new javax.swing.JLabel();
        lblAllowed = new javax.swing.JLabel();
        lblNotAllowed = new javax.swing.JLabel();
        lblDefaulters = new javax.swing.JLabel();
        lblCritical = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtCriticalList = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        pnlChart = new javax.swing.JPanel();
        btnDarkMode = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTotal.setText("Total Students :");

        lblAllowed.setText("Allowed Students :");

        lblNotAllowed.setText("Not Allowed :");

        lblDefaulters.setText("Fee Defaulter :");

        lblCritical.setText("Critical Students :");

        txtCriticalList.setEditable(false);
        txtCriticalList.setColumns(6);
        txtCriticalList.setRows(5);
        txtCriticalList.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        txtCriticalList.setFocusable(false);
        jScrollPane1.setViewportView(txtCriticalList);

        jLabel1.setText("Critical Students (Detail) : ");

        javax.swing.GroupLayout pnlChartLayout = new javax.swing.GroupLayout(pnlChart);
        pnlChart.setLayout(pnlChartLayout);
        pnlChartLayout.setHorizontalGroup(
            pnlChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );
        pnlChartLayout.setVerticalGroup(
            pnlChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        btnDarkMode.setText("Dark Mode");
        btnDarkMode.addActionListener(this::btnDarkModeActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlChart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(lblTotal)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDarkMode))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblAllowed)
                                .addComponent(lblCritical)
                                .addComponent(lblDefaulters)
                                .addComponent(lblNotAllowed))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(65, 65, 65)
                                    .addComponent(jLabel1))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(18, 18, 18)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(lblTotal))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnDarkMode)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblAllowed)
                        .addGap(18, 18, 18)
                        .addComponent(lblNotAllowed)
                        .addGap(18, 18, 18)
                        .addComponent(lblDefaulters, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(lblCritical))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(pnlChart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDarkModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDarkModeActionPerformed
    ThemeManager.setDarkMode(!ThemeManager.isDarkMode());
    ThemeManager.applyTheme(this);
    
    }//GEN-LAST:event_btnDarkModeActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDarkMode;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAllowed;
    private javax.swing.JLabel lblCritical;
    private javax.swing.JLabel lblDefaulters;
    private javax.swing.JLabel lblNotAllowed;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JPanel pnlChart;
    private javax.swing.JTextArea txtCriticalList;
    // End of variables declaration//GEN-END:variables
}
