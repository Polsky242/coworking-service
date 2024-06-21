package ru.polskiy.dao.impl;

import ru.polskiy.dao.WorkspaceTypeDAO;
import ru.polskiy.model.entity.WorkspaceType;

import java.util.*;

public class WorkspaceTypeDAOimpl implements WorkspaceTypeDAO {

    private final Map<Long, WorkspaceType> workspaceTypes = new HashMap<>();

    private Long id = 1L;

    public WorkspaceTypeDAOimpl() {
        save(WorkspaceType.builder().typeName("ConferenceRoom").build());
        save(WorkspaceType.builder().typeName("WorkspaceRoom").build());
    }

    @Override
    public Optional<WorkspaceType> findById(Long id) {
        return Optional.ofNullable(workspaceTypes.get(id));
    }

    @Override
    public List<WorkspaceType> findAll() {
        return new ArrayList<>(workspaceTypes.values());
    }

    @Override
    public WorkspaceType save(WorkspaceType entity) {
        entity.setId(id++);
        workspaceTypes.put(entity.getId(), entity);
        return workspaceTypes.get(entity.getId());
    }
}
