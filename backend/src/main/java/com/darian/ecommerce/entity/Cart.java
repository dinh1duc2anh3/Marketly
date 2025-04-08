package com.darian.ecommerce.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    // Primary key (userId acts as cartId)
    @Id
    private String userId;

    // List of items in the cart (1-* relationship)
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    // Total cost of the cart
    private float total;

    // Constructor for initializing with userId
    public Cart(String userId) {
        this.userId = userId;
        this.total = 0;
    }

    // Update total based on items
    public void updateTotal() {
        this.total = (float) items.stream()
                .mapToDouble(item -> item.getProductPrice() * item.getQuantity())
                .sum();
    }



}
