package ru.polskiy.dto;

/**
 * Represents a request DTO for creating or updating a workspace type, containing the type name.
 */
public record WorkspaceTypeRequest(
        String typeName
) {
}