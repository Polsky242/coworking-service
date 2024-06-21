package ru.polskiy.service;

import ru.polskiy.model.entity.WorkspaceType;

import java.util.List;

public interface WorkspaceTypeService {

    List<WorkspaceType> showAvailableWorkspaceTypes();

    WorkspaceType save(WorkspaceType workspaceType);
}
