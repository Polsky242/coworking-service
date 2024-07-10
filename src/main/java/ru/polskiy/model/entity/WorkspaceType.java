package ru.polskiy.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * Represents a WorkspaceType entity extending BaseEntity, with fields for workspace type details.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkspaceType extends BaseEntity {

    /**
     * Constructs a WorkspaceType object with specified id and type name.
     *
     * @param id       The unique identifier for the workspace type.
     * @param typeName The name of the workspace type.
     */
    public WorkspaceType(Long id, String typeName) {
        super(id);
        this.typeName = typeName;
    }

    /**
     * The name of the workspace type.
     */
    private String typeName;
}