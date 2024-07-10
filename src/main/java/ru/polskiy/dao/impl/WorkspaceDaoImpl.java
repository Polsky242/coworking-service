package ru.polskiy.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.polskiy.dao.WorkspaceDao;
import ru.polskiy.model.entity.Workspace;
import ru.polskiy.util.ConnectionManager;

import java.sql.*;
import java.util.*;

/**
 * Implementation of the WorkspaceDAO interface that manages workspace data using a HashMap.
 */
@RequiredArgsConstructor
@Slf4j
public class WorkspaceDaoImpl implements WorkspaceDao {

    private final ConnectionManager connectionProvider;

    /**
     * Retrieves a workspace by its ID.
     *
     * @param id The ID of the workspace to retrieve.
     * @return An Optional containing the workspace if found, otherwise empty.
     */
    @Override
    public Optional<Workspace> findById(Long id) {
        String sqlFindById = """
                SELECT * FROM develop.workspace WHERE id = ?
                """;
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlFindById)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next() ?
                    Optional.of(buildWorkspace(resultSet))
                    : Optional.empty();
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    /**
     * Retrieves all workspaces.
     *
     * @return A list of all workspaces.
     */
    @Override
    public List<Workspace> findAll() {
        String sqlFindAll = """
                SELECT * FROM develop.workspace
                """;
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlFindAll)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Workspace> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildWorkspace(resultSet));
            }
            return result;
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

    /**
     * Saves a new workspace or updates an existing workspace.
     *
     * @param entity The Workspace object to save or update.
     * @return The saved or updated Workspace object.
     */
    @Override
    public Workspace save(Workspace entity) {
        String sqlSave = """
                INSERT INTO develop.workspace (start_date, end_date, type_id, user_id,create_date,update_date)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlSave, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(entity.getStartDate()));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(entity.getEndDate()));
            preparedStatement.setLong(3, entity.getTypeId());
            preparedStatement.setObject(4, entity.getUserId());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(entity.getCreatedAt()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(entity.getCreatedAt()));
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                if (id != 0) {
                    entity.setId(id);
                } else {
                    throw new RuntimeException("Failed to generate ID for workspace");
                }
            }
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save workspace", e);
        }
    }

    /**
     * Deletes a user by their ID.
     *
     * This method deletes a user from the database based on the provided user ID.
     *
     * @param id The ID of the user to delete.
     * @return true if the user was successfully deleted, otherwise false.
     */
    @Override
    public boolean delete(Long id) {
        String sqlDelete = """
                DELETE FROM develop.workspace WHERE id = ?;
                """;
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate()>0;
        } catch (SQLException e) {
            log.error("Ошибка при выполнении SQL-запроса: " + e.getMessage());
            return false;
        }
    }

    /**
     * Updates an existing workspace.
     *
     * @param workspace The updated Workspace object.
     * @return The updated Workspace object.
     * @throws IllegalArgumentException If the workspace with the provided ID doesn't exist.
     */
    @Override
    public Workspace update(Workspace workspace) {
        String sqlUpdate = """
                UPDATE develop.workspace
                SET start_date = ?,  end_date= ?, type_id = ?, user_id = ?,update_date= ?
                WHERE id = ?;
                """;

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate)) {

            preparedStatement.setTimestamp(1, Timestamp.valueOf(workspace.getStartDate()));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(workspace.getStartDate()));
            preparedStatement.setLong(3,workspace.getTypeId() );
            preparedStatement.setLong(4, workspace.getUserId());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(workspace.getUpdatedAt()));
            preparedStatement.setLong(6, workspace.getId());

            preparedStatement.executeUpdate();
            return workspace;
        } catch (SQLException e) {
            log.error("Ошибка при выполнении SQL-запроса: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Workspace> findAllByUserId(Long userId) {
        String sqlFindAllByUserId = """
                SELECT * FROM develop.workspace WHERE user_id = ?
                """;
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlFindAllByUserId)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Workspace> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildWorkspace(resultSet));
            }
            return result;
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

    /**
     * Constructs a Workspace object from the given ResultSet.
     *
     * This method extracts the workspace details from the provided ResultSet and
     * constructs a User object using the extracted data.
     *
     * @param resultSet The ResultSet containing the workspace data retrieved from the database.
     * @return A Workspace object with data from the ResultSet.
     * @throws SQLException if there is an error accessing the data in the ResultSet.
     */
    private Workspace buildWorkspace(ResultSet resultSet) throws SQLException {
        Workspace workspace = Workspace.builder()
                .startDate(resultSet.getTimestamp("start_date").toLocalDateTime())
                .endDate(resultSet.getTimestamp("end_date").toLocalDateTime())
                .typeId(resultSet.getLong("type_id"))
                .userId(resultSet.getLong("user_id"))
                .build();
        workspace.setId(resultSet.getLong("id"));
        workspace.setCreatedAt(resultSet.getTimestamp("create_date").toLocalDateTime());
        workspace.setUpdatedAt(resultSet.getTimestamp("update_date").toLocalDateTime());
        return workspace;
    }
}
