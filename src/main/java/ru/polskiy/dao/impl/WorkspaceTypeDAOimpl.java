package ru.polskiy.dao.impl;

import ru.polskiy.dao.WorkspaceTypeDAO;
import ru.polskiy.model.entity.WorkspaceType;

import java.util.*;

import java.util.*;

/**
 * Implementation of the WorkspaceTypeDAO interface that manages workspace types using a HashMap.
 */
public class WorkspaceTypeDAOimpl implements WorkspaceTypeDAO {

    private final Map<Long, WorkspaceType> workspaceTypes = new HashMap<>();

    private Long id = 1L;

    /**
     * Constructs a WorkspaceTypeDAOimpl object and initializes it with predefined workspace types.
     */
    public WorkspaceTypeDAOimpl() {
        // Initializing with predefined workspace types during construction
        save(WorkspaceType.builder().typeName("ConferenceRoom").build());
        save(WorkspaceType.builder().typeName("WorkspaceRoom").build());
    }

    /**
     * Retrieves a workspace type by its ID.
     *
     * @param id The ID of the workspace type to retrieve.
     * @return An Optional containing the workspace type if found, otherwise empty.
     */
    @Override
    public Optional<WorkspaceType> findById(Long id) {
        return Optional.ofNullable(workspaceTypes.get(id));
    }

    /**
     * Retrieves all workspace types stored in the DAO.
     *
     * @return A list of all workspace types.
     */
    @Override
    public List<WorkspaceType> findAll() {
        return new ArrayList<>(workspaceTypes.values());
    }

    /**
     * Saves a new workspace type in the DAO.
     *
     * @param entity The WorkspaceType object to save.
     * @return The saved WorkspaceType object.
     */
    @Override
    public WorkspaceType save(WorkspaceType entity) {
        entity.setId(id++);
        workspaceTypes.put(entity.getId(), entity);
        return workspaceTypes.get(entity.getId());
    }
}

