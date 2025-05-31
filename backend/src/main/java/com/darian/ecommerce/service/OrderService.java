package com.darian.ecommerce.service;

import com.darian.ecommerce.config.exception.order.OrderNotFoundException;
import com.darian.ecommerce.dto.*;
import com.darian.ecommerce.entity.Order;
import com.darian.ecommerce.enums.OrderStatus;
import com.darian.ecommerce.enums.PaymentStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    OrderDTO createOrder(CartDTO cartDTO);

    OrderDTO getOrderDetails(Long orderId) throws OrderNotFoundException;

    InvoiceDTO getInvoice(Long orderId) throws OrderNotFoundException;

    Optional<Order> findOrderById(Long orderId);

    List<OrderDTO> getOrderHistory(Integer customerId);

    OrderDTO setDeliveryInfo(Long orderId, DeliveryInfoDTO deliveryInfoDTO) throws OrderNotFoundException;

    void setPending(Long orderId);

    OrderDTO placeOrder(OrderDTO orderDTO);

    RushOrderDTO placeRushOrder(RushOrderDTO rushOrderDTO);

    void cancelOrder(Long orderId) throws OrderNotFoundException;

    void updatePaymentStatus(Long orderId, PaymentStatus status);

    void updateOrderStatus(Long orderId, OrderStatus orderStatus);

    Boolean isRushOrder(Long orderId);

    Boolean checkAvailability(CartDTO cartDTO);

    Boolean validateDeliveryInfo(DeliveryInfoDTO deliveryInfoDTO);

    Boolean checkRushDeliveryAddress(String address);

    Boolean checkCancellationValidity(Long orderId);

    Boolean checkRushProductEligibility(Long productId);
}
