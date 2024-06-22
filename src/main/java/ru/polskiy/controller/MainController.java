package ru.polskiy.controller;

import lombok.RequiredArgsConstructor;
import ru.polskiy.model.entity.User;
import ru.polskiy.model.entity.Workspace;
import ru.polskiy.model.entity.WorkspaceType;
import ru.polskiy.service.SecurityService;
import ru.polskiy.service.UserService;
import ru.polskiy.service.WorkspaceService;
import ru.polskiy.service.WorkspaceTypeService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class MainController {

    private final SecurityService securityService;
    private final WorkspaceService workspaceService;
    private final WorkspaceTypeService workspaceTypeService;
    private final UserService userService;

    public User register(String login, String password){
        return securityService.register(login,password);
    }

    public Optional<User> authorize(String login, String password){
        return securityService.authorize(login,password);
    }

    public List<Workspace> showCurrentWorkspaces(Long userId){
        return workspaceService.getCurrentWorkspaces(userId);
    }

    public List<Workspace> showAvailableWorkspaces(){
        return workspaceService.getAvailableWorkspaces();
    }

    public List<WorkspaceType> showWorkspaceTypes(){
        return workspaceTypeService.showAvailableWorkspaceTypes();
    }

    public void addNewWorkspaceType(WorkspaceType workspaceType){
        workspaceTypeService.save(workspaceType);
    }

    public List<User> showAllUsers(){
        return userService.showAll();
    }

    public List<Workspace> getWorkspaceByDate(Integer year, Integer month, Integer day,Integer hours, Integer minutes){
        return workspaceService.getWorkspacesByDate(year, month, day, hours, minutes);
    }

    public void submitWorkspace(Long userId, Long workspaceTypeId, Long workspaceId){
        workspaceService.submitWorkspace(userId, workspaceTypeId, workspaceId);
    }

    public List<Workspace> getWorkspaceByDate(Integer year, Integer month, Integer day) {
        return workspaceService.getWorkspacesByDate(year, month, day);
    }

    public void bookCancel(Long userId,Workspace workspace){
        workspaceService.cancelBook(userId, workspace);
    }
    public List<Workspace> showAllWorkspaces(){
        return workspaceService.getAllWorkSpaces();
    }

    public void addWorkspace(Workspace workspace){
         workspaceService.addWorkspace(workspace);
    }

}
