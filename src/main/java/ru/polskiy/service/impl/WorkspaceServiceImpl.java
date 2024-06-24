package ru.polskiy.service.impl;

import lombok.RequiredArgsConstructor;
import ru.polskiy.dao.WorkspaceDAO;
import ru.polskiy.exception.DuplicateException;
import ru.polskiy.exception.NoSuchWorkspaceException;
import ru.polskiy.model.entity.Workspace;
import ru.polskiy.service.WorkspaceService;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation for managing workspaces.
 * This class provides methods to handle workspace operations such as retrieving,
 * updating, deleting, and managing workspace bookings.
 */
@RequiredArgsConstructor
public class WorkspaceServiceImpl implements WorkspaceService {

    private final WorkspaceDAO workspaceDAO;

    /**
     * Retrieves a list of available workspaces.
     * Available workspaces are those not currently booked by any user.
     *
     * @return a list of available workspaces
     */
    @Override
    public List<Workspace> getAvailableWorkspaces() {
        List<Workspace> all = workspaceDAO.findAll();
        return all.stream()
                .filter(workspace -> workspace.getUserId() == null)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of booked workspaces.
     * Booked workspaces are those currently assigned to a user.
     *
     * @return a list of booked workspaces
     */
    @Override
    public List<Workspace> getBookedWorkSpaces() {
        List<Workspace> all = workspaceDAO.findAll();
        return all.stream()
                .filter(workspace -> workspace.getUserId() != null)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of all workspaces.
     *
     * @return a list of all workspaces
     */
    @Override
    public List<Workspace> getAllWorkSpaces() {
        return workspaceDAO.findAll();
    }

    /**
     * Deletes a workspace by its ID.
     * Throws an IllegalArgumentException if the workspace with the specified ID is not found.
     *
     * @param id the ID of the workspace to delete
     */
    @Override
    public void deleteWorkSpaceById(Long id) {
        Optional<Workspace> workspace = workspaceDAO.findById(id);
        if (workspace.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            workspaceDAO.delete(workspace.get());
        }
    }

    /**
     * Updates an existing workspace.
     *
     * @param workspace the workspace to update
     * @return the updated workspace
     */
    @Override
    public Workspace updateWorkspace(Workspace workspace) {
        return workspaceDAO.update(workspace);
    }

    /**
     * Submits a workspace booking for a user.
     * Throws a NoSuchWorkspaceException if the workspace with the specified ID is not found.
     * Throws a DuplicateException if the workspace is already booked.
     *
     * @param userId          the ID of the user booking the workspace
     * @param workspaceTypeId the type ID of the workspace
     * @param workspaceId     the ID of the workspace to book
     */
    @Override
    public void submitWorkspace(Long userId, Long workspaceTypeId, Long workspaceId) {
        Optional<Workspace> optionalWorkspace = workspaceDAO.findById(workspaceId);
        if (optionalWorkspace.isEmpty()) {
            throw new NoSuchWorkspaceException("with id:" + workspaceId);
        }
        Workspace workspace = optionalWorkspace.get();
        if (workspace.getUserId() != null) {
            throw new DuplicateException(workspace.toString());
        }
        workspace.setUserId(userId);
        workspace.setTypeId(workspaceTypeId);
        workspaceDAO.save(workspace);
    }

    /**
     * Retrieves the current workspaces booked by a specific user.
     *
     * @param userId the ID of the user
     * @return a list of workspaces booked by the user
     */
    @Override
    public List<Workspace> getCurrentWorkspaces(Long userId) {
        return workspaceDAO.findAll().stream()
                .filter(entity -> Objects.equals(entity.getUserId(), userId))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves workspaces based on a specific date and time.
     *
     * @param year    the year of the date
     * @param month   the month of the date
     * @param day     the day of the date
     * @param hours   the hour of the time
     * @param minutes the minute of the time
     * @return a list of workspaces matching the specified date and time
     */
    @Override
    public List<Workspace> getWorkspacesByDate(Integer year, Integer month, Integer day, Integer hours, Integer minutes) {
        return getWorkspacesByDate(year, month, day).stream()
                .filter(entity -> entity.getStartDate().getHour() == hours)
                .filter(entity -> entity.getStartDate().getMinute() == minutes)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves workspaces based on a specific date.
     *
     * @param year  the year of the date
     * @param month the month of the date
     * @param day   the day of the date
     * @return a list of workspaces matching the specified date
     */
    @Override
    public List<Workspace> getWorkspacesByDate(Integer year, Integer month, Integer day) {
        return getAvailableWorkspaces().stream()
                .filter(entity -> entity.getStartDate().getYear() == year)
                .filter(entity -> entity.getStartDate().getMonth().getValue() == month)
                .filter(entity -> entity.getStartDate().getDayOfMonth() == day)
                .collect(Collectors.toList());
    }

    /**
     * Cancels a workspace booking for a user.
     *
     * @param userId    the ID of the user
     * @param workspace the workspace to cancel the booking for
     */
    @Override
    public void cancelBook(Long userId, Workspace workspace) {
        List<Workspace> allUserWorkspaces = getCurrentWorkspaces(userId);
        for (Workspace entity : allUserWorkspaces) {
            if (entity.equals(workspace)) {
                entity.setUserId(null);
                break;
            }
        }
    }

    /**
     * Adds a new workspace.
     *
     * @param workspace the workspace to add
     */
    @Override
    public void addWorkspace(Workspace workspace) {
        workspaceDAO.save(workspace);
    }
}
