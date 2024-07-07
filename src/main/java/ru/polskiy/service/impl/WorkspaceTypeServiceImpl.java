package ru.polskiy.service.impl;

import lombok.RequiredArgsConstructor;
import ru.polskiy.dao.WorkspaceTypeDao;
import ru.polskiy.dto.WorkspaceTypeRequest;
import ru.polskiy.model.entity.WorkspaceType;
import ru.polskiy.service.WorkspaceTypeService;

import java.util.List;

/**
 * Service implementation for managing workspace types.
 * This class provides methods to handle operations related to workspace types,
 * such as retrieving available workspace types and saving a new workspace type.
 */
@RequiredArgsConstructor
public class WorkspaceTypeServiceImpl implements WorkspaceTypeService {

    private final WorkspaceTypeDao workspaceTypeDAO;

    /**
     * Retrieves a list of all available workspace types.
     *
     * @return a list of available workspace types
     */
    @Override
    public List<WorkspaceType> showAvailableWorkspaceTypes() {
        return workspaceTypeDAO.findAll();
    }

    /**
     * Saves a new workspace type.
     * @param request the workspace type for saving
     *
     * @return the saved workspace type
     */
    @Override
    public WorkspaceType save(WorkspaceTypeRequest request) {
        WorkspaceType workspaceType = WorkspaceType.builder().typeName(request.typeName())
                .build();

        return workspaceTypeDAO.save(workspaceType);
    }
}
