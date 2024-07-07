package ru.polskiy.service.impl;

import lombok.RequiredArgsConstructor;
import ru.polskiy.dao.UserDao;
import ru.polskiy.model.entity.User;
import ru.polskiy.service.UserService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Service implementation for managing users.
 * This class provides methods to handle operations related to users,
 * such as retrieving a user by ID and listing all users.
 */
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDAO;

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return an Optional containing the user if found, or empty if not found
     */
    @Override
    public Optional<User> getUserById(Long id) {
        return userDAO.findById(id);
    }

    /**
     * Retrieves a list of all users.
     *
     * @return a list of all users
     */
    @Override
    public List<User> showAll() {
        return userDAO.findAll();
    }

    @Override
    public User getUserByLogin(String login) {
        return userDAO.findByLogin(login)
                .orElseThrow(() -> new NoSuchElementException("No user found with login: " + login));
    }
}
