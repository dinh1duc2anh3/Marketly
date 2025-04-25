package com.darian.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart")
public class Cart {
    // Primary key (userId acts as cartId)
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // List of items in the cart (1-* relationship)
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    // Total cost of the cart
    @Column(name = "total")
    private Float total;

    // Constructor for initializing with userId
    public Cart(Integer userId) {
        this.userId = userId;
        this.total = 0;
    }

    // Update total based on items
    public void updateTotal() {
        this.total = (Float) items.stream()
                .mapToDouble(item -> item.getProductPrice() * item.getQuantity())
                .sum();
    }



}
