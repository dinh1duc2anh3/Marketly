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
        logger.info("User {} (role: {}) searched with keyword: {}", userId, role, keyword);
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
        logger.info("User {} (role: {}) viewed product: {}", userId, role, productId);

        //create audit log entity
        AuditLog log = new AuditLog();
        log.setActionType(ActionType.VIEW_PRODUCT);

        //fetch + set user
        User user = userService.getUserById(userId);
        log.setUser(user);

        //fetch + set product
        Product product = productService.getProductById(productId).orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + productId));
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
            logger.info("User {} (role: {}) added product: {}", userId, role, productId);

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
            logger.error("Failed to log add action for user {}: {}", userId, e.getMessage());
            return false;
        }
    }

    // Log a delete product action, return true if successful
    @Override
    public Boolean logDeleteAction(Integer userId, Long productId, UserRole role) {
        try {
            logger.info("User {} (role: {}) deleted product: {}", userId, role, productId);
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
            logger.error("Failed to log delete action for user {}: {}", userId, e.getMessage());
            return false;
        }
    }

    // Check if the user has exceeded the delete limit
    @Override
    public Boolean checkDeleteLimit(Integer userId) {
        logger.info("Checking delete limit for user: {}", userId);
        // Example: Limit is 30 deletions per user
        Integer deleteCount = countDeletesByUserId(userId);
        boolean withinLimit = deleteCount < 30;
        logger.info("User {} has {} deletions, limit check: {}", userId, deleteCount, withinLimit);
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
            logger.info("User {} (role: {}) updated product: {}", userId, role, productId);
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
            logger.error("Failed to log update action for user {}: {}", userId, e.getMessage());
            return false;
        }
    }

    // Log an order-related action, return true if successful
    @Override
    public Boolean logOrderAction(Integer userId, Long orderId, UserRole role, ActionType action) {
        try {
            logger.info("User {} (role: {}) performed order action: {}", userId, role, orderId);
            AuditLog log = new AuditLog();
            User user = userService.getUserById(userId);
            log.setUser(user);
            log.setActionType(ActionType.ORDER_ACTION);
//            log.setProduct(orderId); // Using productId field to store orderId for simplicity
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
//        log.setUserId(null);
        if (paymentResult.getTransactionType().equals(TransactionType.PAYMENT) ){
            log.setActionType(ActionType.PAY_ORDER);
        }
        else if (paymentResult.getTransactionType().equals(TransactionType.REFUND)){
            log.setActionType(ActionType.CANCEL_ORDER);
        }
//        log.setReferenceId(paymentResult.getTransactionId()); // Store transactionId
        log.setRole(UserRole.CUSTOMER); // Assuming payment/refund by customer
        log.setTimestamp(LocalDateTime.now());
        auditLogRepository.save(log);
    }
}
