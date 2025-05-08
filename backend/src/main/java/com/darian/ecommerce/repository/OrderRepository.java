package com.darian.ecommerce.repository;

import com.darian.ecommerce.entity.Order;
import com.darian.ecommerce.enums.OrderStatus;
import com.darian.ecommerce.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    // Find order by ID
    Optional<Order> findById(Long orderId);

    // Find orders by status
    List<Order> findByStatus(String status);

    // Find orders by customer ID
    List<Order> findByCustomerId(Integer customerId);

    // Save an order (returns Order, but UML specifies Integer, adjusted to entity)
    Order save(Order order);

    // Delete an order by ID
    void deleteById(Long orderId);

    // Update order status
    default void updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        findById(orderId).ifPresent(order -> {
            order.setOrderStatus(orderStatus);
            save(order);
        });
    }

    // Update order status
    default void updatePaymentStatus(Long orderId, PaymentStatus paymentStatus) {
        findById(orderId).ifPresent(order -> {
            order.setPaymentStatus(paymentStatus);
            save(order);
        });
    }

    // Update shipping fee
    default void updateShippingFee(Long orderId, Float fees) {
        findById(orderId).ifPresent(order -> {
            order.setShippingFee(fees);
            save(order);
        });
    }
}
