package ru.polskiy.service.impl;

import lombok.RequiredArgsConstructor;
import ru.polskiy.dao.WorkspaceDAO;
import ru.polskiy.exception.NoSuchWorkspaceException;
import ru.polskiy.model.entity.Workspace;
import ru.polskiy.service.WorkspaceService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class WorkspaceServiceImpl implements WorkspaceService {

    private final WorkspaceDAO workspaceDAO;

    @Override
    public List<Workspace> getAvailableWorkspaces() {
        List<Workspace> all = workspaceDAO.findAll();
        return all.stream()
                .filter(workspace -> !workspace.getIsBooked())
                .collect(Collectors.toList());
    }

    @Override
    public List<Workspace> getBookedWorkSpaces() {
        List<Workspace> all = workspaceDAO.findAll();
        return all.stream()
                .filter(Workspace::getIsBooked)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteWorkSpaceById(Long id) {
        List<Workspace> all = workspaceDAO.findAll();
        Workspace workspace = all.get(id.intValue());
        if (workspace==null){
            throw new IllegalArgumentException();//TODO write exception
        }else{
            workspaceDAO.delete(workspace);
        }


    }

    @Override
    public Workspace updateWorkspace(Workspace workspace) {
        return workspaceDAO.update(workspace);
    }

    @Override
    public void submitWorkspace(Long userId, Long WorkspaceTypeId, Long workspaceId) {

    }

    @Override
    public List<Workspace> getCurrentWorkspaces(Long userId) {
        return null;
    }

    @Override
    public List<Workspace> getWorkspacesByDate(Integer year, Integer month, Integer day) {
        return null;
    }


}
