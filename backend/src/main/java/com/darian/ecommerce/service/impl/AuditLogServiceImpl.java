package com.darian.ecommerce.service.impl;

import com.darian.ecommerce.dto.BasePaymentResult;
import com.darian.ecommerce.entity.AuditLog;
import com.darian.ecommerce.repository.AuditLogRepository;
import com.darian.ecommerce.service.AuditLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditLogServiceImpl implements AuditLogService {
    // Logger for logging actions to console/file
    private static final Logger logger = LoggerFactory.getLogger(AuditLogServiceImpl.class);

    // Dependency injected via constructor
    private final AuditLogRepository auditLogRepository;

    // Constructor for dependency injection
    public AuditLogServiceImpl(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    // Log a search action performed by a user
    @Override
    public void logSearchAction(String userId, String keyword, String role) {
        logger.info("User {} (role: {}) searched with keyword: {}", userId, role, keyword);
        //create audit log entity
        AuditLog log = new AuditLog();
        log.setUserId(userId);
        log.setActionType("SEARCH_PRODUCTS");
        log.setKeyword(keyword);
        log.setRole(role);
        log.setTimestamp(LocalDateTime.now());
        //save audit log entity to db
        auditLogRepository.save(log);
    }

    // Log a product view action by a user
    @Override
    public void logViewProduct(String productId, String userId, String role) {
        logger.info("User {} (role: {}) viewed product: {}", userId, role, productId);
        //create audit log entity
        AuditLog log = new AuditLog();
        log.setUserId(userId);
        log.setActionType("VIEW_PRODUCT");
        log.setProductId(productId);
        log.setRole(role);
        log.setTimestamp(LocalDateTime.now());
        //save audit log entity to db
        auditLogRepository.save(log);
    }

    // Log an add product action, return true if successful
    @Override
    public Boolean logAddAction(String userId, String productId, String role) {
        try {
            logger.info("User {} (role: {}) added product: {}", userId, role, productId);
            //create audit log entity
            AuditLog log = new AuditLog();
            log.setUserId(userId);
            log.setActionType("ADD_PRODUCT");
            log.setProductId(productId);
            log.setRole(role);
            log.setTimestamp(LocalDateTime.now());
            //save audit log entity to db
            auditLogRepository.save(log);
            return true;
        } catch (Exception e) {
            logger.error("Failed to log add action for user {}: {}", userId, e.getMessage());
            return false;
        }
    }

    // Log a delete product action, return true if successful
    @Override
    public Boolean logDeleteAction(String userId, String productId, String role) {
        try {
            logger.info("User {} (role: {}) deleted product: {}", userId, role, productId);
            AuditLog log = new AuditLog();
            log.setUserId(userId);
            log.setActionType("DELETE_PRODUCT");
            log.setProductId(productId);
            log.setRole(role);
            log.setTimestamp(LocalDateTime.now());
            auditLogRepository.save(log);
            return true;
        } catch (Exception e) {
            logger.error("Failed to log delete action for user {}: {}", userId, e.getMessage());
            return false;
        }
    }

    // Check if the user has exceeded the delete limit
    @Override
    public Boolean checkDeleteLimit(String userId) {
        logger.info("Checking delete limit for user: {}", userId);
        // Example: Limit is 30 deletions per user
        int deleteCount = countDeletesByUserId(userId);
        boolean withinLimit = deleteCount < 30;
        logger.info("User {} has {} deletions, limit check: {}", userId, deleteCount, withinLimit);
        return withinLimit;
    }

    @Override
    public int countDeletesByUserId(String userId) {
        return auditLogRepository.countDeletesByUserId(userId);
    }

    // Log an update product action, return true if successful
    @Override
    public Boolean logUpdateAction(String userId, String productId, String role) {
        try {
            logger.info("User {} (role: {}) updated product: {}", userId, role, productId);
            AuditLog log = new AuditLog();
            log.setUserId(userId);
            log.setActionType("UPDATE_PRODUCT");
            log.setProductId(productId);
            log.setRole(role);
            log.setTimestamp(LocalDateTime.now());
            auditLogRepository.save(log);
            return true;
        } catch (Exception e) {
            logger.error("Failed to log update action for user {}: {}", userId, e.getMessage());
            return false;
        }
    }

    // Log an order-related action, return true if successful
    @Override
    public Boolean logOrderAction(String userId, String orderId, String role) {
        try {
            logger.info("User {} (role: {}) performed order action: {}", userId, role, orderId);
            AuditLog log = new AuditLog();
            log.setUserId(userId);
            log.setActionType("ORDER_ACTION");
            log.setProductId(orderId); // Using productId field to store orderId for simplicity
            log.setRole(role);
            log.setTimestamp(LocalDateTime.now());
            auditLogRepository.save(log);
            return true;
        } catch (Exception e) {
            logger.error("Failed to log order action for user {}: {}", userId, e.getMessage());
            return false;
        }
    }

    // Log a payment transaction
    @Override
    public void logPayment(BasePaymentResult paymentResult) {
        logger.info("Logging transaction: {}, type: {}",
                paymentResult.getTransactionId(), paymentResult.getTransactionType());
        AuditLog log = new AuditLog();
        // UserId not available in BasePaymentResult, may need to be passed separately
        log.setUserId(null);
        log.setActionType((paymentResult.getTransactionType()));
        log.setReferenceId(paymentResult.getTransactionId()); // Store transactionId
        log.setRole("CUSTOMER"); // Assuming payment/refund by customer
        log.setTimestamp(LocalDateTime.now());
        auditLogRepository.save(log);
    }
}
