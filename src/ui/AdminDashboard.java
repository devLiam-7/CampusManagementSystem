package ui;

import utils.ThemeManager;
import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    private String role;

    public AdminDashboard() {
        this.role = "ADMIN";
        initComponents();
    }

    public AdminDashboard(String role) {
        this.role = role;
        initComponents();
    }

    private void initComponents() {
        setTitle("Campus Management System - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 620);
        setLocationRelativeTo(null);
        setResizable(false);

        // ── MAIN PANEL
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));

        // ── HEADER
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(37, 99, 235));
        headerPanel.setPreferredSize(new Dimension(520, 100));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JPanel headerLeft = new JPanel();
        headerLeft.setOpaque(false);
        headerLeft.setLayout(new BoxLayout(headerLeft, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("🎓 Campus Management");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        JLabel roleLabel = new JLabel("Logged in as: " + role);
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        roleLabel.setForeground(new Color(191, 219, 254));

        headerLeft.add(titleLabel);
        headerLeft.add(Box.createVerticalStrut(4));
        headerLeft.add(roleLabel);

        // Dark mode button in header
        JButton btnDarkMode = new JButton("🌙");
        btnDarkMode.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        btnDarkMode.setForeground(Color.WHITE);
        btnDarkMode.setBackground(new Color(59, 130, 246));
        btnDarkMode.setBorderPainted(false);
        btnDarkMode.setFocusPainted(false);
        btnDarkMode.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDarkMode.addActionListener(e -> {
            ThemeManager.setDarkMode(!ThemeManager.isDarkMode());
            ThemeManager.applyTheme(this);
        });

        headerPanel.add(headerLeft, BorderLayout.WEST);
        headerPanel.add(btnDarkMode, BorderLayout.EAST);

        // ── CONTENT PANEL
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(245, 247, 250));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Section label
        JLabel sectionLabel = new JLabel("Quick Actions");
        sectionLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        sectionLabel.setForeground(new Color(100, 116, 139));
        sectionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        contentPanel.add(sectionLabel);
        contentPanel.add(Box.createVerticalStrut(15));

        // ── MENU BUTTONS GRID (2 columns)
        JPanel gridPanel = new JPanel(new GridLayout(0, 2, 12, 12));
        gridPanel.setOpaque(false);
        gridPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Button definitions: label, emoji, color
        Object[][] buttons = {
            {"Add Student",       "👤", new Color(37, 99, 235)},
            {"View Students",     "📋", new Color(5, 150, 105)},
            {"Update Attendance", "📅", new Color(217, 119, 6)},
            {"Fee Management",    "💰", new Color(124, 58, 237)},
            {"Timetable",         "🗓", new Color(220, 38, 38)},
            {"Critical Students", "⚠️",  new Color(234, 88, 12)},
        };

        for (Object[] btn : buttons) {
            String label = (String) btn[0];
            String emoji = (String) btn[1];
            Color color  = (Color)  btn[2];

            JButton button = createMenuButton(label, emoji, color);

            // Disable non-view buttons for non-admin
            if (!role.equals("ADMIN") &&
                (label.equals("Add Student") || label.equals("Update Attendance") || label.equals("Fee Management"))) {
                button.setEnabled(false);
                button.setToolTipText("Admin only");
            }

            button.addActionListener(e -> handleNavigation(label));
            gridPanel.add(button);
        }

        contentPanel.add(gridPanel);
        contentPanel.add(Box.createVerticalStrut(20));

        // ── ADMIN DASHBOARD WIDE BUTTON
        JButton btnDashboard = new JButton("📊  Admin Dashboard Overview");
        btnDashboard.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDashboard.setForeground(Color.WHITE);
        btnDashboard.setBackground(new Color(15, 23, 42));
        btnDashboard.setOpaque(true);
        btnDashboard.setBorderPainted(false);
        btnDashboard.setFocusPainted(false);
        btnDashboard.setPreferredSize(new Dimension(100, 52));
        btnDashboard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
        btnDashboard.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDashboard.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnDashboard.addActionListener(e -> new AdminDashboardFrame().setVisible(true));
        contentPanel.add(btnDashboard);

        contentPanel.add(Box.createVerticalStrut(12));

        // ── LOGOUT BUTTON
        JButton btnLogout = new JButton("🚪  Logout");
        btnLogout.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnLogout.setForeground(new Color(220, 38, 38));
        btnLogout.setBackground(new Color(254, 242, 242));
        btnLogout.setOpaque(true);
        btnLogout.setBorderPainted(false);
        btnLogout.setFocusPainted(false);
        btnLogout.setPreferredSize(new Dimension(100, 40));
        btnLogout.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                new LoginFrame().setVisible(true);
                this.dispose();
            }
        });
        contentPanel.add(btnLogout);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
    }

    private JButton createMenuButton(String label, String emoji, Color color) {
        JButton btn = new JButton("<html><center>" + emoji + "<br><b>" + label + "</b></center></html>");
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(200, 72));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void handleNavigation(String label) {
        switch (label) {
            case "Add Student"       -> new AddStudent().setVisible(true);
            case "View Students"     -> new ViewStudentsFrame().setVisible(true);
            case "Update Attendance" -> new UpdateAttendanceFrame().setVisible(true);
            case "Fee Management"    -> new FeeManagementFrame().setVisible(true);
            case "Timetable"         -> new Timetable().setVisible(true);
            case "Critical Students" -> new CriticalStudentsFrame().setVisible(true);
        }
    }
}