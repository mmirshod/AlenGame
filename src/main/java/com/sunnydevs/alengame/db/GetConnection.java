package com.sunnydevs.alengame.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * An abstract class providing a shared database connection for AlenGame application.
 */
public abstract class GetConnection {

    /**
     * The shared database connection used by subclasses.
     */
    protected static Connection conn;

    static {
        try {
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1); // Terminate the application if the driver is not found
        }

        try {
            // Establish a connection to the PostgreSQL database
            // Note: Uncomment the appropriate connection string based on your database configuration
            // Example with AWS RDS:
            // conn = DriverManager.getConnection("jdbc:postgresql://ep-small-sky-23370704.eu-central-1.aws.neon.tech/AlenGame?user=mmirshod&password=CxZQz0BuF2Hs&sslmode=require");

            // Example with localhost:
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/AlenGame", "mildof", " ");
        } catch (SQLException e) {
            throw new RuntimeException(e); // Throw a runtime exception if connection cannot be established
        }
    }
}