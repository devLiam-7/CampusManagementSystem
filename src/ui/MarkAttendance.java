package ui;

import database.DBConnection;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import utils.Session;

public class MarkAttendance extends BaseFrame{
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnPresent;
    private JButton btnAbsent;
    private JButton btnSaveAll;
    private JLabel dateLabel;
     
    public MarkAttendance(){    
    super("Mark Attendance");
    initComponents();
    loadStudents();
    }
    private void initComponents(){
        JPanel topBar = new JPanel(new BorderLayout(10,0));
        topBar.setBackground(new Color(245, 247, 250));
        topBar.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));
        
        JLabel titleLabel = new JLabel("Attendance");
        titleLabel.setFont(new Font("Segoe UI" , Font.BOLD, 18));
        titleLabel.setForeground(new Color(15, 23, 42));
        
        topBar.add(titleLabel, BorderLayout.WEST);
        
        JComboBox<String> deptBox = new JComboBox<>();;
        deptBox.addItem("All");
        try {
            Connection con = DBConnection.getConnection();
            ResultSet rs = con.prepareStatement("SELECT DISTINCT department FROM students").executeQuery();
            while(rs.next()) deptBox.addItem(rs.getString("department"));
            con.close();
        }catch (Exception e) {e.printStackTrace(); }
        
        deptBox.addActionListener(e -> {
            Session.selectedDepartment = (String) deptBox.getSelectedItem();
            loadStudents();
        });
        
        topBar.add(deptBox, BorderLayout.EAST);
        
        String[] columns = {"Roll No", "Name", "Status"};
        tableModel = new DefaultTableModel(columns, 0){
        public boolean isCellEditable(int row, int col) {return false;}
        };
        
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN , 13));
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
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
        tableCard.add(scrollPane, BorderLayout.CENTER);
        
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(new Color(245, 247, 250));
        mainContent.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        
           btnPresent = new JButton("Present");
           btnPresent.setBackground(new Color(217, 119, 6));
           btnPresent.setForeground(Color.WHITE);
           btnPresent.setFocusPainted(false);
           btnPresent.setMaximumSize(new Dimension(Integer.MAX_VALUE,40));
           btnPresent.setAlignmentX(Component.LEFT_ALIGNMENT);
           btnPresent.addActionListener(e -> markStatus("Present"));
           
           btnAbsent = new JButton("Absent");
           btnAbsent.setBackground(new Color(217, 119, 6));
           btnAbsent.setForeground(Color.WHITE);
           btnAbsent.setFocusPainted(false);
           btnAbsent.setMaximumSize(new Dimension(Integer.MAX_VALUE,40));
           btnAbsent.setAlignmentX(Component.CENTER_ALIGNMENT);
           btnAbsent.addActionListener(e -> markStatus("Absent"));
           
           btnSaveAll = new JButton("Save All");
           btnSaveAll.setBackground(new Color(217, 119, 6));
           btnSaveAll.setForeground(Color.WHITE);
           btnSaveAll.setFocusPainted(false);
           btnSaveAll.setMaximumSize(new Dimension(Integer.MAX_VALUE,40));
           btnSaveAll.setAlignmentX(Component.RIGHT_ALIGNMENT);
           btnSaveAll.addActionListener(e -> saveAttendance());
           
           JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
           buttonPanel.add(btnPresent);
           buttonPanel.add(btnAbsent);
           buttonPanel.add(btnSaveAll);
       
        tableCard.add(buttonPanel, BorderLayout.SOUTH);
        mainContent.add(topBar , BorderLayout.NORTH);
        mainContent.add(tableCard , BorderLayout.CENTER);
        
        contentArea.add(mainContent, BorderLayout.CENTER);
        
  }
    
    private void loadStudents() {
    tableModel.setRowCount(0);
    try {
        Connection con = DBConnection.getConnection();
        
        String dept = Session.selectedDepartment;
        String sql;
        
        if (dept.equals("All")) {
            sql = "SELECT s.roll_no, s.name, a.status " +
                  "FROM students s LEFT JOIN attendance a " +
                  "ON s.roll_no = a.roll_no AND a.date = CURDATE()";
        } else {
            sql = "SELECT s.roll_no, s.name, a.status " +
                  "FROM students s LEFT JOIN attendance a " +
                  "ON s.roll_no = a.roll_no AND a.date = CURDATE() " +
                  "WHERE s.department = '" + dept + "'";
        }
        
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        
        while (rs.next()) {
            int roll = rs.getInt("roll_no");
            String name = rs.getString("name");
            String status = rs.getString("status");
            if (status == null) status = "";
            tableModel.addRow(new Object[]{roll, name, status});
        }
        con.close();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
}
    
    private void markStatus(String status) {
    int selectedRow = table.getSelectedRow();
    
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a student first!");
        return;
    }
    
    tableModel.setValueAt(status, selectedRow, 2);
}
    
    private void saveAttendance() {
    try {
        Connection con = DBConnection.getConnection();
        String sql = "INSERT INTO attendance (roll_no, date, status) VALUES (?, CURDATE(), ?)";
        PreparedStatement pst = con.prepareStatement(sql);
        
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            int roll = (int) tableModel.getValueAt(i, 0);
            String status = (String) tableModel.getValueAt(i, 2);
            
            if (status == null || status.isEmpty()) continue; // skip unmarked
            
            pst.setInt(1, roll);
            pst.setString(2, status);
            pst.executeUpdate();
        }
        
        con.close();
        JOptionPane.showMessageDialog(this, "Attendance saved successfully!");
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
}
  }

