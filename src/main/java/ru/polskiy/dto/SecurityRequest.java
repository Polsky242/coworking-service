package ru.polskiy.dto;

/**
 * Represents a Security Request DTO containing login and password.
 * This DTO is used to encapsulate user credentials for security-related operations.
 *
 * @param login    The login username.
 * @param password The password associated with the login.
 */
public record SecurityRequest(
        String login,
        String password
) {
}
