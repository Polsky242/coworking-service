package ru.polskiy.service;

import ru.polskiy.model.entity.Workspace;

import java.util.List;

/**
 * Interface representing a workspace service.
 * This service provides methods for managing and retrieving workspace information.
 */
public interface WorkspaceService {

    /**
     * Retrieves a list of available workspaces.
     *
     * @return A list of available workspaces.
     */
    List<Workspace> getAvailableWorkspaces();

    /**
     * Retrieves a list of booked workspaces.
     *
     * @return A list of booked workspaces.
     */
    List<Workspace> getBookedWorkSpaces();

    /**
     * Retrieves a list of all workspaces.
     *
     * @return A list of all workspaces.
     */
    List<Workspace> getAllWorkSpaces();

    /**
     * Deletes a workspace by its unique ID.
     *
     * @param id The unique ID of the workspace to be deleted.
     * @return A Boolean indicating whether the deletion was successful.
     */
    Boolean deleteWorkSpaceById(Long id);

    /**
     * Updates the details of an existing workspace.
     *
     * @param workspace The workspace object containing updated details.
     * @return The updated workspace object.
     */
    Workspace updateWorkspace(Workspace workspace);

    /**
     * Submits a workspace for a user.
     *
     * @param userId The unique ID of the user.
     * @param workspaceTypeId The unique ID of the workspace type.
     * @param workspaceId The unique ID of the workspace.
     */
    void submitWorkspace(Long userId, Long workspaceTypeId, Long workspaceId);

    /**
     * Retrieves the current workspaces for a user.
     *
     * @param userId The unique ID of the user.
     * @return A list of current workspaces for the user.
     */
    List<Workspace> getCurrentWorkspaces(Long userId);

    /**
     * Retrieves workspaces for a specific date and time.
     *
     * @param year The year of the date.
     * @param month The month of the date.
     * @param day The day of the date.
     * @param hours The hours of the time.
     * @param minutes The minutes of the time.
     * @return A list of workspaces available on the specified date and time.
     */
    List<Workspace> getWorkspacesByDate(Integer year, Integer month, Integer day, Integer hours, Integer minutes);

    /**
     * Retrieves workspaces for a specific date.
     *
     * @param year The year of the date.
     * @param month The month of the date.
     * @param day The day of the date.
     * @return A list of workspaces available on the specified date.
     */
    List<Workspace> getWorkspacesByDate(Integer year, Integer month, Integer day);

    /**
     * Cancels a workspace booking for a user.
     *
     * @param userId The unique ID of the user.
     * @param workspace The workspace to be canceled.
     */
    void cancelBook(Long userId, Workspace workspace);

    /**
     * Adds a new workspace.
     *
     * @param workspace The workspace object to be added.
     */
    void addWorkspace(Workspace workspace);
}
