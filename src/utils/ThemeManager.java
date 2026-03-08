package utils;

import java.awt.*;
import javax.swing.*;

public class ThemeManager {

    private static boolean darkMode = false;
 
    public static void setDarkMode(boolean value) {
        darkMode = value;
    }

    public static boolean isDarkMode() {
        return darkMode;
    }

    public static void applyTheme(Container container) {
        
        Color bg = Color.WHITE;
        Color fg = Color.BLACK;
        Color btn = Color.LIGHT_GRAY;

        if (darkMode == true) {
            bg = Color.DARK_GRAY;
            fg = Color.WHITE;
            btn = Color.GRAY;
        }

        container.setBackground(bg);

        Component[] components = container.getComponents();
        for (int i = 0; i < components.length; i++) {
            Component c = components[i];
            
            String type = c.getClass().getSimpleName();

            if (type.equals("JPanel")) {
                c.setBackground(bg);
            } 
            else if (type.equals("JLabel")) {
                c.setForeground(fg);
            } 
            else if (type.equals("JButton")) {
                c.setBackground(btn);
                c.setForeground(fg);
            } 
            else if (type.equals("JTextField") || type.equals("JTable")) {
                c.setBackground(Color.WHITE); 
                c.setForeground(Color.BLACK);
            }

            if (c instanceof Container) {
                applyTheme((Container) c);
            }
        }
    }
}