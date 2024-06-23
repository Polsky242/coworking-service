package ru.polskiy.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkspaceType extends BaseEntity{

    public WorkspaceType(Long id, String typeName) {
        super(id);
        this.typeName = typeName;
    }

    String typeName;
}
