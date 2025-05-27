package com.darian.ecommerce.entity;

import com.darian.ecommerce.id.CartItemId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart_item")
public class CartItem {

    // Primary key
    @EmbeddedId
    private CartItemId id;

    // ID of the cart
    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart ;

    // ID of the product
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // Quantity of the product
    private Integer quantity;

    // Price of the product at the time of adding to cart
    @Column(name = "product_price")
    private Float productPrice;

}
