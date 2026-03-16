package com.quantityApp.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionPool {

    public static Connection getConnection() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/quantitydb",
                    "root",
                    "Abhay@1234"
            );

        } catch (Exception e) {
            throw new RuntimeException("Database connection failed", e);
        }
    }

    public static void releaseConnection(Connection conn) {

        try {
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}