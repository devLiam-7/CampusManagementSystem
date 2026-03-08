package utils;

import database.DBConnection;
import java.sql.*;

public class AuthService {

    public static String login(String username, String password) {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT role FROM users WHERE username = ? AND password = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getString("role");
            }

            con.close();

        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
        }

        return null;
    }
}