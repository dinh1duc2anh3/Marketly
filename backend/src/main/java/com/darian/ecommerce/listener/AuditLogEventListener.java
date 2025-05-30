package com.darian.ecommerce.listener;

import com.darian.ecommerce.event.*;
import com.darian.ecommerce.service.AuditLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AuditLogEventListener {
    private final Logger logger = LoggerFactory.getLogger(AuditLogEventListener.class);

    private final AuditLogService auditLogService;

    public AuditLogEventListener(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @EventListener
    public void handleViewProduct(ViewProductEvent event) {
        auditLogService.logViewProduct(event.getProductId(), event.getUserId(), event.getRole());
    }

    @EventListener
    public void handleSearchProduct(SearchProductEvent event) {
        auditLogService.logSearchAction(event.getUserId(), event.getKeyword(), event.getRole());
    }

    @EventListener
    public void handleAddProduct(AddProductEvent event) {
        auditLogService.logAddAction(event.getUserId(), event.getProductId(), event.getRole());
    }

    @EventListener
    public void handleUpdateProduct(UpdateProductEvent event) {
        auditLogService.logUpdateAction(event.getUserId(), event.getProductId(), event.getRole());
    }

    @EventListener
    public void handleDeleteProduct(DeleteProductEvent event) {
        auditLogService.logDeleteAction(event.getUserId(), event.getProductId(), event.getRole());
    }

    @EventListener
    public void handleCheckDeleteLimit(CheckDeleteLimitEvent event) {
        logger.info("Handling check delete limit event for user: {}", event.getUserId());
    }
}
