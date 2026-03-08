package ui;

import java.awt.*;
import javax.swing.*;

public class Practice1 extends JFrame {

    public Practice1() {
        setTitle("My First Window");
        setSize(400, 300);
        setLocationRelativeTo(null); // centers the window on screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // closes app when X is clicked
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(37, 99, 235));
        topPanel.setPreferredSize(new Dimension(400,80));
        
        JPanel centerPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Add 4 buttons
        JButton btn1 = new JButton("Button 1");
        btn1.setFocusPainted(false);
        btn1.setBackground(Color.BLUE);
        btn1.setForeground(Color.WHITE);
        centerPanel.add( btn1 );
        
        JButton btn2 = new JButton ("Button 2");
        btn2.setFocusPainted(false);
        btn2.setBackground(Color.PINK);
        btn2.setForeground(Color.WHITE);
        centerPanel.add( btn2 );
        
        JButton btn3 = new JButton("Button 3");
        btn3.setFocusPainted(false);
        btn3.setBackground(Color.cyan);
        btn3.setForeground(Color.WHITE);
        centerPanel.add( btn3 );
        
        JButton btn4 = new JButton("Button 4");
        btn4.setFocusPainted(false);
        btn4.setBackground(Color.BLACK);
        btn4.setForeground(Color.WHITE);
        centerPanel.add( btn4 );
        
        
        // Add to main panel
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        JLabel titleLabel = new JLabel("Dashboard");
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(new Font("Segoe UI",Font.BOLD,20));
        topPanel.add(titleLabel);
        
        topPanel.setLayout(new GridBagLayout());
        
        mainPanel.add(topPanel , BorderLayout.NORTH);
        
        setContentPane(mainPanel);
        
        setVisible(true); // makes the window appear
    }

    public static void main(String[] args) {
        new Practice1();
    }
}