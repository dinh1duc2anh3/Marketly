package com.darian.ecommerce.repository;

import com.darian.ecommerce.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    // Save an audit log entry
    AuditLog save(AuditLog auditLog);

    // Count the number of delete actions by a user
    int countDeletesByUserId(String userId);
}
