package com.darian.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    // Primary key
    @Id
    private String orderId;

    // ID of the customer who placed the order
    private String customerId;

    // List of order items (1-* relationship)
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    // Status of the order (e.g., PENDING, SHIPPED)
    private String status;

    // Delivery information (1-1 relationship)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_info_id")
    private DeliveryInfo deliveryInfo;

    // Subtotal of the order
    private float subtotal;

    // Shipping fee for the order
    private float shippingFee;

    // Total amount (including shipping and discount)
    private float total;

    // Date and time the order was created
    private LocalDateTime createdDate;

    // Discount applied to the order
    private float discount;

    // Calculate subtotal (sum of line totals)
    public float getSubtotal(){
        return items != null ? (float) items.stream().mapToDouble(OrderItem::getLineTotal).sum() : 0;
    }

    // Calculate VAT (example: 10% of subtotal)
    public float getVAT(){
        return getSubtotal() * 0.1f;
    }

    // Calculate VAT (example: 10% of subtotal)
    public float getTotal(){
        return getSubtotal() + shippingFee - discount + getVAT();
    }


}
