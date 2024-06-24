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

import lombok.RequiredArgsConstructor;

/**
 * Controller class that manages operations related to security, workspaces, workspace types, and users.
 */
@RequiredArgsConstructor
public class MainController {

    private final SecurityService securityService;
    private final WorkspaceService workspaceService;
    private final WorkspaceTypeService workspaceTypeService;
    private final UserService userService;

    /**
     * Registers a new user with the provided login and password.
     *
     * @param login    The login username for the new user.
     * @param password The password for the new user.
     * @return The registered User object.
     */
    public User register(String login, String password) {
        return securityService.register(login, password);
    }

    /**
     * Authorizes a user with the provided login and password.
     *
     * @param login    The login username of the user to authorize.
     * @param password The password of the user to authorize.
     * @return An Optional containing the authorized User object, if authorization is successful.
     */
    public Optional<User> authorize(String login, String password) {
        return securityService.authorize(login, password);
    }

    /**
     * Retrieves current workspaces associated with a specific user.
     *
     * @param userId The ID of the user whose current workspaces are to be retrieved.
     * @return A list of current Workspace objects associated with the user.
     */
    public List<Workspace> showCurrentWorkspaces(Long userId) {
        return workspaceService.getCurrentWorkspaces(userId);
    }

    /**
     * Retrieves all available workspaces.
     *
     * @return A list of all available Workspace objects.
     */
    public List<Workspace> showAvailableWorkspaces() {
        return workspaceService.getAvailableWorkspaces();
    }

    /**
     * Retrieves all available workspace types.
     *
     * @return A list of all available WorkspaceType objects.
     */
    public List<WorkspaceType> showWorkspaceTypes() {
        return workspaceTypeService.showAvailableWorkspaceTypes();
    }

    /**
     * Adds a new workspace type.
     *
     * @param workspaceType The WorkspaceType object to be added.
     */
    public void addNewWorkspaceType(WorkspaceType workspaceType) {
        workspaceTypeService.save(workspaceType);
    }

    /**
     * Retrieves all users.
     *
     * @return A list of all User objects.
     */
    public List<User> showAllUsers() {
        return userService.showAll();
    }

    /**
     * Retrieves workspaces based on a specific date and time.
     *
     * @param year    The year of the date.
     * @param month   The month of the date.
     * @param day     The day of the date.
     * @param hours   The hours of the time.
     * @param minutes The minutes of the time.
     * @return A list of Workspace objects that match the specified date and time.
     */
    public List<Workspace> getWorkspaceByDate(Integer year, Integer month, Integer day, Integer hours, Integer minutes) {
        return workspaceService.getWorkspacesByDate(year, month, day, hours, minutes);
    }

    /**
     * Submits a workspace with the specified user ID, workspace type ID, and workspace ID.
     *
     * @param userId          The ID of the user submitting the workspace.
     * @param workspaceTypeId The ID of the workspace type.
     * @param workspaceId     The ID of the workspace to be submitted.
     */
    public void submitWorkspace(Long userId, Long workspaceTypeId, Long workspaceId) {
        workspaceService.submitWorkspace(userId, workspaceTypeId, workspaceId);
    }

    /**
     * Retrieves workspaces based on a specific date.
     *
     * @param year  The year of the date.
     * @param month The month of the date.
     * @param day   The day of the date.
     * @return A list of Workspace objects that match the specified date.
     */
    public List<Workspace> getWorkspaceByDate(Integer year, Integer month, Integer day) {
        return workspaceService.getWorkspacesByDate(year, month, day);
    }

    /**
     * Cancels booking of a workspace by a user.
     *
     * @param userId    The ID of the user canceling the booking.
     * @param workspace The Workspace object to cancel the booking for.
     */
    public void bookCancel(Long userId, Workspace workspace) {
        workspaceService.cancelBook(userId, workspace);
    }

    /**
     * Retrieves all workspaces.
     *
     * @return A list of all Workspace objects.
     */
    public List<Workspace> showAllWorkspaces() {
        return workspaceService.getAllWorkSpaces();
    }

    /**
     * Adds a new workspace.
     *
     * @param workspace The Workspace object to be added.
     */
    public void addWorkspace(Workspace workspace) {
        workspaceService.addWorkspace(workspace);
    }

    /**
     * Deletes a workspace by its ID.
     *
     * @param id The ID of the workspace to be deleted.
     */
    public void deleteWorkspace(Long id) {
        workspaceService.deleteWorkSpaceById(id);
    }

    /**
     * Updates a workspace with new details.
     *
     * @param workspace The updated Workspace object.
     * @return The updated Workspace object.
     */
    public Workspace updateWorkspace(Workspace workspace) {
        return workspaceService.updateWorkspace(workspace);
    }
}

