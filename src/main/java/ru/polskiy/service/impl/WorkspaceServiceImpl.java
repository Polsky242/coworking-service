package ru.polskiy.service.impl;

import lombok.RequiredArgsConstructor;
import ru.polskiy.dao.WorkspaceDAO;
import ru.polskiy.exception.NoSuchWorkspaceException;
import ru.polskiy.model.entity.Workspace;
import ru.polskiy.service.WorkspaceService;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class WorkspaceServiceImpl implements WorkspaceService {

    private final WorkspaceDAO workspaceDAO;

    @Override
    public List<Workspace> getAvailableWorkspaces() {
        List<Workspace> all = workspaceDAO.findAll();
        return all.stream()
                .filter(workspace -> workspace.getUserId()==null)
                .collect(Collectors.toList());
    }

    @Override
    public List<Workspace> getBookedWorkSpaces() {
        List<Workspace> all = workspaceDAO.findAll();
        return all.stream()
                .filter(workspace -> workspace.getUserId()!=null)
                .collect(Collectors.toList());
    }

    @Override
    public List<Workspace> getAllWorkSpaces() {
        return workspaceDAO.findAll();
    }

    @Override
    public void deleteWorkSpaceById(Long id) {
        List<Workspace> all = workspaceDAO.findAll();
        Workspace workspace = all.get(id.intValue());
        if (workspace==null){
            throw new IllegalArgumentException();
        }else{
            workspaceDAO.delete(workspace);
        }
    }

    @Override
    public Workspace updateWorkspace(Workspace workspace) {
        return workspaceDAO.update(workspace);
    }

    @Override
    public void submitWorkspace(Long userId, Long workspaceTypeId, Long workspaceId) {
        Optional<Workspace> optionalWorkspace = workspaceDAO.findById(workspaceId);
        if(optionalWorkspace.isEmpty()){
            throw new NoSuchWorkspaceException("with id:"+workspaceId);
        }
        Workspace workspace = optionalWorkspace.get();
        workspace.setUserId(userId);
        workspace.setTypeId(workspaceTypeId);
    }

    @Override
    public List<Workspace> getCurrentWorkspaces(Long userId) {
        return workspaceDAO.findAll().stream()
                .filter(entity-> Objects.equals(entity.getUserId(), userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Workspace> getWorkspacesByDate(Integer year, Integer month, Integer day, Integer hours, Integer minutes) {
        return getAvailableWorkspaces().stream()
                .filter(entity -> entity.getStartDate().getYear() == year)
                .filter(entity -> entity.getStartDate().getMonth().getValue() == month)
                .filter(entity -> entity.getStartDate().getDayOfMonth() == day)
                .filter(entity-> entity.getStartDate().getHour()==hours)
                .filter(entity-> entity.getStartDate().getMinute()==minutes)
                .collect(Collectors.toList());
    }

    @Override
    public List<Workspace> getWorkspacesByDate(Integer year, Integer month, Integer day) {
        return getAvailableWorkspaces().stream()
                .filter(entity -> entity.getStartDate().getYear() == year)
                .filter(entity -> entity.getStartDate().getMonth().getValue() == month)
                .filter(entity -> entity.getStartDate().getDayOfMonth() == day)
                .collect(Collectors.toList());
    }

    @Override
    public void cancelBook(Long userId, Workspace workspace) {
        List<Workspace> allUserWorkspaces =getCurrentWorkspaces(userId);
        for (Workspace entity: allUserWorkspaces){
            if(entity.equals(workspace)){
                entity.setUserId(null);
            }
        }
    }

    @Override
    public void addWorkspace(Workspace workspace) {
        workspaceDAO.save(workspace);
    }
}
