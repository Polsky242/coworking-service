package ru.polskiy.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Workspace extends BaseEntity{

    @Builder.Default
    Long userId=null;

    LocalDateTime startDate;

    LocalDateTime endDate;

    Long typeId;
}
