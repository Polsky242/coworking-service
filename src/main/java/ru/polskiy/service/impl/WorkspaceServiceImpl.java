package ru.polskiy.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.polskiy.annotations.Auditable;
import ru.polskiy.dao.WorkspaceDao;
import ru.polskiy.exception.DuplicateException;
import ru.polskiy.exception.NoSuchWorkspaceException;
import ru.polskiy.model.entity.Workspace;
import ru.polskiy.model.type.ActionType;
import ru.polskiy.service.WorkspaceService;
import ru.polskiy.service.WorkspaceTypeService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkspaceServiceImpl implements WorkspaceService {

    private final WorkspaceDao workspaceDAO;

    @Override
    public List<Workspace> getAvailableWorkspaces() {
        List<Workspace> all = workspaceDAO.findAll();
        return all.stream()
                .filter(workspace -> workspace.getUserId() == 0)
                .filter(workspace -> workspace.getStartDate().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Workspace> getBookedWorkSpaces() {
        List<Workspace> all = workspaceDAO.findAll();
        return all.stream()
                .filter(workspace -> workspace.getUserId() != 0)
                .collect(Collectors.toList());
    }

    @Override
    public List<Workspace> getAllWorkSpaces() {
        return workspaceDAO.findAll();
    }

    @Override
    public Boolean deleteWorkSpaceById(Long id) {
        Optional<Workspace> workspace = workspaceDAO.findById(id);
        if (workspace.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return workspaceDAO.delete(id);

    }

    @Override
    public Workspace updateWorkspace(Workspace workspace) {
        workspace.onUpdate();
        return workspaceDAO.update(workspace);
    }

    @Override
    @Auditable(actionType = ActionType.SUBMIT_WORKSPACE, userId = "@userId")
    public void submitWorkspace(Long userId, Long workspaceTypeId, Long workspaceId) {
        Optional<Workspace> optionalWorkspace = workspaceDAO.findById(workspaceId);
        if (optionalWorkspace.isEmpty()) {
            throw new NoSuchWorkspaceException("with id:" + workspaceId);
        }
        Workspace workspace = optionalWorkspace.get();
        if (workspace.getUserId() != 0) {
            throw new DuplicateException(workspace.toString());
        }
        workspace.setUserId(userId);
        workspace.setTypeId(workspaceTypeId);
        workspaceDAO.update(workspace);
    }

    @Override
    @Auditable(actionType = ActionType.GETTING_WORKSPACES, userId = "@userId")
    public List<Workspace> getCurrentWorkspaces(Long userId) {
        List<Workspace> workspaces = workspaceDAO.findAll();

        Map<Long, List<Workspace>> workspaceByType = workspaces.stream()
                .collect(Collectors.groupingBy(Workspace::getTypeId));

        List<Workspace> lastWorkspaces = new ArrayList<>();

        for (List<Workspace> workspaceList : workspaceByType.values()) {
            if (!workspaceList.isEmpty()) {
                Workspace lastWorkspace = workspaces.get(workspaces.size() - 1);
                lastWorkspaces.add(lastWorkspace);
            }
        }
        return lastWorkspaces;
    }

    @Override
    @Auditable(actionType = ActionType.GETTING_WORKSPACES, userId = "@userId")
    public List<Workspace> getWorkspacesByDate(Integer year, Integer month, Integer day, Integer hours, Integer minutes) {
        return getWorkspacesByDate(year, month, day).stream()
                .filter(entity -> entity.getStartDate().getHour() == hours)
                .filter(entity -> entity.getStartDate().getMinute() == minutes)
                .collect(Collectors.toList());
    }

    @Override
    @Auditable(actionType = ActionType.GETTING_WORKSPACES, userId = "@userId")
    public List<Workspace> getWorkspacesByDate(Integer year, Integer month, Integer day) {
        return getAvailableWorkspaces().stream()
                .filter(entity -> entity.getStartDate().getYear() == year)
                .filter(entity -> entity.getStartDate().getMonth().getValue() == month)
                .filter(entity -> entity.getStartDate().getDayOfMonth() == day)
                .collect(Collectors.toList());
    }

    @Override
    @Auditable(actionType = ActionType.CANCEL_WORKSPACE, userId = "@userId")
    public void cancelBook(Long userId, Workspace workspace) {
        List<Workspace> allUserWorkspaces = getCurrentWorkspaces(userId);
        for (Workspace entity : allUserWorkspaces) {
            if (entity.equals(workspace)) {
                entity.setUserId(0L);
                break;
            }
        }
    }

    @Override
    public void addWorkspace(Workspace workspace) {
        workspace.onCreate();
        workspaceDAO.save(workspace);
    }
}
