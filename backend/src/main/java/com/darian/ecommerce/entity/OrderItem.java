package com.darian.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_item")
public class OrderItem {
    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    // ID of the product in the order
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // Quantity of the product
    private int quantity;

    // Unit price of the product
    @Column(name = "unit_price")
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
