package ru.polskiy.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.polskiy.dao.UserDao;
import ru.polskiy.dto.WorkspaceRequest;
import ru.polskiy.exception.AuthorizeException;
import ru.polskiy.exception.UserNotFoundException;
import ru.polskiy.model.entity.User;
import ru.polskiy.model.entity.Workspace;
import ru.polskiy.service.WorkspaceService;
import ru.polskiy.util.SecurityUtils;

import java.util.List;
import java.util.Optional;

/**
 * Controller for managing workspace-related operations.
 * Provides endpoints for retrieving and submitting workspaces.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/workspace")
@Api(value = "Workspace Controller")
public class WorkspaceController {

    private final WorkspaceService workspaceService;
    private final UserDao userDao;

    /**
     * Retrieves the current workspaces for a user by login.
     *
     * @param login The login identifier of the user.
     * @return ResponseEntity containing a list of current Workspace objects.
     * @throws AuthorizeException if the login is invalid.
     */
    @ApiOperation(value = "Get current workspaces", response = List.class)
    @GetMapping("/current")
    public ResponseEntity<List<Workspace>> getCurrentWorkspaces(
            @RequestParam String login) {
        if (!SecurityUtils.isValidLogin(login)) throw new AuthorizeException("Incorrect login!");
        Long id = getIdByLogin(login);
        return ResponseEntity.ok(workspaceService.getCurrentWorkspaces(id));
    }

    /**
     * Retrieves workspaces by a specific date.
     *
     * @param year  The year to filter workspaces.
     * @param month The month to filter workspaces.
     * @param day   The day to filter workspaces.
     * @return ResponseEntity containing a list of Workspace objects for the specified date.
     */
    @ApiOperation(value = "Get workspaces by date", response = List.class)
    @GetMapping("/date")
    public ResponseEntity<List<Workspace>> showWorkspacesByDate(
            @RequestParam Integer year,
            @RequestParam Integer month,
            @RequestParam Integer day) {
        List<Workspace> workspacesByDate = workspaceService.getWorkspacesByDate(year, month, day);
        return ResponseEntity.ok(workspacesByDate);
    }

    /**
     * Submits a workspace for a user.
     *
     * @param request The request object containing workspace details.
     * @param login   The login identifier of the user.
     * @return ResponseEntity containing a success message.
     * @throws AuthorizeException if the login is invalid.
     */
    @ApiOperation(value = "submit workspace", response = String.class)
    @PostMapping("/submit")
    public ResponseEntity<String> submitWorkspace(
            @RequestBody WorkspaceRequest request,
            @RequestParam String login) {
        if (!SecurityUtils.isValidLogin(login)) throw new AuthorizeException("Incorrect login!");
        Long id = getIdByLogin(login);
        workspaceService.submitWorkspace(id, request.workspaceTypeId(), request.workspaceId());
        return ResponseEntity.ok("The workspace was successfully saved!");
    }

    /**
     * Retrieves a list of available workspaces.
     *
     * @return ResponseEntity containing a list of available Workspace objects.
     */
    @ApiOperation(value = "getting not booked  workspaces", response = String.class)
    @GetMapping
    public ResponseEntity<List<Workspace>> getAvailableWorkspaces() {
        return ResponseEntity.ok(workspaceService.getAvailableWorkspaces());
    }

    /**
     * Helper method to get user ID by login.
     *
     * @param login The login identifier of the user.
     * @return The user ID.
     * @throws UserNotFoundException if the user is not found.
     */
    private Long getIdByLogin(String login) {
        Optional<User> userOptional = userDao.findByLogin(login);
        if (userOptional.isPresent()) {
            return userOptional.get().getId();
        } else {
            throw new UserNotFoundException("User not found for login: " + login);
        }
    }
}