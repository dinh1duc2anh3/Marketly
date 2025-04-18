package com.darian.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart_item")
public class CartItem {
    // Primary key
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // ID of the product
    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // Quantity of the product
    private int quantity;

    // Price of the product at the time of adding to cart
    @Column(name = "product_price")
    private float productPrice;

}
