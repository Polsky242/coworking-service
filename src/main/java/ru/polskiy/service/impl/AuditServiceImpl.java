package ru.polskiy.service.impl;

import lombok.RequiredArgsConstructor;
import ru.polskiy.dao.AuditDao;
import ru.polskiy.model.entity.Audit;
import ru.polskiy.model.type.ActionType;
import ru.polskiy.model.type.AuditStatus;
import ru.polskiy.service.AuditService;

import java.util.List;

@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditDao auditDAO;

    /**
     * Saves an audit record.
     *
     * @param audit the audit record to save
     * @return the saved audit record
     */
    public Audit save(Audit audit) {
        return auditDAO.save(audit);
    }

    /**
     * Retrieves a list of all audit records.
     *
     * @return the list of all audit records
     */
    @Override
    public List<Audit> showAllAudits() {
        return auditDAO.findAll();
    }

    /**
     * Performs an audit for a specific action.
     *
     * @param login      the login associated with the action
     * @param actionType the type of action
     * @param auditStatus  the type of audit (SUCCESS or FAIL)
     * @return
     */
    @Override
    public Audit audit(String login, ActionType actionType, AuditStatus auditStatus) {
        Audit audit = Audit.builder()
                .login(login)
                .actionType(actionType)
                .auditStatus(auditStatus)
                .build();
        return save(audit);
    }
}
