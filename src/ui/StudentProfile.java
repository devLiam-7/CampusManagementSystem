package ui;

import database.DBConnection;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;

public class StudentProfile extends BaseFrame {

    private int rollNo;
    private JLabel lblName;
    private JLabel lblDept;
    private JLabel lblAttendance;
    private JLabel lblFeeStatus;
    private JTable table;
    private DefaultTableModel tableModel;

    public StudentProfile(int rollNo) {
        super("Student Profile");
        this.rollNo = rollNo;
        initComponents();
        loadProfile();
    }

    private void initComponents() {

        // ── TOP INFO CARD ──
        JPanel infoCard = new JPanel();
        infoCard.setLayout(new BoxLayout(infoCard, BoxLayout.Y_AXIS));
        infoCard.setBackground(Color.WHITE);
        infoCard.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(226, 232, 240)),
        BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));

        lblName = new JLabel("Loading...");
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblName.setForeground(new Color(15, 23, 42));

        lblDept = new JLabel("");
        lblDept.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblDept.setForeground(new Color(71, 85, 105));

        lblAttendance = new JLabel("");
        lblAttendance.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblAttendance.setForeground(new Color(71, 85, 105));

        lblFeeStatus = new JLabel("");
        lblFeeStatus.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblFeeStatus.setForeground(new Color(71, 85, 105));

        infoCard.add(lblName);
        infoCard.add(Box.createVerticalStrut(8));
        infoCard.add(lblDept);
        infoCard.add(Box.createVerticalStrut(4));
        infoCard.add(lblAttendance);
        infoCard.add(Box.createVerticalStrut(4));
        infoCard.add(lblFeeStatus);

        // ── MARKS TABLE ────────────────────────────────────────────
        String[] columns = {"Subject", "Marks", "Max Marks", "Grade"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(35);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(37, 99, 235));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setGridColor(new Color(226, 232, 240));
        table.setShowGrid(true);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JLabel marksTitle = new JLabel("  Marks & Grades");
        marksTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        marksTitle.setForeground(new Color(15, 23, 42));
        marksTitle.setPreferredSize(new Dimension(Integer.MAX_VALUE, 35));
        marksTitle.setOpaque(true);
        marksTitle.setBackground(new Color(245, 247, 250));

        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
        tableCard.add(marksTitle, BorderLayout.NORTH);
        tableCard.add(scrollPane, BorderLayout.CENTER);

        // ── MAIN LAYOUT ────────────────────────────────────────────
        JPanel mainContent = new JPanel(new BorderLayout(0, 15));
        mainContent.setBackground(new Color(245, 247, 250));
        mainContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainContent.add(infoCard, BorderLayout.NORTH);
        mainContent.add(tableCard, BorderLayout.CENTER);

        contentArea.add(mainContent, BorderLayout.CENTER);
    }

    private void loadProfile() {
        try {
            Connection con = DBConnection.getConnection();

            // Query 1 — student info + fee status
                String sql1 = "SELECT s.name, s.department, s.attendance, f.status, f.total_fee, f.paid_fee, f.due_fee " +
                "FROM students s JOIN fees f ON s.roll_no = f.roll_no " +
                "WHERE s.roll_no = ?";
            PreparedStatement pst1 = con.prepareStatement(sql1);
            pst1.setInt(1, rollNo);
            ResultSet rs1 = pst1.executeQuery();

            if (rs1.next()) {
                String name = rs1.getString("name");
                String dept = rs1.getString("department");
                double attendance = rs1.getDouble("attendance");
                String feeStatus = rs1.getString("status");
                
                lblName.setText("👤  " + name);
                lblDept.setText("🎓  Department: " + dept + "   |   Roll No: " + rollNo);
                lblAttendance.setText("📊  Attendance: " + attendance + "%");
                lblFeeStatus.setText("💰  Fee Status: " + feeStatus + 
                "  |  Total: " + rs1.getDouble("total_fee") + 
                "  |  Paid: " + rs1.getDouble("paid_fee") + 
                "  |  Due: " + rs1.getDouble("due_fee"));

                // colour fee status
                if (feeStatus.equalsIgnoreCase("Paid")) {
                    lblFeeStatus.setForeground(new Color(22, 163, 74));
                } else {
                    lblFeeStatus.setForeground(new Color(220, 38, 38));
                }
            }

            // Query 2 — marks
            String sql2 = "SELECT subject, marks, max_marks, grade FROM marks " +
                          "WHERE roll_no = ? ORDER BY subject ASC";
            PreparedStatement pst2 = con.prepareStatement(sql2);
            pst2.setInt(1, rollNo);
            ResultSet rs2 = pst2.executeQuery();

            while (rs2.next()) {
                tableModel.addRow(new Object[]{
                    rs2.getString("subject"),
                    rs2.getInt("marks"),
                    rs2.getInt("max_marks"),
                    rs2.getString("grade")
                });
            }

            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}