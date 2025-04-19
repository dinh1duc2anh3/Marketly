package com.darian.ecommerce.repository;

import com.darian.ecommerce.entity.Order;
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
    default void updateOrderStatus(Long orderId, String status) {
        findById(orderId).ifPresent(order -> {
            order.setStatus(status);
            save(order);
        });
    }

    // Update shipping fee
    default void updateShippingFee(Long orderId, Integer fees) {
        findById(orderId).ifPresent(order -> {
            order.setShippingFee(fees);
            save(order);
        });
    }
}
