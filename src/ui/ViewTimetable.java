package ui;

import database.DBConnection;
import java.sql.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class ViewTimetable extends BaseFrame{
    
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> cmbDept;
    
    public ViewTimetable() {
        super("View Time Table");
        initComponents();
        loadTimetable("");
    }    
    
    private void initComponents(){

    JPanel topBar = new JPanel(new BorderLayout(10, 0));
    topBar.setBackground(new Color(245, 247, 250));
    topBar.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

    JLabel titleLabel = new JLabel("View Timetable");
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
    titleLabel.setForeground(new Color(15, 23, 42));

    JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    filterPanel.setBackground(new Color(245, 247, 250));
    JLabel deptLabel = new JLabel("Department :");
    deptLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    cmbDept = new JComboBox<>(new String[]{"ALL", "BSC", "BCA", "IT"});
    cmbDept.addActionListener(e -> loadTimetable(cmbDept.getSelectedItem().toString().equals("ALL") ? "" : cmbDept.getSelectedItem().toString()));
    filterPanel.add(deptLabel);
    filterPanel.add(cmbDept);

    topBar.add(titleLabel, BorderLayout.WEST);
    topBar.add(filterPanel, BorderLayout.EAST);       
        
        String[] columns = {"Day","Time","Subject","Faculty","Department"};
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
    public void loadTimetable(String search){
        tableModel.setRowCount(0);
        try{
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM timetable";
            PreparedStatement pst;
                

        if(search == null || search.isEmpty()){
            sql = "SELECT * FROM timetable";
            pst = con.prepareStatement(sql);
        }else{
            sql = "SELECT * FROM timetable WHERE department = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, search);
        }
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()){
            String day = rs.getString("day");
            String time = rs.getString("time_slot");
            String subject  = rs.getString("subject");
            String faculty = rs.getString("faculty");
            String department = rs.getString("department");
            tableModel.addRow(new Object[]{day , time , subject , faculty , department});
        }
            con.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this , e.getMessage());
        }
    }    
}
