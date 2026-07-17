package ui;

import database.DBConnection;
import java.sql.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import utils.Session;

public class ViewFees extends BaseFrame{
    
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtSearch;
    
    public ViewFees(){
    super("View Fees");
    initComponents();
    loadFees("");
    } 
    private void initComponents(){
        
        JPanel topBar = new JPanel(new BorderLayout(10 , 0));
        topBar.setBackground(new Color(245, 247, 250));
        topBar.setBorder(BorderFactory.createEmptyBorder(15 , 20 , 10 , 20));
        
        JLabel titleLabel = new JLabel("Fee Details");
        titleLabel.setFont(new Font("Segoe UI" , Font.BOLD , 18));
        titleLabel.setForeground(new Color(15, 23, 42));
        
        txtSearch = new JTextField();
        txtSearch.setFont(new Font("Segoe UI" , Font.PLAIN , 13));
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203 , 213 , 225)),
                BorderFactory.createEmptyBorder(5, 10 , 5, 10)
                ));
        txtSearch.setPreferredSize(new Dimension(200 , 35));
        txtSearch.putClientProperty("JTextField.placeholderText" , "Search .... ");
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyReleased(java.awt.event.KeyEvent e){  
                    loadFees(txtSearch.getText().trim());
                }
    });
        
        topBar.add(titleLabel , BorderLayout.WEST);
        
    JComboBox<String> deptBox = new JComboBox<>();
    deptBox.addItem("All");
    
    try {
        Connection con = DBConnection.getConnection();
        ResultSet rs = con.prepareStatement("SELECT DISTINCT department FROM students").executeQuery();
        while (rs.next()) deptBox.addItem(rs.getString("department"));
        con.close();
    } catch (Exception e) { e.printStackTrace(); }

        deptBox.setSelectedIndex(0);
        deptBox.addActionListener(e -> {
        Session.selectedDepartment = (String) deptBox.getSelectedItem();
        loadFees(txtSearch.getText().trim());
    });

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightPanel.setBackground(new Color(245, 247, 250));
        rightPanel.add(deptBox);
        rightPanel.add(txtSearch);
        topBar.add(rightPanel, BorderLayout.EAST);
        
        String[] columns = {"Roll No","Total Fees","Paid Fees","Due Fees","Status"};
        tableModel = new DefaultTableModel(columns,0){
             public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI" , Font.PLAIN , 13));
        table.setRowHeight(35);
        table.getTableHeader().setFont(new Font("Segoe UI" , Font.BOLD , 13));
        table.getTableHeader().setBackground(new Color(37, 99, 235));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setGridColor(new Color(226, 232, 240));
        table.setShowGrid(true);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(new Color(245, 247, 250));
        mainContent.setBorder(BorderFactory.createEmptyBorder(0 , 20 , 20 , 20));
        
        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
        tableCard.add(scrollPane , BorderLayout.CENTER);
        
        mainContent.add(topBar , BorderLayout.NORTH);
        mainContent.add(tableCard , BorderLayout.CENTER);
        
        contentArea.add(mainContent , BorderLayout.CENTER);
}
    
    public void loadFees(String search){
        tableModel.setRowCount(0);
        try{
            Connection con = DBConnection.getConnection();
            String dept = Session.selectedDepartment;
            String sql;
                if (dept.equals("All")) {
                sql = "SELECT f.* FROM fees f WHERE f.roll_no LIKE ?";
            } else {
                sql = "SELECT f.* FROM fees f JOIN students s ON f.roll_no = s.roll_no WHERE f.roll_no LIKE ? AND s.department = '" + dept + "'";
            }   
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "%" + search + "%");
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()){
            int roll = rs.getInt("roll_no");
            double total = rs.getDouble("total_fee");
            double paid = rs.getDouble("paid_fee");
            double due = rs.getDouble("due_fee");
            String status = rs.getString("status");
            tableModel.addRow(new Object[]{roll , total , paid , due , status});
        }
            con.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this , e.getMessage());
        }
    }
}


