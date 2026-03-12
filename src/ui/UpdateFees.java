package ui;

import database.DBConnection;
import java.sql.*;
import java.awt.*;
import javax.swing.*;
        

public class UpdateFees extends BaseFrame{
    
   private JTextField txtRoll ;
   private JTextField txtTotal ;
   private JTextField txtPaid ;
   private JTextField txtDue ;
   private JLabel lblStatus ; 
    
    public UpdateFees(){
        
        super("Update Fees");
        initComponents();
    }
    private void initComponents(){
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel , BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(25 , 30 , 25 , 30));
        
        txtRoll = new JTextField();
        txtTotal = new JTextField();
        txtPaid = new JTextField();
        txtDue = new JTextField();
        lblStatus = new JLabel();
     
        JLabel titleLabel = new JLabel("Update Fees");
        titleLabel.setFont(new Font("Segoe UI" , Font.BOLD , 18));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(titleLabel);
        
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(makeRow("Roll No : " , txtRoll ));
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(makeRow("Total Fees :", txtTotal ));
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(makeRow("Fees Paid :" , txtPaid));
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(makeRow("Fees Due :" , txtDue ));
        formPanel.add(Box.createVerticalStrut(20));
        
        JPanel statusRow = new JPanel(new BorderLayout(10, 0));
        statusRow.setBackground(Color.WHITE);
        statusRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        JLabel statusTitle = new JLabel("Status :");
        statusTitle.setPreferredSize(new Dimension(130, 35));
        statusTitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblStatus.setForeground(new Color(37, 99, 235));
        statusRow.add(statusTitle, BorderLayout.WEST);
        statusRow.add(lblStatus, BorderLayout.CENTER);
        formPanel.add(statusRow);
        
        
        JButton btnSave = new JButton("Save Fees");
        btnSave.setBackground(new Color(217, 119, 6));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFocusPainted(false);
        btnSave.setMaximumSize(new Dimension (Integer.MAX_VALUE , 30));
        btnSave.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnSave.addActionListener(e-> SaveFees());
        formPanel.add(Box.createVerticalStrut(24));
        formPanel.add(btnSave);
        
        
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(new Color(245, 247, 250));
        wrapper.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 200));
        wrapper.add(formPanel , BorderLayout.CENTER);
        contentArea.add(wrapper, BorderLayout.CENTER);    
        
    }
    
    private JPanel makeRow(String labeltext , JTextField field){
    JPanel row = new JPanel( new BorderLayout(10 , 0));
    row.setBackground(Color.WHITE);
    row.setMaximumSize(new Dimension (Integer.MAX_VALUE , 35));        
    
    JLabel label = new JLabel(labeltext);
    label.setFont(new Font("Segoe UI" , Font.PLAIN , 13));
    label.setPreferredSize(new Dimension(130 , 35));
    
    field.setFont(new Font("Segoe UI" , Font.PLAIN , 13));
    field.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(203, 213, 225)),
        BorderFactory.createEmptyBorder(5, 10, 5, 10)
    ));
    
    row.add(label , BorderLayout.WEST);
    row.add(field , BorderLayout.CENTER);
    return row;
    
    }

    private void SaveFees(){
        
        String rolltext = txtRoll.getText().trim();
        String totaltext = txtTotal.getText().trim();
        String paidtext = txtPaid.getText().trim();
        
        if(rolltext.isEmpty() || totaltext.isEmpty() || paidtext.isEmpty()){
            JOptionPane.showMessageDialog(this,"All Field Required");
            return;
        }
        
        int roll ;
        double total, paid , due ;
        String status;
        
        try{
            roll = Integer.parseInt(rolltext);
            total = Integer.parseInt(totaltext);
            paid = Integer.parseInt(paidtext);   
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this,"Must Be An Number");
            return;
        }
        
        if (paid>total){
            JOptionPane.showMessageDialog(this,"Paid Fees Cannot Be Graeter Then Total Fees");
            return;
        }
        due = total-paid;
        
        if(due == 0){
            status = "PAID";
        }else if (paid == 0){
            status = "Due";
        }else{
            status = "PARTIAL";
        }
         
        txtDue.setText(String.valueOf(due));
        lblStatus.setText(status);
    
    try{
    
    Connection con = DBConnection.getConnection();
    String checkSql = "SELECT roll_no FROM students WHERE roll_no = ?";
    PreparedStatement checkPst = con.prepareStatement(checkSql);
    checkPst.setInt(1,roll);
    ResultSet rs = checkPst.executeQuery();
    
    if(!rs.next()){
        
       JOptionPane.showMessageDialog(this,"Student does not exist , Add Student first");
       con.close();
       return;
    }
    String sql ="Replace into fees(roll_no, total_fee , paid_fee , due_fee, status) values (?,?,?,?,?)";
    PreparedStatement pst = con.prepareStatement(sql);
    pst.setInt(1, roll);
    pst.setDouble(2, total);
    pst.setDouble(3, paid);
    pst.setDouble(4, due);
    pst.setString(5, status);
    pst.executeUpdate();
    
    
    JOptionPane.showMessageDialog(this,"Fee Details Saved Succesfully");
    con.close();
    }catch(Exception e){
    JOptionPane.showMessageDialog(this,e.getMessage());
        }
    }
}
