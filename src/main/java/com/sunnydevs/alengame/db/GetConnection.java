package com.sunnydevs.alengame.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class GetConnection {
    protected static Connection conn;
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
//            conn = DriverManager.getConnection("jdbc:postgresql://ep-small-sky-23370704.eu-central-1.aws.neon.tech/AlenGame?user=mmirshod&password=CxZQz0BuF2Hs&sslmode=require");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/AlenGame", "mildof", " ");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
