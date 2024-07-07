package ru.polskiy.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Data transfer object (DTO) representing workspace information.
 * Contains start and end date along with the type ID.
 */
@Data
public class WorkspaceDto {

    /**
     * The start date of the workspace.
     */
    private LocalDateTime startDate;

    /**
     * The end date of the workspace.
     */
    private LocalDateTime endDate;

    /**
     * The ID of the workspace type.
     */
    private Long typeId;

    // Constructors, getters, setters, and other methods can be added here if needed
}
