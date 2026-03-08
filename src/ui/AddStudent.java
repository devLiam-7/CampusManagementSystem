package ui;
import database.DBConnection;


import java.sql.*;
import java.awt.*;
import javax.swing.*;

public class AddStudent extends JFrame {

    // Step 1 — declare fields at class level
    private JTextField txtRoll;
    private JTextField txtName;
    private JTextField txtDept;
    private JTextField txtAttendance;

    public AddStudent() {
        setTitle("Add Student");
        setSize(400, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Step 2 — initialize fields
        txtRoll       = new JTextField();
        txtName       = new JTextField();
        txtDept       = new JTextField();
        txtAttendance = new JTextField();

        JPanel mainPanel = new JPanel(new BorderLayout());

        // ── HEADER
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(new Color(37, 99, 235));
        topPanel.setPreferredSize(new Dimension(400, 80));
        JLabel titleLabel = new JLabel("Add Student");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel);

        // ── FORM PANEL
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        // add rows to form
        formPanel.add(makeRow("Roll No :", txtRoll));
        formPanel.add(Box.createVerticalStrut(12));
        formPanel.add(makeRow("Name :", txtName));
        formPanel.add(Box.createVerticalStrut(12));
        formPanel.add(makeRow("Department :", txtDept));
        formPanel.add(Box.createVerticalStrut(12));
        formPanel.add(makeRow("Attendance :", txtAttendance));
        formPanel.add(Box.createVerticalStrut(20));

        // ── SAVE BUTTON
        JButton btnSave = new JButton("Save Student");
        btnSave.setBackground(new Color(37, 99, 235));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFocusPainted(false);
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSave.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btnSave.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnSave.addActionListener(e -> btnSaveActionPerformed(e));
        formPanel.add(btnSave);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
        setVisible(true);
    }

    // Step 3 — helper method to create a label + field row
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

    // Check empty fields
    if (rollText.isEmpty() || name.isEmpty() || dept.isEmpty() || attendanceText.isEmpty()) {
        JOptionPane.showMessageDialog(this, "All fields are required.");
        return;
    }

    // Validate Roll No is a number
    int roll;
    try {
        roll = Integer.parseInt(rollText);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Roll No must be a number.");
        return;
    }

    // Validate Attendance is a number between 0-100
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