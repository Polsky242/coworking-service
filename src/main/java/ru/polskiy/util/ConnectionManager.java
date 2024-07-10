package ru.polskiy.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Manages database connections.
 *
 * This class is responsible for providing connections to the database using
 * the provided URL, username, and password.
 */
public class ConnectionManager {

    private final String URL;
    private final String username;
    private final String password;

    /**
     * Constructs a ConnectionManager with the specified database URL, username, and password.
     *
     * @param URL The URL of the database.
     * @param username The username for the database connection.
     * @param password The password for the database connection.
     */
    public ConnectionManager(String URL, String username, String password, String driver) {
        this.URL = URL;
        this.username = username;
        this.password = password;

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Provides a connection to the database.
     *
     * This method attempts to establish a connection to the database using the
     * provided URL, username, and password.
     *
     * @return A Connection object representing the database connection.
     * @throws RuntimeException if a connection cannot be established.
     */
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, username, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}