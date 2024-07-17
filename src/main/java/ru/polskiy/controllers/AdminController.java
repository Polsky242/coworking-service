package ru.polskiy.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.polskiy.dto.WorkspaceDto;
import ru.polskiy.dto.WorkspaceTypeRequest;
import ru.polskiy.mapper.WorkspaceMapper;
import ru.polskiy.model.entity.User;
import ru.polskiy.model.entity.Workspace;
import ru.polskiy.model.entity.WorkspaceType;
import ru.polskiy.service.UserService;
import ru.polskiy.service.WorkspaceService;
import ru.polskiy.service.WorkspaceTypeService;

import java.util.List;


/**
 * Controller for managing administrative operations.
 * Provides endpoints for managing users, workspace types, and workspaces.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@Api(value = "Admin Controller")
public class AdminController {

    private final UserService userService;
    private final WorkspaceTypeService workspaceTypeService;
    private final WorkspaceService workspaceService;
    private final WorkspaceMapper workspaceMapper;

    /**
     * Retrieves a list of all users.
     *
     * @return ResponseEntity containing a list of User objects.
     */
    @ApiOperation(value = "View a list of all users", response = List.class)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<User>> showAllUsers() {
        List<User> allUsers = userService.showAll();
        return ResponseEntity.ok(allUsers);
    }

    /**
     * Adds a new workspace type.
     *
     * @param request The WorkspaceTypeRequest object containing information about the new workspace type.
     * @return ResponseEntity containing the saved WorkspaceType object.
     */
    @ApiOperation(value = "Add a new workspace type", response = WorkspaceType.class)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/workspace-type/add")
    public ResponseEntity<WorkspaceType> addWorkspaceType(@RequestBody WorkspaceTypeRequest request) {
        WorkspaceType savedType = workspaceTypeService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedType);
    }

    /**
     * Adds a new workspace.
     *
     * @param request The WorkspaceDto object containing information about the new workspace.
     * @return ResponseEntity indicating success or failure of workspace addition.
     */
    @ApiOperation(value = "Add a new workspace")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/workspace/add")
    public ResponseEntity<Void> addWorkspace(@RequestBody WorkspaceDto request) {
        Workspace workspace = workspaceMapper.toEntity(request);
        workspaceService.addWorkspace(workspace);
        return ResponseEntity.ok().build();
    }

    /**
     * Retrieves a list of booked workspaces.
     *
     * @return ResponseEntity containing a list of booked Workspace objects.
     */
    @ApiOperation(value = "get booked workspaces", response = List.class)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/workspace/booked")
    public ResponseEntity<List<Workspace>> getBookedWorkspaces() {
        return ResponseEntity.ok(workspaceService.getBookedWorkSpaces());
    }

    /**
     * Retrieves a list of all workspaces.
     *
     * @return ResponseEntity containing a list of all Workspace objects.
     */
    @ApiOperation(value = "get all workspaces", response = List.class)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping({"/workspaces", "/all"})
    public ResponseEntity<List<Workspace>> getAllWorkspaces() {
        return ResponseEntity.ok(workspaceService.getAllWorkSpaces());
    }
}