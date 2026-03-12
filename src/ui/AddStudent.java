package ui;
import database.DBConnection;


import java.sql.*;
import java.awt.*;
import javax.swing.*;

public class AddStudent extends BaseFrame {

    private JTextField txtRoll;
    private JTextField txtName;
    private JTextField txtDept;
    private JTextField txtAttendance;

    public AddStudent() {
       super("Add Student");

         txtRoll       = new JTextField();
         txtName       = new JTextField();
         txtDept       = new JTextField();
         txtAttendance = new JTextField();

       
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
       
        
        JLabel titleLabel = new JLabel("Add Student");
        titleLabel.setFont(new Font("Segoe UI" , Font.BOLD , 18));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(titleLabel);

        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(makeRow("Roll No :", txtRoll));
        formPanel.add(Box.createVerticalStrut(12));
        formPanel.add(makeRow("Name :", txtName));
        formPanel.add(Box.createVerticalStrut(12));
        formPanel.add(makeRow("Department :", txtDept));
        formPanel.add(Box.createVerticalStrut(12));
        formPanel.add(makeRow("Attendance :", txtAttendance));
        formPanel.add(Box.createVerticalStrut(20));

        JButton btnSave = new JButton("Save Student");
        btnSave.setBackground(new Color(37, 99, 235));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFocusPainted(false);
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSave.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btnSave.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnSave.addActionListener(e -> btnSaveActionPerformed(e));
        formPanel.add(btnSave);
        
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(new Color(245, 247, 250));
        wrapper.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 200));
        wrapper.add(formPanel, BorderLayout.CENTER);
        contentArea.add(wrapper, BorderLayout.CENTER);

    }

    private JPanel makeRow(String labelText, JTextField field) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(Color.WHITE);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setPreferredSize(new Dimension(100, 35));

        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(203, 213, 225)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        row.add(label, BorderLayout.WEST);
        row.add(field, BorderLayout.CENTER);
        return row;
    }
    
    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {

    String rollText = txtRoll.getText().trim();
    String name = txtName.getText().trim();
    String dept = txtDept.getText().trim();
    String attendanceText = txtAttendance.getText().trim();

    if (rollText.isEmpty() || name.isEmpty() || dept.isEmpty() || attendanceText.isEmpty()) {
        JOptionPane.showMessageDialog(this, "All fields are required.");
        return;
    }

    int roll;
    try {
        roll = Integer.parseInt(rollText);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Roll No must be a number.");
        return;
    }

    double attendance;
    try {
        attendance = Double.parseDouble(attendanceText);
        if (attendance < 0 || attendance > 100) {
            JOptionPane.showMessageDialog(this, "Attendance must be between 0 and 100.");
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Attendance must be a number.");
        return;
    }

    try {
        Connection con = DBConnection.getConnection();
        String sql = "INSERT INTO students (roll_no, name, department, attendance) VALUES (?,?,?,?)";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, roll);
        pst.setString(2, name);
        pst.setString(3, dept);
        pst.setDouble(4, attendance);
        pst.executeUpdate();
        con.close();

        JOptionPane.showMessageDialog(this, "Student Added Successfully!");
        txtRoll.setText("");
        txtName.setText("");
        txtDept.setText("");
        txtAttendance.setText("");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
}
    }

    public static void main(String[] args) {
        new AddStudent();
    }
}