package com.anabol.onlineshop.dao.jdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private String username;
    private String password;
    private String driver;
    private String url;

    public DBConnection(String driver, String url, String username, String password) {
        this.username = username;
        this.password = password;
        this.driver = driver;
        this.url = url;
    }

    public Connection getConnection() throws SQLException {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(url, username, password);
    }
}