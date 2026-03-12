package ui;
import database.DBConnection;
import java.sql.*;
import java.awt.*;
import javax.swing.*;

public class FacultyDashboard extends BaseFrame {

    private JLabel lblTotalStudents;
    private JLabel lblTotalTimetable;

    public FacultyDashboard() {
        super("Faculty Dashboard");
        initComponents();
        loadStats();
    }

    private void initComponents() {
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(new Color(245, 247, 250));
        mainContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Faculty Dashboard");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainContent.add(title, BorderLayout.NORTH);

        lblTotalStudents = new JLabel("0");
        lblTotalTimetable = new JLabel("0");

        JPanel cardsPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        cardsPanel.setBackground(new Color(245, 247, 250));
        cardsPanel.add(makeCard("Total Students", lblTotalStudents, new Color(37, 99, 235)));
        cardsPanel.add(makeCard("Timetable Entries", lblTotalTimetable, new Color(22, 163, 74)));

        mainContent.add(cardsPanel, BorderLayout.CENTER);
        contentArea.add(mainContent, BorderLayout.CENTER);
    }

    private JPanel makeCard(String title, JLabel numberLabel, Color color) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(color);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        numberLabel.setForeground(Color.WHITE);
        numberLabel.setFont(new Font("Segoe UI", Font.BOLD, 40));
        numberLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalGlue());
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(numberLabel);
        card.add(Box.createVerticalGlue());
        return card;
    }

    private void loadStats() {
        try {
            Connection con = DBConnection.getConnection();

            ResultSet rs1 = con.prepareStatement("SELECT COUNT(*) FROM students").executeQuery();
            if (rs1.next()) lblTotalStudents.setText(String.valueOf(rs1.getInt(1)));

            ResultSet rs2 = con.prepareStatement("SELECT COUNT(*) FROM timetable").executeQuery();
            if (rs2.next()) lblTotalTimetable.setText(String.valueOf(rs2.getInt(1)));

            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}