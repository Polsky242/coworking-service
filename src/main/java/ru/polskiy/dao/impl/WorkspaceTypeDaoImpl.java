package ru.polskiy.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.polskiy.dao.WorkspaceTypeDao;
import ru.polskiy.model.entity.WorkspaceType;
import ru.polskiy.util.ConnectionManager;

import java.sql.*;
import java.util.*;

/**
 * Implementation of the WorkspaceTypeDAO interface that manages workspace types using a HashMap.
 */
@RequiredArgsConstructor
@Repository
public class WorkspaceTypeDaoImpl implements WorkspaceTypeDao {

    private final ConnectionManager connectionProvider;

    /**
     * Retrieves a workspace type by its ID.
     *
     * @param id The ID of the workspace type to retrieve.
     * @return An Optional containing the workspace type if found, otherwise empty.
     */
    @Override
    public Optional<WorkspaceType> findById(Long id) {
        String sqlFindById = """
                SELECT * FROM develop.workspace_type WHERE id = ?
                """;
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlFindById)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next() ?
                    Optional.of(buildWorkspaceType(resultSet))
                    : Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Retrieves all workspace types.
     *
     * @return A list of all workspace types.
     */
    @Override
    public List<WorkspaceType> findAll() {
        String sqlFindAll = """
                SELECT * FROM develop.workspace_type
                """;
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlFindAll)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<WorkspaceType> workspaceTypes = new ArrayList<>();
            while (resultSet.next()) {
                workspaceTypes.add(buildWorkspaceType(resultSet));
            }
            return workspaceTypes;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    /**
     * Saves a new workspace type.
     *
     * @param entity The WorkspaceType object to save.
     * @return The saved WorkspaceType object.
     */
    @Override
    public WorkspaceType save(WorkspaceType entity) {
        String sqlSave = """
                INSERT INTO develop.workspace_type (type_name) VALUES (?)
                """;

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlSave, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getTypeName());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Failed to save workspace type");
            }

            ResultSet keys = preparedStatement.getGeneratedKeys();
            if (keys.next()) {
                entity.setId(keys.getLong(1));
            } else {
                throw new RuntimeException("Failed to save workspace type");
            }

            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save workspace type", e);
        }
    }

    /**
     * Constructs a Workspace type object from the given ResultSet.
     *
     * This method extracts the workspace type details from the provided ResultSet and
     * constructs a Workspace type object using the extracted data.
     *
     * @param resultSet The ResultSet containing the workspace data retrieved from the database.
     * @return A Workspace object with data from the ResultSet.
     */
    private WorkspaceType buildWorkspaceType(ResultSet resultSet) {
        try {
            return new WorkspaceType(resultSet.getLong("id"),resultSet.getString("type_name"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to build workspace type", e);
        }
    }
}

