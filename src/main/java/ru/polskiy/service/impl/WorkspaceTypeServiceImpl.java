package ru.polskiy.service.impl;

import lombok.RequiredArgsConstructor;
import ru.polskiy.dao.WorkspaceTypeDAO;
import ru.polskiy.model.entity.WorkspaceType;
import ru.polskiy.service.WorkspaceTypeService;

import java.util.List;

@RequiredArgsConstructor
public class WorkspaceTypeServiceImpl implements WorkspaceTypeService {

    private final WorkspaceTypeDAO workspaceTypeDAO;


    @Override
    public List<WorkspaceType> showAvailableWorkspaceTypes() {
        return workspaceTypeDAO.findAll();
    }

    @Override
    public WorkspaceType save(WorkspaceType workspaceType) {
        return workspaceTypeDAO.save(workspaceType);
    }
}
