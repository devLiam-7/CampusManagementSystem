package ui;

import database.DBConnection;
import java.sql.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import utils.Session;

public class ViewStudents extends BaseFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtSearch;

    public ViewStudents() {
        super("View Students");
        initComponents();
        loadStudents("");
    }

    private void initComponents() {
  
        JPanel topBar = new JPanel(new BorderLayout(10, 0));
        topBar.setBackground(new Color(245, 247, 250));
        topBar.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        JLabel titleLabel = new JLabel("All Students");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(15, 23, 42));

        txtSearch = new JTextField();
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(203, 213, 225)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        txtSearch.setPreferredSize(new Dimension(200, 35));
        txtSearch.putClientProperty("JTextField.placeholderText", "Search...");
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                loadStudents(txtSearch.getText().trim());
            }
        });

        topBar.add(titleLabel, BorderLayout.WEST);
        topBar.add(txtSearch, BorderLayout.EAST);

        String[] columns = {"Roll No", "Name", "Department", "Attendance", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(35);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(37, 99, 235));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setGridColor(new Color(226, 232, 240));
        table.setShowGrid(true);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(new Color(245, 247, 250));
        mainContent.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
        tableCard.add(scrollPane, BorderLayout.CENTER);
       
        if (Session.role.equals("ADMIN")) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnEdit = new JButton("Edit Student");
        btnEdit.setBackground(new Color(37, 99, 235));
        btnEdit.setForeground(Color.WHITE);
        btnEdit.setFocusPainted(false);
        btnEdit.addActionListener(e -> editStudent());

        JButton btnDelete = new JButton("Delete Student");
        btnDelete.setBackground(new Color(220, 38, 38));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFocusPainted(false);
        btnDelete.addActionListener(e -> deleteStudent());

        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);

        tableCard.add(buttonPanel, BorderLayout.SOUTH);
    }
        
        mainContent.add(topBar, BorderLayout.NORTH);
        mainContent.add(tableCard, BorderLayout.CENTER);

        contentArea.add(mainContent, BorderLayout.CENTER);
    }

    private void loadStudents(String search) {
        tableModel.setRowCount(0);
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM students WHERE name LIKE ? OR roll_no LIKE ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "%" + search + "%");
            pst.setString(2, "%" + search + "%");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int roll = rs.getInt("roll_no");
                String name = rs.getString("name");
                String dept = rs.getString("department");
                double attendance = rs.getDouble("attendance");
                String status = attendance >= 75 ? "Allowed" : "Not Allowed";
                tableModel.addRow(new Object[]{roll, name, dept, attendance, status});
            }
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    private void deleteStudent() {
    int selectedRow = table.getSelectedRow();
    
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a student first!");
        return;
    }
    
    int roll = (int) tableModel.getValueAt(selectedRow, 0);
    String name = tableModel.getValueAt(selectedRow, 1).toString();
    
    int confirm = JOptionPane.showConfirmDialog(this,
        "Are you sure you want to delete " + name + "?",
        "Confirm Delete",
        JOptionPane.YES_NO_OPTION);
    
    if (confirm != JOptionPane.YES_OPTION) return;
    
    try {
        Connection con = DBConnection.getConnection();

        String sql1 = "DELETE FROM fees WHERE roll_no = ?";
        PreparedStatement pst1 = con.prepareStatement(sql1);
        pst1.setInt(1, roll);
        pst1.executeUpdate();

        String sql2 = "DELETE FROM students WHERE roll_no = ?";
        PreparedStatement pst2 = con.prepareStatement(sql2);
        pst2.setInt(1, roll);
        pst2.executeUpdate();
        con.close();
        
        JOptionPane.showMessageDialog(this, "Student deleted successfully!");
        loadStudents("");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
}
private void editStudent() {
    int selectedRow = table.getSelectedRow();

    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a student first!");
        return;
    }

    int roll = (int) tableModel.getValueAt(selectedRow, 0);
    String name = tableModel.getValueAt(selectedRow, 1).toString();
    String dept = tableModel.getValueAt(selectedRow, 2).toString();
    String attendance = tableModel.getValueAt(selectedRow, 3).toString();

    JTextField txtName = new JTextField(name);
    JTextField txtDept = new JTextField(dept);
    JTextField txtAttendance = new JTextField(attendance);

    Object[] fields = {
        "Name:", txtName,
        "Department:", txtDept,
        "Attendance:", txtAttendance
    };

    int result = JOptionPane.showConfirmDialog(this, fields, 
        "Edit Student - Roll No: " + roll, JOptionPane.OK_CANCEL_OPTION);

    if (result != JOptionPane.OK_OPTION) return;

    try {
        Connection con = DBConnection.getConnection();
        String sql = "UPDATE students SET name=?, department=?, attendance=? WHERE roll_no=?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, txtName.getText().trim());
        pst.setString(2, txtDept.getText().trim());
        pst.setDouble(3, Double.parseDouble(txtAttendance.getText().trim()));
        pst.setInt(4, roll);
        pst.executeUpdate();
        con.close();

        JOptionPane.showMessageDialog(this, "Student updated successfully!");
        loadStudents("");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
}
}