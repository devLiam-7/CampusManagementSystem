package ui;
import database.DBConnection;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import utils.Session;

public class BaseFrame extends JFrame{
    
    protected JPanel contentArea;
    
    
    public BaseFrame(String Title){
        setTitle(Title);
        setSize(900,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(37, 99, 235));
        header.setPreferredSize(new Dimension(900,60));
        header.setBorder(BorderFactory.createEmptyBorder( 0 , 20 , 0 ,20));
        
        JLabel appName = new JLabel("Campus Management System");
        appName.setFont(new Font("Segoe UI", Font.BOLD, 18));
        appName.setForeground(Color.WHITE);
        
        header.add(appName , BorderLayout.WEST);
        
        JButton btnChangePass = new JButton("Change Password");
        btnChangePass.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnChangePass.setForeground(Color.WHITE);
        btnChangePass.setBackground(new Color(37, 99, 235));
        btnChangePass.setBorderPainted(false);
        btnChangePass.setFocusPainted(false);
        btnChangePass.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnChangePass.addActionListener(e -> changePassword());
        
        header.add(btnChangePass, BorderLayout.EAST);
        
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(15 , 23 , 42 ));
        sidebar.setPreferredSize(new Dimension(180,600));
        sidebar.setLayout(new BoxLayout (sidebar , BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(new Color(245, 247, 250));

        
        sidebar.add(makeSidebarButton("Dashboard"));
        sidebar.add(Box.createVerticalStrut(15));
        

    if (Session.role.equals("ADMIN")) {
        sidebar.add(makeSidebarLabel("STUDENTS"));
        sidebar.add(makeSidebarButton("Add Student"));
        sidebar.add(makeSidebarButton("View Students"));
        sidebar.add(makeSidebarButton("View Attendance"));
        sidebar.add(makeSidebarButton("View Marks"));
        sidebar.add(Box.createVerticalStrut(15));
        sidebar.add(makeSidebarLabel("FEES Management"));
        sidebar.add(makeSidebarButton("Update Fees"));
        sidebar.add(makeSidebarButton("View Fees"));
        sidebar.add(Box.createVerticalStrut(15));
        sidebar.add(makeSidebarLabel("TIMETABLE"));
        sidebar.add(makeSidebarButton("Update Timetable"));
        sidebar.add(makeSidebarButton("View Timetable"));
        sidebar.add(Box.createVerticalStrut(15));
        sidebar.add(makeSidebarLabel("REPORTS"));
        sidebar.add(makeSidebarButton("Critical Students"));
    } else {
        sidebar.add(makeSidebarLabel("STUDENTS"));
        sidebar.add(makeSidebarButton("View Students"));
        sidebar.add(makeSidebarButton("Mark Attendance"));
        sidebar.add(makeSidebarButton("View Attendance"));
        sidebar.add(makeSidebarButton("Add Marks"));
        sidebar.add(makeSidebarButton("View Marks"));
        sidebar.add(Box.createVerticalStrut(15));
        sidebar.add(makeSidebarLabel("TIMETABLE"));
        sidebar.add(makeSidebarButton("View Timetable"));
    }
  

        sidebar.add(Box.createVerticalGlue());
        sidebar.add(makeSidebarButton("Logout"));
        
        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentArea, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }
    
private JButton makeSidebarButton(String text) {
    JButton btn = new JButton("  " + text);
    btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    btn.setForeground(new Color(148, 163, 184));
    btn.setBackground(new Color(15, 23, 42));
    btn.setBorderPainted(false);
    btn.setFocusPainted(false);
    btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
    btn.setAlignmentX(Component.LEFT_ALIGNMENT);
    btn.setHorizontalAlignment(SwingConstants.LEFT);
    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    btn.addActionListener(e -> navigate(text)); 
    
    btn.addMouseListener(new java.awt.event.MouseAdapter(){
        public void mouseEntered(java.awt.event.MouseEvent evt){
            btn.setBackground(new Color(37,99,235));
    }
    public void mouseExited(java.awt.event.MouseEvent evt){
        btn.setBackground(new Color(15,23,42));
    }
    });
    return btn;
}

private JLabel makeSidebarLabel(String text) {
    JLabel label = new JLabel("  " + text);
    label.setFont(new Font("Segoe UI", Font.BOLD, 10));
    label.setForeground(new Color(71, 85, 105));
    label.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
    return label;
}

protected void navigate(String screen) {
    switch (screen) {
        case "Dashboard"        -> {new Dashboard().setVisible(true);  this.dispose();}
        case "Add Student"      -> {new AddStudent().setVisible(true);  this.dispose(); }
        case "View Students"    -> {new ViewStudents().setVisible(true);  this.dispose(); }
        case "Mark Attendance"  -> {new MarkAttendance().setVisible(true); this.dispose();}
        case "View Attendance"  -> {new ViewAttendance().setVisible(true); this.dispose();}
        case "Add Marks"        -> {new AddMarks().setVisible(true); this.dispose();}
        case "View Marks" -> {new ViewMarks().setVisible(true); this.dispose();}
        case "Update Fees"      -> {new UpdateFees().setVisible(true); this.dispose(); }
        case "View Fees"        -> {new ViewFees().setVisible(true);  this.dispose(); }
        case "Update Timetable" -> {new UpdateTimetable().setVisible(true);  this.dispose(); }
        case "View Timetable"   -> {new ViewTimetable().setVisible(true);  this.dispose(); }
        case "Critical Students"-> {new CriticalStudents().setVisible(true);  this.dispose(); }
        case "Logout"           -> {
            new LoginFrame().setVisible(true);
            this.dispose();
        }
    }
}
private void changePassword() {
    JPasswordField txtCurrent = new JPasswordField();
    JPasswordField txtNew = new JPasswordField();
    JPasswordField txtConfirm = new JPasswordField();

    Object[] fields = {
        "Current Password:", txtCurrent,
        "New Password:", txtNew,
        "Confirm Password:", txtConfirm
    };

    int result = JOptionPane.showConfirmDialog(this, fields,
        "Change Password", JOptionPane.OK_CANCEL_OPTION);

    if (result != JOptionPane.OK_OPTION) return;

    String current = new String(txtCurrent.getPassword()).trim();
    String newPass = new String(txtNew.getPassword()).trim();
    String confirm = new String(txtConfirm.getPassword()).trim();

    if (current.isEmpty() || newPass.isEmpty() || confirm.isEmpty()) {
        JOptionPane.showMessageDialog(this, "All fields required!");
        return;
    }

    if (!newPass.equals(confirm)) {
        JOptionPane.showMessageDialog(this, "New passwords don't match!");
        return;
    }

    try {
        Connection con = DBConnection.getConnection();
        String checkSql = "SELECT * FROM users WHERE username = ? AND password = ?";
        PreparedStatement checkPst = con.prepareStatement(checkSql);
        checkPst.setString(1, Session.username);
        checkPst.setString(2, current);
        ResultSet rs = checkPst.executeQuery();

        if (!rs.next()) {
            JOptionPane.showMessageDialog(this, "Current password is incorrect!");
            con.close();
            return;
        }

        String updateSql = "UPDATE users SET password = ? WHERE username = ?";
        PreparedStatement updatePst = con.prepareStatement(updateSql);
        updatePst.setString(1, newPass);
        updatePst.setString(2, Session.username);
        updatePst.executeUpdate();
        con.close();

        JOptionPane.showMessageDialog(this, "Password changed successfully!");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
}
    public static void main(String[] args) {
    BaseFrame f = new BaseFrame("Test");
    f.setVisible(true);
}
    
}
