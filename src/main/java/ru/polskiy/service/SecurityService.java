package ru.polskiy.service;

import ru.polskiy.dto.TokenResponse;
import ru.polskiy.model.entity.User;

import java.util.Optional;

/**
 * Interface representing a security service.
 * This service provides methods for user registration and authorization.
 */
public interface SecurityService {

    /**
     * Registers a new user with the specified login and password.
     *
     * @param login The login identifier for the new user.
     * @param password The password for the new user.
     * @return The registered user.
     */
    User register(String login, String password);

    /**
     * Authorizes a user with the specified login and password.
     *
     * @param login The login identifier of the user.
     * @param password The password of the user.
     * @return A token response containing authentication details.
     */
    TokenResponse authorize(String login, String password);
}

