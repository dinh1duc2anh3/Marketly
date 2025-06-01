package com.darian.ecommerce.repository;

import com.darian.ecommerce.entity.Cart;
import com.darian.ecommerce.entity.CartItem;
import com.darian.ecommerce.entity.Product;
import com.darian.ecommerce.id.CartItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository  extends JpaRepository<CartItem, CartItemId>  {
    // Save a cart
//    CartItem save(CartItem cartItem);

    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

    List<CartItem> findByCart(Cart cart);

    void deleteByCart(Cart cart);
}