package ui;

import database.DBConnection;
import java.sql.*;
import java.awt.*;
import javax.swing.*;

public class AddMarks extends BaseFrame{
    
    private JTextField txtRoll;
    private JComboBox<String> txtSub;
    private JTextField txtMark;
    private JTextField txtMax;
    
    public AddMarks(){
        super("Add Marks");
        
        txtRoll     = new JTextField();
        txtSub      = new JComboBox<>(new String[]{"Math", "Science", "English", "History", "Geography", "Computer"});
        txtSub.setBackground(Color.WHITE);
        txtSub.setForeground(new Color(15, 23, 42));
        txtSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtMark     = new JTextField();
        txtMax      = new JTextField();
        
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
       
        
        JLabel titleLabel = new JLabel("Add Marks");
        titleLabel.setFont(new Font("Segoe UI" , Font.BOLD , 18));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(titleLabel);
        
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(makeRow("Roll No :", txtRoll));
        formPanel.add(Box.createVerticalStrut(12));
        formPanel.add(makeRow("Subject :", txtSub));
        formPanel.add(Box.createVerticalStrut(12));
        formPanel.add(makeRow("Marks :", txtMark));
        formPanel.add(Box.createVerticalStrut(12));
        formPanel.add(makeRow("Max Marks :", txtMax));
        formPanel.add(Box.createVerticalStrut(20));

        JButton btnSave = new JButton("Save Marks");
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
    
    private JPanel makeRow(String labelText, JComponent field) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(Color.WHITE);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setPreferredSize(new Dimension(100, 35));

        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        if (field instanceof JTextField) {
        field.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(203, 213, 225)),
        BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

        row.add(label, BorderLayout.WEST);
        row.add(field, BorderLayout.CENTER);
        return row;
    }

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {

    String rollText = txtRoll.getText().trim();
    String subject = (String) txtSub.getSelectedItem();
    String marksText = txtMark.getText().trim();
    String maxMarksText = txtMax.getText().trim();

    if (rollText.isEmpty() || subject.isEmpty() || marksText.isEmpty() || maxMarksText.isEmpty()) {
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
    
    int marks;
    try {
        marks = Integer.parseInt(marksText);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Marks must be a number.");
        return;
    }
    
    int max;
    try {
        max = Integer.parseInt(maxMarksText);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Max Marks must be a number.");
        return;
    }

    try {
        Connection con = DBConnection.getConnection();
        String sql = "INSERT INTO marks (roll_no, subject, marks, max_marks) VALUES (?,?,?,?)";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, roll);
        pst.setString(2, subject);
        pst.setInt(3, marks);
        pst.setInt(4, max);
        pst.executeUpdate();
        con.close();

        JOptionPane.showMessageDialog(this, "Marks Added Successfully!");
        txtRoll.setText("");
        txtSub.setSelectedIndex(0);
        txtMark.setText("");
        txtMax.setText("");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
}
    }
}
