package com.darian.ecommerce.service;

import com.darian.ecommerce.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    OrderDTO createOrder(CartDTO cartDTO);

    OrderDTO placeOrder(OrderDTO orderDTO);

    RushOrderDTO placeRushOrder(RushOrderDTO rushOrderDTO);

    void cancelOrder(Long orderId);

    OrderDTO getOrderDetails(Long orderId);

    Boolean checkAvailability(CartDTO cartDTO);

    Boolean validateDeliveryInfo(DeliveryInfoDTO deliveryInfoDTO);

    OrderDTO setDeliveryInfo(Long orderId, DeliveryInfoDTO deliveryInfoDTO);

    Boolean isRushOrder(Long orderId);

    void setPending(Long orderId);

    Boolean checkRushDeliveryAddress(String address);

    Boolean checkRushProductEligibility(String Long productId);

    InvoiceDTO getInvoice(Long orderId);

    void updatePaymentStatus(Long orderId, String status);

    List<OrderDTO> getOrderHistory(Integer customerId);
}
