package com.darian.ecommerce.controller;

import com.darian.ecommerce.dto.CartDTO;
import com.darian.ecommerce.service.impl.CartServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartServiceImpl cartService;

    // Constructor injection for CartServiceImpl
    public CartController(CartServiceImpl cartService) {
        this.cartService = cartService;
    }

    // Add a product to the user's cart
    @PostMapping("/{userId}/add")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable String userId,
                                                    @RequestParam String productId,
                                                    @RequestParam int quantity) {
        CartDTO cartDTO = cartService.addProductToCart(userId, productId, quantity);
        return ResponseEntity.ok(cartDTO);
    }

    // View the user's cart
    @GetMapping("/{userId}")
    public ResponseEntity<CartDTO> viewCart(@PathVariable String userId) {
        CartDTO cartDTO = cartService.viewCart(userId);
        return ResponseEntity.ok(cartDTO);
    }

    // Update the quantity of a product in the user's cart
    @PutMapping("/{userId}/update")
    public ResponseEntity<CartDTO> updateCart(@PathVariable String userId,
                                              @RequestParam String productId,
                                              @RequestParam int quantity) {
        CartDTO cartDTO = cartService.updateCart(userId, productId, quantity);
        return ResponseEntity.ok(cartDTO);
    }

    // Remove a product from the user's cart
    @DeleteMapping("/{userId}/remove")
    public ResponseEntity<Void> removeFromCart(@PathVariable String userId,
                                               @RequestParam String productId) {
        cartService.removeFromCart(userId, productId);
        return ResponseEntity.noContent().build();
    }

    // Empty the user's cart
    @DeleteMapping("/{userId}/empty")
    public ResponseEntity<Void> emptyCart(@PathVariable String userId) {
        cartService.emptyCart(userId);
        return ResponseEntity.noContent().build();
    }

}
