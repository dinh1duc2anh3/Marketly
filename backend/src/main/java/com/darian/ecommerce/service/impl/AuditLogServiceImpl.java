package com.darian.ecommerce.service.impl;

import com.darian.ecommerce.dto.BasePaymentResult;
import com.darian.ecommerce.entity.AuditLog;
import com.darian.ecommerce.entity.Product;
import com.darian.ecommerce.entity.User;
import com.darian.ecommerce.enums.ActionType;
import com.darian.ecommerce.enums.TransactionType;
import com.darian.ecommerce.enums.UserRole;
import com.darian.ecommerce.repository.AuditLogRepository;
import com.darian.ecommerce.service.AuditLogService;
import com.darian.ecommerce.service.ProductService;
import com.darian.ecommerce.service.UserService;
import com.darian.ecommerce.utils.LoggerMessages;
import com.darian.ecommerce.utils.ErrorMessages;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuditLogServiceImpl implements AuditLogService {
    // Logger for logging actions to console/file
    private static final Logger logger = LoggerFactory.getLogger(AuditLogServiceImpl.class);

    // Dependency injected via constructor
    private final AuditLogRepository auditLogRepository;
    private final UserService userService;
    private final ProductService productService;

    // Constructor for dependency injection
    public AuditLogServiceImpl(AuditLogRepository auditLogRepository,
                               UserService userService,
                               @Lazy ProductService productService) {
        this.auditLogRepository = auditLogRepository;
        this.userService = userService;
        this.productService = productService;
    }

    // Log a search action performed by a user
    @Override
    public void logSearchAction(Integer userId, String keyword, UserRole role) {
        logger.info(LoggerMessages.PRODUCT_SEARCH, userId, role, keyword);
        //create audit log entity
        AuditLog log = new AuditLog();
        User user = userService.getUserById(userId);
        log.setUser(user);
        log.setActionType(ActionType.SEARCH_PRODUCTS);
        log.setKeyword(keyword);
        log.setRole(role);
        log.setTimestamp(LocalDateTime.now());
        //save audit log entity to db
        auditLogRepository.save(log);
    }

    // Log a product view action by a user
    @Override
    public void logViewProduct(Long productId, Integer userId, UserRole role) {
        logger.info(LoggerMessages.PRODUCT_VIEW, userId, role, productId);

        //create audit log entity
        AuditLog log = new AuditLog();
        log.setActionType(ActionType.VIEW_PRODUCT);

        //fetch + set user
        User user = userService.getUserById(userId);
        log.setUser(user);

        //fetch + set product
        Product product = productService.getProductById(productId)
            .orElseThrow(() -> new EntityNotFoundException(String.format(ErrorMessages.PRODUCT_NOT_FOUND, productId)));
        log.setProduct(product);
        log.setKeyword(product.getName());

        log.setRole(role);

        //save audit log entity to db
        auditLogRepository.save(log);
    }

    // Log an add product action, return true if successful
    @Override
    public Boolean logAddAction(Integer userId, Long productId, UserRole role) {
        try {
            logger.info(LoggerMessages.PRODUCT_ADD, userId, role, productId);

            //create audit log entity
            AuditLog log = new AuditLog();
            User user = userService.getUserById(userId);
            log.setUser(user);
            log.setActionType(ActionType.ADD_PRODUCT);
            Optional<Product> product = productService.getProductById(productId);
            if (product.isPresent()) log.setProduct(product.get());
            log.setRole(role);
            log.setTimestamp(LocalDateTime.now());

            auditLogRepository.save(log);
            return true;
        } catch (Exception e) {
            logger.error(LoggerMessages.AUDIT_ACTION_FAILED, "add", userId, e.getMessage());
            return false;
        }
    }

    // Log a delete product action, return true if successful
    @Override
    public Boolean logDeleteAction(Integer userId, Long productId, UserRole role) {
        try {
            logger.info(LoggerMessages.PRODUCT_DELETE, userId, role, productId);
            AuditLog log = new AuditLog();
            User user = userService.getUserById(userId);
            log.setUser(user);
            log.setActionType(ActionType.DELETE_PRODUCT);
            Optional<Product> product = productService.getProductById(productId);
            if (product.isPresent()) log.setProduct(product.get());
            log.setRole(role);
            log.setTimestamp(LocalDateTime.now());
            auditLogRepository.save(log);
            return true;
        } catch (Exception e) {
            logger.error(LoggerMessages.AUDIT_ACTION_FAILED, "delete", userId, e.getMessage());
            return false;
        }
    }

    // Check if the user has exceeded the delete limit
    @Override
    public Boolean checkDeleteLimit(Integer userId) {
        logger.info(LoggerMessages.PRODUCT_DELETE_LIMIT, userId);
        Integer deleteCount = countDeletesByUserId(userId);
        boolean withinLimit = deleteCount < 30;
        logger.info(LoggerMessages.PRODUCT_DELETE_COUNT, userId, deleteCount, withinLimit);
        return withinLimit;
    }

    @Override
    public Integer countDeletesByUserId(Integer userId) {
        return auditLogRepository.countDeletesByUserId(userId);
    }

    // Log an update product action, return true if successful
    @Override
    public Boolean logUpdateAction(Integer userId, Long productId, UserRole role) {
        try {
            logger.info(LoggerMessages.PRODUCT_UPDATE, userId, role, productId);
            AuditLog log = new AuditLog();
            User user = userService.getUserById(userId);
            log.setUser(user);
            log.setActionType(ActionType.UPDATE_PRODUCT);
            Optional<Product> product = productService.getProductById(productId);
            if (product.isPresent()) log.setProduct(product.get());
            log.setRole(role);
            log.setTimestamp(LocalDateTime.now());
            auditLogRepository.save(log);
            return true;
        } catch (Exception e) {
            logger.error(LoggerMessages.AUDIT_ACTION_FAILED, "update", userId, e.getMessage());
            return false;
        }
    }

    // Log an order-related action, return true if successful
    @Override
    public Boolean logOrderAction(Integer userId, Long orderId, UserRole role, ActionType action) {
        try {
            logger.info(LoggerMessages.AUDIT_ORDER_ACTION, userId, role, orderId);
            AuditLog log = new AuditLog();
            User user = userService.getUserById(userId);
            log.setUser(user);
            log.setActionType(action);
            log.setRole(role);
            log.setTimestamp(LocalDateTime.now());
            auditLogRepository.save(log);
            return true;
        } catch (Exception e) {
            logger.error(LoggerMessages.AUDIT_ACTION_FAILED, "order", userId, e.getMessage());
            return false;
        }
    }

    // Log a payment transaction
    @Override
    public void logPayment(BasePaymentResult paymentResult) {
        logger.info(LoggerMessages.AUDIT_PAYMENT, paymentResult.getTransactionId(), paymentResult.getTransactionType());
        AuditLog log = new AuditLog();
        if (paymentResult.getTransactionType().equals(TransactionType.PAYMENT)) {
            log.setActionType(ActionType.PAY_ORDER);
        } else if (paymentResult.getTransactionType().equals(TransactionType.REFUND)) {
            log.setActionType(ActionType.CANCEL_ORDER);
        }
        log.setRole(UserRole.CUSTOMER);
        log.setTimestamp(LocalDateTime.now());
        auditLogRepository.save(log);
    }
}
