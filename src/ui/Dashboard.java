package ui;

import database.DBConnection;
import java.sql.*;
import java.awt.*;
import javax.swing.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.*;
import javax.swing.table.*;

public class Dashboard extends BaseFrame{
    
    private JLabel lblTotalStudents;
    private JLabel lblFeesCollected;
    private JLabel lblLowAttendance;
    private JLabel lblPendingFees;
    
    public Dashboard(){
        super("Dashboard");
        initComponent();
        loadStats();
    }
    public void initComponent(){
        
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(new Color(245, 247, 250));
        mainContent.setBorder(BorderFactory.createEmptyBorder(15, 20, 20, 20));
        
        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("Segoe UI" , Font.BOLD , 18));
        title.setBorder(BorderFactory.createEmptyBorder());
        
        mainContent.add(title , BorderLayout.NORTH);
        
        lblTotalStudents = new JLabel("0");
        lblFeesCollected = new JLabel("0");
        lblLowAttendance = new JLabel("0");
        lblPendingFees = new JLabel("0");

        JPanel cardsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        cardsPanel.setBackground(new Color(245, 247, 250));

        cardsPanel.add(makeCard("Total Students", lblTotalStudents, new Color(37, 99, 235), 
            () -> showTotalStudents()));
        cardsPanel.add(makeCard("Fees Collected", lblFeesCollected, new Color(22, 163, 74),  
            () -> showFeesCollected()));
        cardsPanel.add(makeCard("Low Attendance", lblLowAttendance, new Color(217, 119, 6),  
            () -> showLowAttendance()));
        cardsPanel.add(makeCard("Pending Fees", lblPendingFees, new Color(220, 38, 38),      
            () -> showPendingFees()));    
        cardsPanel.setPreferredSize(new Dimension(600, 250));
        
        mainContent.add(cardsPanel, BorderLayout.NORTH);
        contentArea.add(mainContent, BorderLayout.CENTER);
        
        ChartPanel chartPanel = createAttendanceChart();
        chartPanel.setPreferredSize(new Dimension(600, 200));
        mainContent.add(chartPanel, BorderLayout.SOUTH);
        
    }
        
        private JPanel makeCard(String title, JLabel numberLabel, Color color, Runnable onClick) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(color);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        numberLabel.setForeground(Color.WHITE);
        numberLabel.setFont(new Font("Segoe UI", Font.BOLD, 40));
        numberLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalGlue());
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(numberLabel);
        card.add(Box.createVerticalGlue());
        card.setCursor(new Cursor(Cursor.HAND_CURSOR)); // shows hand cursor on hover
        card.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent e) {
            onClick.run();
        }
    });
        return card;
    }
        private void loadStats() {
    try {
        Connection con = DBConnection.getConnection();

        ResultSet rs1 = con.prepareStatement("SELECT COUNT(*) FROM students").executeQuery();
        if (rs1.next()) lblTotalStudents.setText(String.valueOf(rs1.getInt(1)));

        ResultSet rs2 = con.prepareStatement("SELECT SUM(paid_fee) FROM fees").executeQuery();
        if (rs2.next()) lblFeesCollected.setText(String.valueOf((int)rs2.getDouble(1)));

        ResultSet rs3 = con.prepareStatement("SELECT COUNT(*) FROM students WHERE attendance < 75").executeQuery();
        if (rs3.next()) lblLowAttendance.setText(String.valueOf(rs3.getInt(1)));

        ResultSet rs4 = con.prepareStatement("SELECT COUNT(*) FROM fees WHERE status != 'PAID'").executeQuery();
        if (rs4.next()) lblPendingFees.setText(String.valueOf(rs4.getInt(1)));

        con.close();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
}
        private ChartPanel createAttendanceChart() {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    
    try {
        Connection con = DBConnection.getConnection();
        String sql = "SELECT department, AVG(attendance) FROM students GROUP BY department";
        ResultSet rs = con.prepareStatement(sql).executeQuery();
        
        while (rs.next()) {
            String dept = rs.getString(1);
            double avg = rs.getDouble(2);
            dataset.addValue(avg, "Attendance", dept);
        }
        con.close();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
    
    JFreeChart chart = ChartFactory.createBarChart(
        "Attendance by Department",
        "Department",
        "Average Attendance",
        dataset
    );
    
    return new ChartPanel(chart);
}
        private void showTotalStudents() {
    String[] columns = {"Roll No", "Name", "Department"};
    String sql = "SELECT roll_no, name, department FROM students";
    showPopup("All Students", columns, sql);
}
        private void showFeesCollected() {
    String[] columns = {"Roll No", "Total Fee", "Paid Fee", "Status"};
    String sql = "SELECT roll_no, total_fee, paid_fee, status FROM fees";
    showPopup("Fees Collected", columns, sql);
}
        private void showLowAttendance() {
    String[] columns = {"Roll No", "Name", "Attendance"};
    String sql = "SELECT roll_no, name, attendance FROM students WHERE attendance < 75";
    showPopup("Low Attendance", columns, sql);
}
        private void showPendingFees() {
    String[] columns = {"Roll No", "Name", "Due Fee", "Status"};
    String sql = "SELECT f.roll_no, s.name, f.due_fee, f.status FROM fees f JOIN students s ON f.roll_no = s.roll_no WHERE f.status != 'PAID'";
    showPopup("Pending Fees", columns, sql);
}
        private void showPopup(String title, String[] columns, String sql) {
    JDialog dialog = new JDialog(this, title, true);
    dialog.setSize(600, 400);
    dialog.setLocationRelativeTo(this);

    DefaultTableModel model = new DefaultTableModel(columns, 0) {
        public boolean isCellEditable(int row, int col) {return false;}
    };
    
    JTable table = new JTable(model);
    table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    table.setRowHeight(30);
    table.getTableHeader().setBackground(new Color(37, 99, 235));
    table.getTableHeader().setForeground(Color.WHITE);

    try {
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        int colCount = columns.length;
        
        while (rs.next()) {
            Object[] row = new Object[colCount];
            for (int i = 0; i < colCount; i++) {
                row[i] = rs.getObject(i + 1);
            }
            model.addRow(row);
        }
        con.close();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }

    dialog.add(new JScrollPane(table));
    dialog.setVisible(true);
}
}
    
