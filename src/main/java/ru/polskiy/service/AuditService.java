package ru.polskiy.service;

import ru.polskiy.model.entity.Audit;
import ru.polskiy.model.type.ActionType;
import ru.polskiy.model.type.AuditStatus;

import java.util.List;

public interface AuditService {

    /**
     * Retrieves a list of all audit records.
     *
     * @return the list of audit records
     */
    List<Audit> showAllAudits();

    /**
     * Performs an audit for the specified login, action type, and audit type.
     *
     * @param login      the login associated with the action
     * @param actionType the type of action being audited
     * @param auditStatus  the result of the audit (SUCCESS or FAIL)
     */
    Audit audit(String login, ActionType actionType, AuditStatus auditStatus);
}
