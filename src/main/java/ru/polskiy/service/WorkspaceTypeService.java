package ru.polskiy.service;

import ru.polskiy.dto.WorkspaceTypeRequest;
import ru.polskiy.model.entity.WorkspaceType;

import java.util.List;

/**
 * Interface representing a workspace type service.
 * This service provides methods for managing and retrieving workspace types.
 */
public interface WorkspaceTypeService {

    /**
     * Retrieves a list of available workspace types.
     *
     * @return A list of available workspace types.
     */
    List<WorkspaceType> showAvailableWorkspaceTypes();

    /**
     * Saves a new workspace type based on the provided request.
     *
     * @param request The request object containing the details of the workspace type to be saved.
     * @return The saved workspace type.
     */
    WorkspaceType save(WorkspaceTypeRequest request);
}

