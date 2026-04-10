package ui;

import database.DBConnection;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;

public class ViewAttendance extends BaseFrame{
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField date;
    private JButton view;
    
    public ViewAttendance(){
    super("View Attendance");
    initComponents();
    loadStudents(java.time.LocalDate.now().toString()); 
    }
    private void initComponents(){
        JPanel topBar = new JPanel(new BorderLayout(10,0));
        topBar.setBackground(new Color(245, 247, 250));
        topBar.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));
        
        JLabel titleLabel = new JLabel("View Attendance");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(15, 23, 42));
        
        topBar.add(titleLabel, BorderLayout.WEST);
        
        date = new JTextField();
        date.setFont(new Font("Segoe UI", Font.BOLD, 13));
        date.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        date.setPreferredSize(new Dimension(200, 35));
        date.putClientProperty("JTextField.placeholderText", "YYYY-MM-DD");
        date.setText(java.time.LocalDate.now().toString());
        
        view = new JButton("View");
        view.setBackground(new Color(217, 119, 6));
        view.setForeground(Color.WHITE);
        view.setFocusPainted(false);
        view.setMaximumSize(new Dimension(Integer.MAX_VALUE,40));
        view.setAlignmentX(Component.LEFT_ALIGNMENT);
        view.addActionListener(e -> loadStudents(date.getText().trim()));
        
            topBar.add(date, BorderLayout.CENTER);
        topBar.add(view, BorderLayout.EAST);
        
        String[] columns = {"Roll no", "Name", "Date", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) {return false;}
        };
        
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(35);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.PLAIN, 15));
        table.getTableHeader().setBackground(new Color(37, 99, 235));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setGridColor(new Color(226, 232, 240));
        table.setShowGrid(true);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(new Color(239, 233, 244));
        tableCard.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
        
        tableCard.add(scrollPane, BorderLayout.CENTER);
        
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(new Color(245, 247, 250));
        mainContent.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        
        mainContent.add(topBar, BorderLayout.NORTH);
        mainContent.add(tableCard, BorderLayout.CENTER);
        
        contentArea.add(mainContent, BorderLayout.CENTER);
    }
    private void loadStudents(String search) {
        tableModel.setRowCount(0);
 
    if (search == null || search.isEmpty()) return;
    
        try {
            Connection con = DBConnection.getConnection();
             String sql = "SELECT s.roll_no, s.name, a.date, a.status " +
             "FROM attendance a JOIN students s ON a.roll_no = s.roll_no " +
             "WHERE a.date = ?";
             PreparedStatement pst = con.prepareStatement(sql);
             pst.setString(1, search);
             ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int roll = rs.getInt("roll_no");
                String name = rs.getString("name");
                String Date = rs.getString("date");
                String status = rs.getString("status");
                tableModel.addRow(new Object[]{roll, name, Date, status});
            }
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}
