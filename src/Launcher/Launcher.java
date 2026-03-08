package Launcher;

import ui.LoginFrame;

public class Launcher {

    public static void main(String[] args) {
    
        javax.swing.SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}