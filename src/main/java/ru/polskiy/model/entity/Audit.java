package ru.polskiy.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.polskiy.model.type.ActionType;
import ru.polskiy.model.type.AuditStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an audit in the system.
 *
 * This class contains information about an audit event, including the user login,
 * the status of the audit, and the type of action performed. It uses Lombok annotations
 * to generate boilerplate code such as getters, setters, constructors, and the builder pattern.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Audit {

    /**
     * The unique identifier of the audit record.
     */
    private Long id;

    /**
     * The login of the user associated with this audit record.
     */
    private String login;

    /**
     * The status of the audit.
     */
    private AuditStatus auditStatus;

    /**
     * The type of action that was performed.
     */
    private ActionType actionType;
}
