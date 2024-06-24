package ru.polskiy.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * Abstract base class for entities, providing common fields and timestamp management.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class BaseEntity {

    /**
     * The unique identifier for the entity.
     */
    Long id;

    /**
     * The date and time when the entity was created.
     */
    LocalDateTime createdAt;

    /**
     * The date and time when the entity was last updated.
     */
    LocalDateTime updatedAt;

    /**
     * Constructs a BaseEntity with the specified id.
     *
     * @param id The unique identifier for the entity.
     */
    public BaseEntity(Long id) {
        this.id = id;
    }

    /**
     * Sets the creation and update timestamps to the current date and time.
     * This method should be called when the entity is initially created.
     */
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Updates the update timestamp to the current date and time.
     * This method should be called when the entity is updated.
     */
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
