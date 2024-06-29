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

    private final String url;
    private final String username;
    private final String password;

    /**
     * Constructs a ConnectionManager with the specified database URL, username, and password.
     *
     * @param url The URL of the database.
     * @param username The username for the database connection.
     * @param password The password for the database connection.
     */
    public ConnectionManager(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
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
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}