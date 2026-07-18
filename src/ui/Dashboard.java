package ui;

import database.DBConnection;
import java.sql.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import utils.Session;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.*;

public class Dashboard extends BaseFrame {

    private JLabel lblTotalStudents;
    private JLabel lblFeesCollected;
    private JLabel lblLowAttendance;
    private JLabel lblPendingFees;
    private JComboBox<String> deptBox;

    public Dashboard() {
        super("Dashboard");
        initComponent();
        loadStats();
    }

    public void initComponent() {

        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(new Color(245, 247, 250));
        mainContent.setBorder(BorderFactory.createEmptyBorder(15, 20, 20, 20));

        // ── TOP BAR with title + department dropdown ──
        JPanel topBar = new JPanel(new BorderLayout(10, 0));
        topBar.setBackground(new Color(245, 247, 250));
        topBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(new Color(15, 23, 42));

        deptBox = new JComboBox<>();
        deptBox.addItem("All");
        try {
            Connection con = DBConnection.getConnection();
            ResultSet rs = con.prepareStatement("SELECT DISTINCT department FROM students").executeQuery();
            while (rs.next()) deptBox.addItem(rs.getString("department"));
            con.close();
        } catch (Exception e) { e.printStackTrace(); }

        deptBox.setSelectedIndex(0);
        deptBox.setPreferredSize(new Dimension(150, 30));
        deptBox.addActionListener(e -> {
            Session.selectedDepartment = (String) deptBox.getSelectedItem();
            loadStats();
        });

        topBar.add(title, BorderLayout.WEST);
        topBar.add(deptBox, BorderLayout.EAST);

        mainContent.add(topBar, BorderLayout.NORTH);

        // ── CARDS ──
        lblTotalStudents = new JLabel("0");
        lblFeesCollected = new JLabel("0");
        lblLowAttendance = new JLabel("0");
        lblPendingFees   = new JLabel("0");

        JPanel cardsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        cardsPanel.setBackground(new Color(245, 247, 250));
        cardsPanel.setPreferredSize(new Dimension(600, 250));

        cardsPanel.add(makeCard("Total Students", lblTotalStudents, new Color(37, 99, 235),
                () -> showTotalStudents()));
        cardsPanel.add(makeCard("Fees Collected", lblFeesCollected, new Color(22, 163, 74),
                () -> showFeesCollected()));
        cardsPanel.add(makeCard("Low Attendance", lblLowAttendance, new Color(217, 119, 6),
                () -> showLowAttendance()));
        cardsPanel.add(makeCard("Pending Fees", lblPendingFees, new Color(220, 38, 38),
                () -> showPendingFees()));

        mainContent.add(cardsPanel, BorderLayout.CENTER);

        // ── CHART ──
        ChartPanel chartPanel = createAttendanceChart();
        chartPanel.setPreferredSize(new Dimension(600, 200));
        mainContent.add(chartPanel, BorderLayout.SOUTH);

        contentArea.add(mainContent, BorderLayout.CENTER);
    }

