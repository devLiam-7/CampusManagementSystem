package ui;

import database.DBConnection;
import java.sql.*;
import java.awt.*;
import javax.swing.*;

public class UpdateTimetable extends BaseFrame{
    
    private JComboBox<String> cmbDay;
    private JTextField txtTime;
    private JTextField txtSubject;
    private JTextField txtFaculty;
    private JTextField txtDept;
    
    public UpdateTimetable(){
        super("Add TimeTable");
     
        txtTime = new JTextField();
        txtSubject = new JTextField();
        txtFaculty = new JTextField();
        txtDept = new JTextField();
        cmbDay = new JComboBox();
        
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel , BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(25 , 30 , 25, 30));
        
        JLabel titleLabel = new JLabel("Update TimeTable");
        titleLabel.setFont(new Font("Segoe UI" , Font.BOLD , 18));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(titleLabel);
        
        formPanel.add(Box.createVerticalStrut(20));
        JPanel dayRow = new JPanel(new BorderLayout(10,0));
        dayRow.setBackground(Color.WHITE);
        dayRow.setMaximumSize(new Dimension(Integer.MAX_VALUE , 35));
        
        JLabel dayLabel = new JLabel("Day :");
        dayLabel.setPreferredSize(new Dimension(130,35));
        cmbDay = new JComboBox<>(new String[]{"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"});
        dayRow.add(dayLabel , BorderLayout.WEST);
        dayRow.add(cmbDay , BorderLayout.CENTER);
        
        formPanel.add(dayRow);
        
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(makeRow("Time : ", txtTime));
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(makeRow("Subject : ", txtSubject));
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(makeRow("Faculty : ", txtFaculty));
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(makeRow("Department : " , txtDept));
        formPanel.add(Box.createVerticalStrut(20));
        
        JButton btnSave = new JButton("Save TimeTable");
        btnSave.setBackground(new Color(37, 99, 235));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFocusPainted(false);
        btnSave.setFont(new Font("Segoe UI" , Font.PLAIN , 13));
        btnSave.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btnSave.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnSave.addActionListener(e -> btnSaveActionPerformed(e));
        formPanel.add(btnSave);
        
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(new Color(245, 247, 250));
        wrapper.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 200));
        wrapper.add(formPanel , BorderLayout.CENTER);
        contentArea.add(wrapper , BorderLayout.CENTER);
        
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

    String day = cmbDay.getSelectedItem().toString();
    String time = txtTime.getText().trim();
    String subject = txtSubject.getText().trim();
    String faculty = txtFaculty.getText().trim();
    String dept = txtDept.getText().trim();

    if (day.isEmpty() || time.isEmpty() || subject.isEmpty() || faculty.isEmpty() || dept.isEmpty()) {
        JOptionPane.showMessageDialog(this, "All fields are required.");
        return;
    }

    try {
        Connection con = DBConnection.getConnection();
        String sql = "INSERT INTO timetable (day, time_slot, subject, faculty, department) VALUES (?,?,?,?,?)";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, day);
        pst.setString(2, time);
        pst.setString(3, subject);
        pst.setString(4, faculty);
        pst.setString(5,dept);
        pst.executeUpdate();
        con.close();

        JOptionPane.showMessageDialog(this, "Time Table Updated Successfully!");
        cmbDay.setSelectedIndex(0);
        txtTime.setText("");
        txtSubject.setText("");
        txtFaculty.setText("");
        txtDept.setText("");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
}
    }
    
    
}
