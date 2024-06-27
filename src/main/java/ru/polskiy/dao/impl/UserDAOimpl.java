package ru.polskiy.dao.impl;

import lombok.RequiredArgsConstructor;
import ru.polskiy.dao.UserDAO;
import ru.polskiy.model.entity.User;
import ru.polskiy.model.type.Role;
import ru.polskiy.util.ConnectionManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import java.util.*;

/**
 * Implementation of the UserDAO interface that manages user data using a HashMap.
 */
@RequiredArgsConstructor
public class UserDAOimpl implements UserDAO {

    private final ConnectionManager connectionProvider;

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return An Optional containing the user if found, otherwise empty.
     */
    @Override
    public Optional<User> findById(Long id) {
        String sqlFindById = """
                SELECT * FROM develop.users WHERE id = ?
                """;
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlFindById)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next() ?
                    Optional.of(buildUser(resultSet))
                    : Optional.empty();
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    /**
     * Retrieves all users stored in the DAO.
     *
     * @return An unmodifiable list of all users.
     */
    @Override
    public List<User> findAll() {
        String sqlFindAll = """
                SELECT * FROM develop.users;
                """;
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlFindAll)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> users = new ArrayList<>();

            while (resultSet.next()) {
                users.add(buildUser(resultSet));
            }

            return users;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve all users", e);
        }
    }

    /**
     * Saves a new user or updates an existing user in the DAO.
     *
     * @param entity The User object to save or update.
     * @return The saved or updated User object.
     */
    @Override
    public User save(User entity) {
        String sqlSave = """
                INSERT INTO develop.users (login, password, registration_date, role,update_date) VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlSave)) {
            preparedStatement.setString(1, entity.getLogin());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(entity.getCreatedAt()));
            preparedStatement.setString(4, entity.getRole().toString());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(entity.getUpdatedAt()));
            preparedStatement.executeUpdate();

            return findByLogin(entity.getLogin()).orElse(entity);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save user", e);
        }
    }

    /**
     * Retrieves a user by their login name.
     *
     * @param login The login name of the user to retrieve.
     * @return An Optional containing the user if found, otherwise empty.
     */
    @Override
    public Optional<User> findByLogin(String login) {
        String sqlFindByLogin = """
                SELECT * FROM develop.users WHERE login = ?
                """;
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlFindByLogin)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next() ?
                    Optional.of(buildUser(resultSet))
                    : Optional.empty();
        } catch (SQLException e) {
            return Optional.empty();
        }
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
        String sqlUpdate = """
                UPDATE develop.users SET login = ?, password = ?, update_date = ? WHERE id = ?
                """;
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(user.getUpdatedAt()));
            preparedStatement.setLong(4, user.getId());
            preparedStatement.executeUpdate();
            return user;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update user", e);
        }
    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        User user = User.builder()
                .login(resultSet.getString("login"))
                .role(Role.valueOf(resultSet.getString("role")))
                .password(resultSet.getString("password"))
                .build();
        user.setId(resultSet.getLong("id"));
        user.setCreatedAt(resultSet.getTimestamp("registration_date").toLocalDateTime());
        user.setUpdatedAt(resultSet.getTimestamp("update_date").toLocalDateTime());
        return user;
    }
}
