package com.darian.ecommerce.utils;

public class ApiEndpoints {
    // Base URLs
    public static final String BASE_API = "/api/v1";
    
    // Order endpoints
    public static final String ORDERS = BASE_API + "/orders";
    public static final String ORDER_BY_ID = ORDERS + "/{orderId}";
    public static final String ORDER_PAYMENT = ORDER_BY_ID + "/pay";
    public static final String ORDER_CANCEL = ORDER_BY_ID + "/cancel";
    public static final String ORDER_DELIVERY = ORDER_BY_ID + "/delivery";
    
    // Payment endpoints
    public static final String PAYMENTS = BASE_API + "/payments";
    public static final String PAYMENT_BY_ORDER = PAYMENTS + "/{orderId}";
    public static final String PAYMENT_REFUND = PAYMENT_BY_ORDER + "/refund";
    
    // VNPay specific endpoints
    public static final String VNPAY_API_BASE = "https://sandbox.vnpayment.vn/paymentv2";
    public static final String VNPAY_PAYMENT = VNPAY_API_BASE + "/create_payment_url";
    public static final String VNPAY_REFUND = VNPAY_API_BASE + "/refund";
    public static final String VNPAY_QUERY = VNPAY_API_BASE + "/query";
    
    private ApiEndpoints() {
        // Private constructor to prevent instantiation
    }
} 