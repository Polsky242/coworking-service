package ru.polskiy.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing a user with associated workspaces.
 */
@Getter
@Setter
public class UserWorkspaceDto {

    /**
     * The UserDto representing the user details.
     */
    private UserDto userDto;

    /**
     * The list of WorkspaceDto objects representing the workspaces associated with the user.
     */
    private List<WorkspaceDto> workspaceDtos;
}