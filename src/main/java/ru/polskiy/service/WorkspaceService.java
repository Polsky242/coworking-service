package ru.polskiy.service;

import ru.polskiy.model.entity.Workspace;

import java.util.List;

public interface WorkspaceService {

    List<Workspace> getAvailableWorkspaces();

    List<Workspace> getBookedWorkSpaces();

    List<Workspace> getAllWorkSpaces();

    Boolean deleteWorkSpaceById(Long id);

    Workspace updateWorkspace(Workspace workspace);

    void submitWorkspace(Long userId, Long WorkspaceTypeId, Long workspaceId);

    List<Workspace> getCurrentWorkspaces(Long userId);

    List<Workspace> getWorkspacesByDate(Integer year, Integer month, Integer day, Integer hours, Integer minutes);
    List<Workspace> getWorkspacesByDate(Integer year, Integer month, Integer day);

    void cancelBook(Long userId, Workspace workspace);

    void addWorkspace(Workspace workspace);
}
