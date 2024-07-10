package ru.polskiy.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Represents a Workspace entity extending BaseEntity, with fields for workspace details.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Workspace extends BaseEntity {

    /**
     * Constructs a Workspace object with specified id, user id, start date, end date, and type id.
     *
     * @param id        The unique identifier for the workspace.
     * @param userId    The identifier of the user associated with the workspace.
     * @param startDate The date and time when the workspace starts.
     * @param endDate   The date and time when the workspace ends.
     * @param typeId    The identifier of the type associated with the workspace.
     */
    public Workspace(Long id, Long userId, LocalDateTime startDate, LocalDateTime endDate, Long typeId) {
        super(id);
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.typeId = typeId;
    }

    /**
     * The identifier of the user associated with the workspace.
     */
    @Builder.Default
    private Long userId = null;

    /**
     * The date and time when the workspace starts.
     */
    private LocalDateTime startDate;

    /**
     * The date and time when the workspace ends.
     */
    private LocalDateTime endDate;

    /**
     * The identifier of the type associated with the workspace.
     */
    private Long typeId;
}
