package ru.polskiy.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.polskiy.model.type.ActionType;
import ru.polskiy.model.type.AuditStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Audit {

    Long id;

    String login;

    AuditStatus auditStatus;

    ActionType actionType;
}
