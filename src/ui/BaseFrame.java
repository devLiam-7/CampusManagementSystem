package ui;

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
        sidebar.add(makeSidebarButton("Attendance"));
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
        case "Attendance"       -> {new UpdateAttendance().setVisible(true); this.dispose(); }
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
    public static void main(String[] args) {
    BaseFrame f = new BaseFrame("Test");
    f.setVisible(true);
}
    
}
