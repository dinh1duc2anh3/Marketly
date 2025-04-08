package com.darian.ecommerce.service;

import com.darian.ecommerce.dto.*;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(CartDTO cartDTO);

    OrderDTO placeOrder(OrderDTO orderDTO);

    RushOrderDTO placeRushOrder(RushOrderDTO rushOrderDTO);

    void cancelOrder(String orderId);

    OrderDTO getOrderDetails(String orderId);

    Boolean checkAvailability(CartDTO cartDTO);

    Boolean validateDeliveryInfo(DeliveryInfoDTO deliveryInfoDTO);

    OrderDTO setDeliveryInfo(String orderId, DeliveryInfoDTO deliveryInfoDTO);

    Boolean isRushOrder(String orderId);

    void setPending(String orderId);

    Boolean checkRushDeliveryAddress(String address);

    Boolean checkRushProductEligibility(String cartId);

    InvoiceDTO getInvoice(String orderId);

    void updatePaymentStatus(String orderId, String status);

    List<OrderDTO> getOrderHistory(String customerId);
}
