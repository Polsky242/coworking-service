package ru.polskiy.service;

import ru.polskiy.dao.WorkspaceDAO;
import ru.polskiy.model.entity.Workspace;

import java.util.List;

public interface WorkspaceService {

    List<Workspace> getAvailableWorkspaces();

    List<Workspace> getBookedWorkSpaces();

    void deleteWorkSpaceById(Long id);

    Workspace updateWorkspace(Workspace workspace);

    void submitWorkspace(Long userId, Long WorkspaceTypeId, Long workspaceId);

    List<Workspace> getCurrentWorkspaces(Long userId);

    List<Workspace> getWorkspacesByDate(Integer year,Integer month, Integer day);


}
