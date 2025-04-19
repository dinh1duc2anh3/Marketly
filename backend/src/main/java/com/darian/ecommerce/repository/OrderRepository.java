package com.darian.ecommerce.repository;

import com.darian.ecommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    // Find order by ID
    Optional<Order> findById(String orderId);

    // Find orders by status
    List<Order> findByStatus(String status);

    // Find orders by customer ID
    List<Order> findByCustomerId(String customerId);

    // Save an order (returns Order, but UML specifies int, adjusted to entity)
    Order save(Order order);

    // Delete an order by ID
    void deleteById(String orderId);

    // Update order status
    default void updateOrderStatus(String orderId, String status) {
        findById(orderId).ifPresent(order -> {
            order.setStatus(status);
            save(order);
        });
    }

    // Update shipping fee
    default void updateShippingFee(String orderId, int fees) {
        findById(orderId).ifPresent(order -> {
            order.setShippingFee(fees);
            save(order);
        });
    }
}
