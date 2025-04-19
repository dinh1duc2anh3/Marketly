package com.darian.ecommerce.service.impl;

import com.darian.ecommerce.businesslogic.carthelper.CartHelper;
import com.darian.ecommerce.dto.CartDTO;
import com.darian.ecommerce.dto.CartItemDTO;
import com.darian.ecommerce.entity.Cart;
import com.darian.ecommerce.entity.CartItem;
import com.darian.ecommerce.repository.CartRepository;
import com.darian.ecommerce.service.CartService;
import com.darian.ecommerce.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    private final CartRepository cartRepository;
    private final ProductService productService;

    // Constructor injection for dependencies
    public CartServiceImpl(CartRepository cartRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
    }


    @Override
    public CartDTO addProductToCart(String userId, String productId, int quantity) {
        logger.info("Adding product {} with quantity {} to cart for user {}", productId, quantity, userId);
        if (productService.checkProductQuantity(productId) <= 0) {
            logger.warn("Product {} is not available for user {}", productId, userId);
            throw new IllegalArgumentException("Product not available");
        }
        Cart cart = cartRepository.findByUserId(userId).orElse(new Cart(userId));
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();
        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        }
        else {
            CartItem newItem = new CartItem();
            newItem.setProductId(productId);
            newItem.setQuantity(quantity);
            newItem.setProductPrice(productService.getProductDetails(userId, productId, "CUSTOMER").getPrice());
            newItem.setCart(cart);
            cart.getItems().add(newItem);
        }
        cart.updateTotal();
        Cart savedCart = cartRepository.save(cart);
        logger.info("Product {} added to cart for user {}", productId, userId);
        return mapToCartDTO(savedCart);
    }

    @Override
    public CartDTO viewCart(String userId) {
        logger.info("Viewing cart for user {}", userId);
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found for user: " + userId));
        return mapToCartDTO(cart);
    }

    @Override
    public CartDTO updateCart(String userId, String productId, int quantity) {
        logger.info("Updating cart for user {}: product {}, quantity {}", userId, productId, quantity);
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found for user: " + userId));
        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not in cart"));
        item.setQuantity(quantity);
        cart.updateTotal();
        Cart savedCart = cartRepository.save(cart);
        logger.info("Cart updated for user {}", userId);
        return mapToCartDTO(savedCart);
    }

    @Override
    public CartDTO removeFromCart(String userId, String productId) {
        logger.info("Removing product {} from cart for user {}", productId, userId);
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found for user: " + userId));
        cart.getItems().removeIf(item -> item.getProductId().equals(productId));
        cart.updateTotal();
        Cart savedCart = cartRepository.save(cart);
        logger.info("Product {} removed from cart for user {}", productId, userId);
        return mapToCartDTO(savedCart);
    }

    @Override
    public void emptyCart(String userId) {
        logger.info("Emptying cart for user {}", userId);
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found for user: " + userId));
        cart.getItems().clear();
        cart.setTotal(0);
        cartRepository.save(cart);
        logger.info("Cart emptied for user {}", userId);
    }

    @Override
    public String getUserId(String cartId) {
        logger.info("Getting user ID for cart {}", cartId);
        //need more check
        // Assuming cartId is userId in this simple design
        return cartId; // Adjust if cartId differs from userId
    }

    @Override
    public List<CartItemDTO> getCartItems(String userId) {
        logger.info("Getting cart items for user {}", userId);
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found for user: " + userId));
        return cart.getItems().stream().map(this::mapToCartItemDTO).collect(Collectors.toList());
    }

    // Mapping method
    private CartDTO mapToCartDTO(Cart cart) {
        CartDTO dto = new CartDTO();
        dto.setUserId(cart.getUserId());
        dto.setItems(cart.getItems().stream().map(this::mapToCartItemDTO).collect(Collectors.toList()));
        return dto;
    }

    private CartItemDTO mapToCartItemDTO(CartItem item) {
        CartItemDTO dto = new CartItemDTO();
        dto.setProductId(item.getProductId());
        dto.setQuantity(item.getQuantity());
        return dto;
    }
}