    private JPanel makeCard(String title, JLabel numberLabel, Color color, Runnable onClick) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(color);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

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

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                onClick.run();
            }
        });
        return card;
    }

    private void loadStats() {
        String dept = Session.selectedDepartment;
        String deptFilter = dept.equals("All") ? "" : " WHERE department = '" + dept + "'";
        String deptJoinFilter = dept.equals("All") ? "" : " AND s.department = '" + dept + "'";
        String deptFeesFilter = dept.equals("All") ? "" : " JOIN students s ON f.roll_no = s.roll_no WHERE s.department = '" + dept + "'";
        String deptPendingFilter = dept.equals("All") ? " WHERE f.status != 'PAID'" : " JOIN students s ON f.roll_no = s.roll_no WHERE s.department = '" + dept + "' AND f.status != 'PAID'";

        try {
            Connection con = DBConnection.getConnection();

            ResultSet rs1 = con.prepareStatement("SELECT COUNT(*) FROM students" + deptFilter).executeQuery();
            if (rs1.next()) lblTotalStudents.setText(String.valueOf(rs1.getInt(1)));

            ResultSet rs2 = con.prepareStatement("SELECT SUM(f.paid_fee) FROM fees f" + deptFeesFilter).executeQuery();
            if (rs2.next()) lblFeesCollected.setText(String.valueOf((int) rs2.getDouble(1)));

            ResultSet rs3 = con.prepareStatement("SELECT COUNT(*) FROM students WHERE attendance < 75" + (dept.equals("All") ? "" : " AND department = '" + dept + "'")).executeQuery();
            if (rs3.next()) lblLowAttendance.setText(String.valueOf(rs3.getInt(1)));

            ResultSet rs4 = con.prepareStatement("SELECT COUNT(*) FROM fees f" + deptPendingFilter).executeQuery();
            if (rs4.next()) lblPendingFees.setText(String.valueOf(rs4.getInt(1)));

            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void showTotalStudents() {
        String dept = Session.selectedDepartment;
        String sql = dept.equals("All")
                ? "SELECT roll_no, name, department FROM students"
                : "SELECT roll_no, name, department FROM students WHERE department = '" + dept + "'";
        showPopup("All Students", new String[]{"Roll No", "Name", "Department"}, sql);
    }

    private void showFeesCollected() {
        String dept = Session.selectedDepartment;
        String sql = dept.equals("All")
                ? "SELECT roll_no, total_fee, paid_fee, status FROM fees"
                : "SELECT f.roll_no, f.total_fee, f.paid_fee, f.status FROM fees f JOIN students s ON f.roll_no = s.roll_no WHERE s.department = '" + dept + "'";
        showPopup("Fees Collected", new String[]{"Roll No", "Total Fee", "Paid Fee", "Status"}, sql);
    }

    private void showLowAttendance() {
        String dept = Session.selectedDepartment;
        String sql = dept.equals("All")
                ? "SELECT roll_no, name, attendance FROM students WHERE attendance < 75"
                : "SELECT roll_no, name, attendance FROM students WHERE attendance < 75 AND department = '" + dept + "'";
        showPopup("Low Attendance", new String[]{"Roll No", "Name", "Attendance"}, sql);
    }

    private void showPendingFees() {
        String dept = Session.selectedDepartment;
        String sql = dept.equals("All")
                ? "SELECT f.roll_no, s.name, f.due_fee, f.status FROM fees f JOIN students s ON f.roll_no = s.roll_no WHERE f.status != 'PAID'"
                : "SELECT f.roll_no, s.name, f.due_fee, f.status FROM fees f JOIN students s ON f.roll_no = s.roll_no WHERE f.status != 'PAID' AND s.department = '" + dept + "'";
        showPopup("Pending Fees", new String[]{"Roll No", "Name", "Due Fee", "Status"}, sql);
    }

    private void showPopup(String title, String[] columns, String sql) {
        JDialog dialog = new JDialog(this, title, true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);

        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(30);
        table.getTableHeader().setBackground(new Color(37, 99, 235));
        table.getTableHeader().setForeground(Color.WHITE);

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            int colCount = columns.length;
            while (rs.next()) {
                Object[] row = new Object[colCount];
                for (int i = 0; i < colCount; i++) {
                    row[i] = rs.getObject(i + 1);
                }
                model.addRow(row);
            }
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

        dialog.add(new JScrollPane(table));
        dialog.setVisible(true);
    }

    private ChartPanel createAttendanceChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT department, AVG(attendance) FROM students GROUP BY department";
            ResultSet rs = con.prepareStatement(sql).executeQuery();
            while (rs.next()) {
                dataset.addValue(rs.getDouble(2), "Attendance", rs.getString(1));
            }
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Attendance by Department", "Department", "Average Attendance", dataset);
        return new ChartPanel(chart);
    }
}