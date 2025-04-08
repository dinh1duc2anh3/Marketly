package com.darian.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID of the product
    private String productId;

    // Quantity of the product
    private int quantity;

    // Price of the product at the time of adding to cart
    private float productPrice;

    // Reference to the parent cart (*-1 relationship)
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
}
