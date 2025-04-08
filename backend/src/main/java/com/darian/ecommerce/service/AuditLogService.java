package com.darian.ecommerce.service;

import com.darian.ecommerce.dto.BasePaymentResult;

public interface AuditLogService {

    // Log a search action performed by a user
    void logSearchAction(String userId, String keyword, String role);

    // Log a product view action by a user
    void logViewProduct(String productId, String userId, String role);

    // Log an add product action, return true if successful
    Boolean logAddAction(String userId, String productId, String role);

    // Log a delete product action, return true if successful
    Boolean logDeleteAction(String userId, String productId, String role);

    // Check if the user has exceeded the delete limit
    Boolean checkDeleteLimit(String userId);

    //count delete by specific user
    int countDeletesByUserId(String userId);

    // Log an update product action, return true if successful
    Boolean logUpdateAction(String userId, String productId, String role);

    // Log an order-related action, return true if successful
    Boolean logOrderAction(String userId, String orderId, String role,String action);

    // Log a payment transaction
    void logPayment(BasePaymentResult paymentResult);

}
