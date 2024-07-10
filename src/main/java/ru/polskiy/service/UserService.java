package ru.polskiy.service;

import ru.polskiy.model.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Interface representing a user service.
 * This service provides methods for retrieving and managing user information.
 */
public interface UserService {

    /**
     * Retrieves a user by their unique ID.
     *
     * @param id The unique ID of the user.
     * @return An Optional containing the user if found, or an empty Optional if not found.
     */
    Optional<User> getUserById(Long id);

    /**
     * Retrieves a list of all users.
     *
     * @return A list of all users.
     */
    List<User> showAll();

    /**
     * Retrieves a user by their login identifier.
     *
     * @param login The login identifier of the user.
     * @return The user with the specified login.
     */
    User getUserByLogin(String login);
}

