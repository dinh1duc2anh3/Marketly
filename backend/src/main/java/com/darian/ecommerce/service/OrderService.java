package com.darian.ecommerce.service;

import com.darian.ecommerce.dto.DeliveryInfoDTO;
import com.darian.ecommerce.dto.InvoiceDTO;
import com.darian.ecommerce.dto.OrderDTO;
import com.darian.ecommerce.entity.Order;
import com.darian.ecommerce.enums.PaymentStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface OrderService {
//    OrderDTO createOrder(CartDTO cartDTO);

    OrderDTO getOrderDetails(Long orderId);

    InvoiceDTO getInvoice(Long orderId);

    Optional<Order> findOrderById(Long orderId);

    List<OrderDTO> getOrderHistory(Integer customerId);

    OrderDTO setDeliveryInfo(Long orderId, DeliveryInfoDTO deliveryInfoDTO);

    void setPending(Long orderId);

//    OrderDTO placeOrder(OrderDTO orderDTO);
//
//    RushOrderDTO placeRushOrder(RushOrderDTO rushOrderDTO);

    void cancelOrder(Long orderId);

    void updatePaymentStatus(Long orderId, PaymentStatus status);

    Boolean isRushOrder(Long orderId);

//    Boolean checkAvailability(CartDTO cartDTO);

    Boolean validateDeliveryInfo(DeliveryInfoDTO deliveryInfoDTO);

    Boolean checkRushDeliveryAddress(String address);

//    Boolean checkRushProductEligibility(Long productId);




}
