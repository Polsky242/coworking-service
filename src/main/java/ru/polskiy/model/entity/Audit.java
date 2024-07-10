package ru.polskiy.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.polskiy.model.type.ActionType;
import ru.polskiy.model.type.AuditStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Audit {
    private Long id;
    private String login;
    private AuditStatus auditStatus;
    private ActionType actionType;
}
