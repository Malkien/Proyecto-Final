package com.project.database;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtils {
    public static final String nameDB = "jdbc:mysql://localhost?useTimezone=true&serverTimezone=UTC";
    public static final String userDB = "adminChatBot";
    public static final String passDB = "admin";

    public static Statement createStatement() throws SQLException {
        return DriverManager.getConnection(nameDB,userDB,passDB).createStatement();
    }
}
