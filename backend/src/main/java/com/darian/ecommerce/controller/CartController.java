package com.darian.ecommerce.controller;

import com.darian.ecommerce.dto.CartDTO;
import com.darian.ecommerce.service.CartService;
import com.darian.ecommerce.utils.ApiEndpoints;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiEndpoints.CART)
public class CartController {
    private final CartService cartService;

    // Constructor injection for CartService
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @RestController
    @RequestMapping("/api/test")
    public class TestController {
        @GetMapping
        public ResponseEntity<String> testEndpoint() {
            return ResponseEntity.ok("Backend is working!");
        }
    }

    // Add a product to the user's cart
    @PostMapping(ApiEndpoints.CART_ADD)
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable Integer userId,
                                                    @RequestParam Long productId,
                                                    @RequestParam Integer quantity) {
        CartDTO cartDTO = cartService.addProductToCart(userId, productId, quantity);
        return ResponseEntity.ok(cartDTO);
    }

    // View the user's cart
    @GetMapping(ApiEndpoints.CART_BY_USER)
    public ResponseEntity<CartDTO> viewCart(@PathVariable Integer userId) {
        CartDTO cartDTO = cartService.viewCart(userId);
        return ResponseEntity.ok(cartDTO);
    }

    // Update the quantity of a product in the user's cart
    @PutMapping(ApiEndpoints.CART_UPDATE)
    public ResponseEntity<CartDTO> updateCart(@PathVariable Integer userId,
                                              @RequestParam Long productId,
                                              @RequestParam Integer quantity) {
        CartDTO cartDTO = cartService.updateCart(userId, productId, quantity);
        return ResponseEntity.ok(cartDTO);
    }

    // Remove a product from the user's cart
    @DeleteMapping(ApiEndpoints.CART_REMOVE)
    public ResponseEntity<Void> removeFromCart(@PathVariable Integer userId,
                                               @RequestParam Long productId) {
        cartService.removeFromCart(userId, productId);
        return ResponseEntity.noContent().build();
    }

    // Empty the user's cart
    @DeleteMapping(ApiEndpoints.CART_EMPTY)
    public ResponseEntity<Void> emptyCart(@PathVariable Integer userId) {
        cartService.emptyCart(userId);
        return ResponseEntity.noContent().build();
    }

}
