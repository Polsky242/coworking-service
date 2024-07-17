package ru.polskiy.aspects;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import ru.polskiy.annotations.Auditable;
import ru.polskiy.model.entity.Audit;
import ru.polskiy.model.type.ActionType;
import ru.polskiy.model.type.AuditStatus;
import ru.polskiy.service.AuditService;

@Aspect
@RequiredArgsConstructor
public class AuditAspect {

    private final AuditService auditService;

    @Pointcut("(within(@ru.polskiy.annotations.Auditable *) || execution(@ru.polskiy.annotations.Auditable * *(..))) && execution(* *(..))")
    public void annotatedByAuditable() {
    }

    @Around("annotatedByAuditable()")
    public Object audit(ProceedingJoinPoint pjp) {
        var methodSignature = (MethodSignature) pjp.getSignature();

        Auditable audit = methodSignature.getMethod().getAnnotation(Auditable.class);
        ActionType actionType = audit.actionType();
        String payload = audit.login();
        if (payload.isEmpty()) {
            payload = audit.userId();
        }
        return auditService.audit(payload, actionType, AuditStatus.SUCCESS);
    }

    @AfterThrowing(pointcut = "annotatedByAuditable() && @annotation(ru.polskiy.annotations.Auditable)")
    public void auditFailure(ProceedingJoinPoint pjp) {
        var methodSignature = (MethodSignature) pjp.getSignature();

        Auditable audit = methodSignature.getMethod().getAnnotation(Auditable.class);
        ActionType actionType = audit.actionType();
        String payload = audit.login();
        if (payload.isEmpty()) {
            payload = audit.userId();
        }

        auditService.audit(audit.login(), actionType, AuditStatus.FAIL);
    }
}
