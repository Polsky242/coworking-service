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

    public Audit save(Audit audit) {
        return auditDAO.save(audit);
    }

    @Override
    public List<Audit> showAllAudits() {
        return auditDAO.findAll();
    }

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
