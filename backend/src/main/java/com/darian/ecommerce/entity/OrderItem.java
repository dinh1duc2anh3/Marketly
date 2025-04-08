package com.darian.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID of the product in the order
    private String productId;

    // Quantity of the product
    private int quantity;

    // Unit price of the product
    private float unitPrice;


    // Reference to the parent order (*-1 relationship)
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // Calculate line total (quantity * unitPrice)
    public float getLineTotal(){
        return quantity * unitPrice;
    }
}
