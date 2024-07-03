package ru.polskiy.util;

import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Properties;


/**
 * Utility class for loading and accessing application properties.
 *
 * This class provides methods to load properties from the application properties file
 * and to retrieve property values by key.
 */
@NoArgsConstructor
public class PropertiesUtil {

    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    /**
     * Loads properties from the application.properties file.
     *
     * This method is called once when the class is loaded to initialize the PROPERTIES
     * object with the properties from the application.properties file.
     *
     * @throws RuntimeException if an IOException occurs while loading the properties file.
     */
    private static void loadProperties() {
        try (var inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (inputStream != null) {
                PROPERTIES.load(inputStream);
            } else {
                throw new RuntimeException("Properties file not found");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties", e);
        }
    }

    /**
     * Retrieves the property value associated with the given key.
     *
     * @param key The key of the property to retrieve.
     * @return The property value associated with the given key, or null if the key is not found.
     */
    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }
}