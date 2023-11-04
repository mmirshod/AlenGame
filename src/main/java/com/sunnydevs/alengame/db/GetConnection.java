package com.sunnydevs.alengame.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class GetConnection {
    protected static Connection conn;
    {
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/AlenGame", "mildof", " ");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
