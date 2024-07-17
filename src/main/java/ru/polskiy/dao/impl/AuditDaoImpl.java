package ru.polskiy.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.polskiy.dao.AuditDao;
import ru.polskiy.model.entity.Audit;
import ru.polskiy.model.type.ActionType;
import ru.polskiy.model.type.AuditStatus;
import ru.polskiy.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link AuditDao} interface.
 * Provides methods for performing CRUD operations on audit records.
 * This class interacts with the database to manage audit information.
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class AuditDaoImpl implements AuditDao {

    private final ConnectionManager connectionProvider;

    /**
     * Retrieves an Audit entity by its ID.
     *
     * @param id The ID of the Audit entity to retrieve.
     * @return An Optional containing the found Audit entity, or an empty Optional if not found.
     */
    @Override
    public Optional<Audit> findById(Long id) {
        String sqlFindById = """
                SELECT * FROM develop.audit WHERE id = ?""";

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlFindById)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next() ?
                    Optional.of(buildAudit(resultSet))
                    : Optional.empty();
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    /**
     * Retrieves a list of all Audit entities.
     *
     * @return A list containing all Audit entities stored in the in-memory map.
     */
    @Override
    public List<Audit> findAll() {
        String sqlFindAll = """
                SELECT * FROM develop.audit;
                """;

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlFindAll)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Audit> audits = new ArrayList<>();

            while (resultSet.next()) {
                audits.add(buildAudit(resultSet));
            }

            return audits;
        } catch (SQLException e) {
            log.error("Ошибка при выполнении SQL-запроса: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Saves an Audit entity to the in-memory storage.
     *
     * @param audit The Audit entity to save.
     * @return The saved Audit entity with an assigned ID.
     */
    @Override
    public Audit save(Audit audit) {
        String sqlSave = """
                INSERT INTO develop.audit(login, audit_status, action_type)
                VALUES (?,?,?);
                """;

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlSave, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setObject(1, audit.getLogin());
            preparedStatement.setObject(2, audit.getAuditStatus(), Types.OTHER);
            preparedStatement.setObject(3, audit.getActionType(), Types.OTHER);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                    if (keys.next()) {
                        audit.setId(keys.getObject(1, Long.class));
                    }
                } catch (SQLException e) {
                    log.error("Error getting generated key: " + e.getMessage());
                }
            } else {
                log.error("SQL request error.");
            }

            return audit;
        } catch (SQLException e) {
            log.error("SQL request error " + e.getMessage());
        }
        return null;
    }

    /**
     * Updates an audit record in the database.
     *
     * @param audit The audit object containing the updated data.
     */
    public void update(Audit audit) {
        String sqlUpdate = """
                UPDATE develop.audit
                SET login = ?, audit_status = ?, action_type = ?
                WHERE id = ?;
                """;

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate)) {

            preparedStatement.setObject(1, audit.getLogin());
            preparedStatement.setObject(2, audit.getAuditStatus(), Types.OTHER);
            preparedStatement.setObject(3, audit.getActionType(), Types.OTHER);
            preparedStatement.setLong(4, audit.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("SQL request error: " + e.getMessage());
        }
    }

    /**
     * Deletes an audit record from the database based on the given id.
     *
     * @param id The identifier of the audit record to delete.
     * @return true if deletion was successful, otherwise false.
     */
    public boolean delete(Long id) {
        String sqlDelete = """
                DELETE FROM develop.audit WHERE id = ?;
                """;

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("SQL request error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes all audit records from the database.
     */
    public void deleteAll() {
        String sqlDeleteAll = """
                DELETE FROM develop.audit;
                """;

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlDeleteAll)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("SQL request error " + e.getMessage());
        }
    }

    /**
     * Builds an Audit object based on data from the ResultSet.
     *
     * @param resultSet The ResultSet containing data to build the Audit object.
     * @return An Audit object built from the ResultSet data.
     * @throws SQLException if an error occurs while accessing the ResultSet.
     */
    private Audit buildAudit(ResultSet resultSet) throws SQLException {
        String auditStatusString = resultSet.getString("audit_status");
        AuditStatus auditStatus = AuditStatus.valueOf(auditStatusString);

        String actionTypeString = resultSet.getString("action_type");
        ActionType actionType = ActionType.valueOf(actionTypeString);

        return Audit.builder()
                .id(resultSet.getLong("id"))
                .auditStatus(auditStatus)
                .actionType(actionType)
                .login(resultSet.getString("login"))
                .build();
    }
}
