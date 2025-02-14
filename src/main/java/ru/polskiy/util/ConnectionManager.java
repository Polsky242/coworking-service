package ru.polskiy.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Manages database connections.
 *
 * This class is responsible for providing connections to the database using
 * the provided URL, username, and password.
 */
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
public class ConnectionManager {

    @Value("${datasource.url}")
    private String URL;
    @Value("${datasource.driver-class-name}")
    private String driver;
    @Value("${datasource.username}")
    private String username;
    @Value("${datasource.password}")
    private String password;

    public Connection getConnection() {
        try {
            Class.forName(driver);

            return DriverManager.getConnection(
                    URL,
                    username,
                    password
            );
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get a database connection.", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection(String URL, String username, String password, String driver) {
        try {
            Class.forName(driver);
            return DriverManager.getConnection(URL, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get a database connection.", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}