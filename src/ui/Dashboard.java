package ui;

import database.DBConnection;
import java.sql.*;
import java.awt.*;
import javax.swing.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.*;

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

        cardsPanel.add(makeCard("Total Students", lblTotalStudents, new Color(37, 99, 235)));   // blue
        cardsPanel.add(makeCard("Fees Collected", lblFeesCollected, new Color(22, 163, 74)));   // green
        cardsPanel.add(makeCard("Low Attendance", lblLowAttendance, new Color(217, 119, 6)));   // orange
        cardsPanel.add(makeCard("Pending Fees", lblPendingFees, new Color(220, 38, 38)));       // red
        cardsPanel.setPreferredSize(new Dimension(600, 250));
        
        mainContent.add(cardsPanel, BorderLayout.NORTH);
        contentArea.add(mainContent, BorderLayout.CENTER);
        
        ChartPanel chartPanel = createAttendanceChart();
        chartPanel.setPreferredSize(new Dimension(600, 200));
        mainContent.add(chartPanel, BorderLayout.SOUTH);
        
    }
        
        private JPanel makeCard(String title, JLabel numberLabel, Color color) {
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
}
    
