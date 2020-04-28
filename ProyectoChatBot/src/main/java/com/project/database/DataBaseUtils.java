package com.project.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseUtils {
    public static final String nameDB = "jdbc:mysql://localhost:3306/chatbot?useTimezone=true&serverTimezone=UTC";
    public static final String userDB = "adminchatbot";
    public static final String passDB = "admin";
    public static final String nameTableUser = "user";

    public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(nameDB,userDB,passDB);
    }
}
