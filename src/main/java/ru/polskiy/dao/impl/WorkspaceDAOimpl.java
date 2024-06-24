package ru.polskiy.dao.impl;

import ru.polskiy.dao.WorkspaceDAO;
import ru.polskiy.exception.NoSuchWorkspaceException;
import ru.polskiy.model.entity.Workspace;

import java.rmi.NoSuchObjectException;
import java.time.LocalDateTime;
import java.util.*;

import java.util.*;

/**
 * Implementation of the WorkspaceDAO interface that manages workspace data using a HashMap.
 */
public class WorkspaceDAOimpl implements WorkspaceDAO {

    private Long id = 1L;

    private final Map<Long, Workspace> workSpaces = new HashMap<>();

    /**
     * Constructs a WorkspaceDAOimpl object.
     */
    public WorkspaceDAOimpl() {
    }

    /**
     * Retrieves a workspace by its ID.
     *
     * @param id The ID of the workspace to retrieve.
     * @return An Optional containing the workspace if found, otherwise empty.
     */
    @Override
    public Optional<Workspace> findById(Long id) {
        return Optional.ofNullable(workSpaces.get(id));
    }

    /**
     * Retrieves all workspaces stored in the DAO.
     *
     * @return A list of all workspaces.
     */
    @Override
    public List<Workspace> findAll() {
        return new ArrayList<>(workSpaces.values());
    }

    /**
     * Saves a new workspace or updates an existing workspace in the DAO.
     *
     * @param entity The Workspace object to save or update.
     * @return The saved or updated Workspace object.
     */
    @Override
    public Workspace save(Workspace entity) {
        entity.setId(id++);
        workSpaces.put(entity.getId(), entity);
        return workSpaces.get(entity.getId());
    }

    /**
     * Deletes a workspace from the DAO.
     *
     * @param workspace The Workspace object to delete.
     * @throws NoSuchWorkspaceException If the specified workspace doesn't exist in the DAO.
     */
    @Override
    public void delete(Workspace workspace) {
        if (workSpaces.containsValue(workspace)) {
            workSpaces.remove(workspace.getId());
        } else {
            throw new NoSuchWorkspaceException(workspace.toString());
        }
    }

    /**
     * Updates an existing workspace in the DAO.
     *
     * @param workspace The updated Workspace object.
     * @return The updated Workspace object.
     * @throws IllegalArgumentException If the workspace with the provided ID doesn't exist.
     */
    @Override
    public Workspace update(Workspace workspace) {
        Long workspaceId = workspace.getId();
        if (workSpaces.containsKey(workspaceId)) {
            workSpaces.put(workspaceId, workspace);
            return workSpaces.get(workspaceId);
        } else {
            throw new IllegalArgumentException("Workspace with id:" + workspaceId + " doesn't exist");
        }
    }
}
