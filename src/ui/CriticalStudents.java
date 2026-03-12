package ui;

import database.DBConnection;
import java.sql.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class CriticalStudents extends BaseFrame{
    
    private JTable table;
    private DefaultTableModel tableModel;
    
    public CriticalStudents(){
        
        super("Critical Students");
        initComponents();
        loadCriticalStudents();
        applyRiskColors();
        
    }
    
    public void initComponents(){
        
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(new Color(245, 247, 250));
        mainContent.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
        
        JLabel titleLabel = new JLabel("Critical Students");
        titleLabel.setFont(new Font("Segoe UI" , Font.BOLD , 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10,0,20,0));
        
        mainContent.add(titleLabel , BorderLayout.NORTH);
        
        String[] columns = {"Roll No", "Name", "Department", "Attendance", "Due Fees", "Fee Status", "Risk Level"};
        tableModel = new DefaultTableModel(columns , 0){
            public boolean isCellEditable(int row , int col){return false;}
        };
        
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI" , Font.PLAIN, 13));
        table.setRowHeight(35);
        table.getTableHeader().setFont(new Font("Segoe UI" , Font.BOLD , 13));
        table.getTableHeader().setBackground(new Color(37, 99, 235));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setGridColor(new Color(226, 232, 240));
        table.setShowGrid(true);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(new Color(226, 232, 240));
        tableCard.setBorder(BorderFactory.createEmptyBorder());
        tableCard.add(scrollPane , BorderLayout.CENTER);
        
        mainContent.add(tableCard , BorderLayout.CENTER);
        
        contentArea.add(mainContent , BorderLayout.CENTER);
        
    }
        void loadCriticalStudents() {
    try {
        Connection con = DBConnection.getConnection();
    tableModel.setRowCount(0);
        String sql = """
            SELECT s.roll_no, s.name, s.department, s.attendance,
                   f.due_fee, f.status
            FROM students s
            JOIN fees f ON s.roll_no = f.roll_no
            WHERE s.attendance < 75 OR f.status != 'PAID'
        """;

        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

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

            tableModel.addRow(new Object[]{ roll, name, dept, attendance, dueFee, feeStatus, risk}
            );
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

    for (int i = 0; i < table.getColumnCount(); i++) {
        table.getColumnModel().getColumn(i).setCellRenderer(renderer);
    }
}

    
}
