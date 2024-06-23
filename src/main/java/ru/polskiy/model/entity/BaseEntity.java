package ru.polskiy.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class BaseEntity {

    Long id;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public BaseEntity(Long id) {
        this.id=id;
    }

    /**
     * Sets the creation and update timestamps to the current time.
     * This method should be called when the entity is created.
     */
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Updates the update timestamp to the current time.
     * This method should be called when the entity is updated.
     */
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
