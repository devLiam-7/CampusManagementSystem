package ui;

import database.DBConnection;
import java.sql.*;
import java.awt.*;
import javax.swing.*;


    public class UpdateAttendance extends BaseFrame{
    
    private JTextField txtRoll;
    private JTextField txtAttendance;
    
    public UpdateAttendance(){
        
        super("Update Attendance");
        initComponents();
    }
        private void initComponents(){
            
            JPanel formPanel = new JPanel();
            formPanel.setLayout(new BoxLayout(formPanel , BoxLayout.Y_AXIS));
            formPanel.setBackground(Color.WHITE);
            formPanel.setBorder(BorderFactory.createEmptyBorder( 25 , 30 , 25 , 30));
            
            txtRoll = new JTextField();
            txtAttendance = new JTextField();
            
            
            JLabel titleLabel = new JLabel("Update Attendance");
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
            titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            formPanel.add(titleLabel);
            
           formPanel.add(Box.createVerticalStrut(20));
           formPanel.add(makeRow("Roll No :" , txtRoll));
           formPanel.add(Box.createVerticalStrut(20));
           formPanel.add(makeRow("New Attendance :", txtAttendance));
           
           JButton btnUpdate = new JButton("Update Attendance");
           btnUpdate.setBackground(new Color(217, 119, 6));
           btnUpdate.setForeground(Color.WHITE);
           btnUpdate.setFocusPainted(false);
           btnUpdate.setMaximumSize(new Dimension(Integer.MAX_VALUE,40));
           btnUpdate.setAlignmentX(Component.LEFT_ALIGNMENT);
           btnUpdate.addActionListener(e -> UpdateAttendance());
           formPanel.add(Box.createVerticalStrut(24));
           formPanel.add(btnUpdate);
           
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
        label.setPreferredSize(new Dimension(130, 35));

        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(203, 213, 225)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        row.add(label, BorderLayout.WEST);
        row.add(field, BorderLayout.CENTER);
        return row;
    }
    private void UpdateAttendance(){
        
        String rollText = txtRoll.getText().trim();
        String AttendanceText = txtAttendance.getText().trim();
        
        
        if(rollText.isEmpty() || AttendanceText.isEmpty()){
            JOptionPane.showMessageDialog(this,"All Fields Required");
            return;
        }
        
        int roll;
        try{
        roll = Integer.parseInt(rollText);
       }catch (NumberFormatException e){
        JOptionPane.showMessageDialog(this,"Roll No Must Be An Number");
        return;
    }
        double attendance;
        try{
            attendance = Double.parseDouble(AttendanceText);
            if(attendance < 0 || attendance > 100){
                
                JOptionPane.showMessageDialog(this,"Sttendance Must Be Betwwen 0 TO 100");
                return;
            }
            }catch (NumberFormatException e){
                    JOptionPane.showMessageDialog(this,"Attendance Must Be An Number");
                    return;
            }
    
    
    try{
    
    Connection con = DBConnection.getConnection();
    String sql = "UPDATE students SET attendance = ? WHERE roll_no = ?";
    PreparedStatement pst = con.prepareStatement(sql);
    pst.setDouble(1, attendance); 
    pst.setInt(2, roll);     
    int rows = pst.executeUpdate();

    if (rows > 0) {
    JOptionPane.showMessageDialog(this, "Attendance Updated Successfully!");
    txtRoll.setText("");
    txtAttendance.setText("");
    } else {
    JOptionPane.showMessageDialog(this, "Student not found!");
    }
    con.close();
    
    JOptionPane.showMessageDialog(this, "Attendance Updated Succesfully");
    txtRoll.setText("");
    txtAttendance.setText("");
    
    }catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
   }   
 }
   
    
