package com.project.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * CLASS FOR THE DATABASE
 */
public class DataBaseUtils {
    /**
     * THE URL OF THE DATABASE
     */
    public static final String nameDB = "jdbc:mysql://localhost:3306/chatbot?useTimezone=true&serverTimezone=UTC";
    /**
     * THE NAME OF THE USER FROM THE DATABASE
     */
    public static final String userDB = "adminchatbot";
    /**
     * THE PASSWORD OF THE USER FROM THE DATABASE
     */
    public static final String passDB = "admin";
    /**
     * THE NAME OF THE TABLE USER
     */
    public static final String nameTableUser = "user";

    /**
     * METHOD THAN CREATE THE CONNECTION
     * @return THE CONNECTION
     * @throws SQLException
     */
    public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(nameDB, userDB, passDB);
    }
}