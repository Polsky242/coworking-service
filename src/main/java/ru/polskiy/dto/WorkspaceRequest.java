package ru.polskiy.dto;

/**
 * Represents a request DTO for workspace operations, containing identifiers for workspace type, workspace, and user.
 */
public record WorkspaceRequest(
        Long workspaceTypeId,

        Long workspaceId,

        Long userId
) {
}
