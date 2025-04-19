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
@Table(name = "order")
public class Order {
    // Primary key
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    // ID of the customer who placed the order
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User user;

    // Status of the order (e.g., PENDING, SHIPPED)
    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "is_rush_order")
    private Boolean isRushOrder;

    // Delivery information (1-1 relationship)
    @ManyToOne
    @JoinColumn(name = "delivery_info_id", nullable = false)
    private DeliveryInfo deliveryInfo;

    // Subtotal of the order
    private float subtotal;

    // Shipping fee for the order
    @Column(name = "shipping_fee")
    private float shippingFee;

    // Total amount (including shipping and discount)
    private float total;

    // Date and time the order was created
    @Column(name = "created_timestamp")
    private LocalDateTime createdDate;

    // Discount applied to the order
    private float discount;

    // Status of the payment (e.g., PAID, REFUNDED , UNPAID , ... ) //need more check
    @Column(name = "payment_status")
    private String paymentStatus;

    // List of order items (1-* relationship)
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;



    //getter + setter

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
