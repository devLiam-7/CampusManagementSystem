package ui;
import utils.AuthService;
import utils.ThemeManager;
import javax.swing.*;
import java.awt.*;
import utils.Session;

public class LoginFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnDarkMode;

    public LoginFrame() {
        initComponents();
        setTitle("Campus Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(440, 520);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initComponents() {
       
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(37, 99, 235));
        headerPanel.setPreferredSize(new Dimension(440, 160));
        headerPanel.setLayout(new GridBagLayout());

        JLabel iconLabel = new JLabel("🎓");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));

        JLabel titleLabel = new JLabel("Campus Management");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Sign in to your account");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitleLabel.setForeground(new Color(191, 219, 254));

        JPanel headerContent = new JPanel();
        headerContent.setOpaque(false);
        headerContent.setLayout(new BoxLayout(headerContent, BoxLayout.Y_AXIS));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerContent.add(iconLabel);
        headerContent.add(Box.createVerticalStrut(6));
        headerContent.add(titleLabel);
        headerContent.add(Box.createVerticalStrut(4));
        headerContent.add(subtitleLabel);
        headerPanel.add(headerContent);

        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
            BorderFactory.createEmptyBorder(30, 35, 30, 35)
        ));
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));

        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        userLabel.setForeground(new Color(51, 65, 85));
        userLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsername.setPreferredSize(new Dimension(340, 42));
        txtUsername.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
            BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));
        txtUsername.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        passLabel.setForeground(new Color(51, 65, 85));
        passLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setPreferredSize(new Dimension(340, 42));
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
            BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));
        txtPassword.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnLogin = new JButton("Sign In");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBackground(new Color(37, 99, 235));
        btnLogin.setOpaque(true);
        btnLogin.setBorderPainted(false);
        btnLogin.setFocusPainted(false);
        btnLogin.setPreferredSize(new Dimension(340, 46));
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnLogin.addActionListener(e -> handleLogin());

        btnDarkMode = new JButton("Dark Mode");
        btnDarkMode.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnDarkMode.setForeground(new Color(100, 116, 139));
        btnDarkMode.setBackground(new Color(241, 245, 249));
        btnDarkMode.setOpaque(true);
        btnDarkMode.setBorderPainted(false);
        btnDarkMode.setFocusPainted(false);
        btnDarkMode.setPreferredSize(new Dimension(340, 36));
        btnDarkMode.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        btnDarkMode.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDarkMode.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnDarkMode.addActionListener(e -> {
            ThemeManager.setDarkMode(!ThemeManager.isDarkMode());
            ThemeManager.applyTheme(this);
        });

        cardPanel.add(userLabel);
        cardPanel.add(Box.createVerticalStrut(6));
        cardPanel.add(txtUsername);
        cardPanel.add(Box.createVerticalStrut(18));
        cardPanel.add(passLabel);
        cardPanel.add(Box.createVerticalStrut(6));
        cardPanel.add(txtPassword);
        cardPanel.add(Box.createVerticalStrut(24));
        cardPanel.add(btnLogin);
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(btnDarkMode);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(new Color(245, 247, 250));
        wrapper.setBorder(BorderFactory.createEmptyBorder(25, 30, 30, 30));
        wrapper.add(cardPanel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(wrapper, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password.");
            return;
        }

        String role = AuthService.login(username, password);
        if (role != null) {
            Session.role = role;
            Session.username = username;
            if (role.equals("ADMIN")) {
                new Dashboard().setVisible(true);
            } else {
                new FacultyDashboard().setVisible(true);
            }
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.",
                "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}