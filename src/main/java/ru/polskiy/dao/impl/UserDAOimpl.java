package ru.polskiy.dao.impl;

import ru.polskiy.dao.UserDAO;
import ru.polskiy.model.entity.User;
import ru.polskiy.model.type.Role;

import java.time.LocalDateTime;
import java.util.*;

import java.util.*;

/**
 * Implementation of the UserDAO interface that manages user data using a HashMap.
 */
public class UserDAOimpl implements UserDAO {

    private final Map<Long, User> users = new HashMap<>();

    private Long id = 1L;

    /**
     * Constructs a UserDAOimpl object and initializes it with an initial admin user.
     */
    public UserDAOimpl() {
        save(new User(id, "Admin", "12345", Role.ADMIN));
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return An Optional containing the user if found, otherwise empty.
     */
    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    /**
     * Retrieves all users stored in the DAO.
     *
     * @return An unmodifiable list of all users.
     */
    @Override
    public List<User> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(users.values()));
    }

    /**
     * Saves a new user or updates an existing user in the DAO.
     *
     * @param entity The User object to save or update.
     * @return The saved or updated User object.
     */
    @Override
    public User save(User entity) {
        entity.setId(id++);
        users.put(entity.getId(), entity);
        return users.get(entity.getId());
    }

    /**
     * Retrieves a user by their login name.
     *
     * @param login The login name of the user to retrieve.
     * @return An Optional containing the user if found, otherwise empty.
     */
    @Override
    public Optional<User> findByLogin(String login) {
        for (User user : users.values()) {
            if (user.getLogin().equals(login)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    /**
     * Updates an existing user in the DAO.
     *
     * @param user The updated User object.
     * @return The updated User object.
     * @throws IllegalArgumentException If the user with the provided ID doesn't exist.
     */
    @Override
    public User update(User user) {
        Long userId = user.getId();
        if (users.containsKey(userId)) {
            users.put(userId, user);
            return users.get(userId);
        } else {
            throw new IllegalArgumentException("User with id:" + userId + " doesn't exist");
        }
    }
}
