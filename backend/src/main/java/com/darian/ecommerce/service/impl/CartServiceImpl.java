package com.darian.ecommerce.service.impl;

import com.darian.ecommerce.businesslogic.mapper.cartmapper.CartItemMapper;
import com.darian.ecommerce.businesslogic.mapper.cartmapper.CartMapper;
import com.darian.ecommerce.dto.CartDTO;
import com.darian.ecommerce.dto.CartItemDTO;
import com.darian.ecommerce.entity.Cart;
import com.darian.ecommerce.entity.CartItem;
import com.darian.ecommerce.entity.Product;
import com.darian.ecommerce.repository.CartRepository;
import com.darian.ecommerce.service.CartService;
import com.darian.ecommerce.service.ProductService;
import com.darian.ecommerce.utils.LoggerMessages;
import com.darian.ecommerce.utils.ErrorMessages;
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
    private final CartItemMapper cartItemMapper;
    private final CartMapper cartMapper;

    // Constructor injection for dependencies
    public CartServiceImpl(CartRepository cartRepository, ProductService productService, CartItemMapper cartItemMapper, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.cartItemMapper = cartItemMapper;
        this.cartMapper = cartMapper;
    }

    @Override
    public CartDTO addProductToCart(Integer userId, Long productId, Integer quantity) {
        logger.info(LoggerMessages.CART_ADD_PRODUCT, productId, quantity, userId);

        Product product = productService.getProductById(productId)
            .orElseThrow(() -> new IllegalArgumentException(String.format(ErrorMessages.PRODUCT_NOT_FOUND, productId)));

        if (product.getStockQuantity() <= 0 || productService.checkProductQuantity(productId) <= 0) {
            logger.warn(LoggerMessages.CART_PRODUCT_NOT_AVAILABLE, productId, userId);
            throw new IllegalArgumentException(String.format(ErrorMessages.PRODUCT_NOT_AVAILABLE, productId));
        }

        Cart cart = cartRepository.findByUser_Id(userId).orElse(new Cart());
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            logger.info(LoggerMessages.CART_UPDATE_QUANTITY, productId, item.getQuantity(), userId);
        } else {
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setCart(cart);
            cart.getItems().add(newItem);
        }

        Cart savedCart = cartRepository.save(cart);
        return cartMapper.toDTO(savedCart);
    }

    @Override
    public CartDTO viewCart(Integer userId) {
        logger.info(LoggerMessages.CART_VIEW, userId);
        Cart cart = cartRepository.findByUser_Id(userId).orElse(new Cart());
        return cartMapper.toDTO(cart);
    }

    @Override
    public CartDTO updateCart(Integer userId, Long productId, Integer quantity) {
        logger.info(LoggerMessages.CART_UPDATE_QUANTITY, productId, quantity, userId);
        Cart cart = cartRepository.findByUser_Id(userId).orElse(new Cart());
        
        cart.getItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(productId))
                .findFirst()
                .ifPresent(item -> item.setQuantity(quantity));

        Cart savedCart = cartRepository.save(cart);
        return cartMapper.toDTO(savedCart);
    }

    @Override
    public CartDTO removeFromCart(Integer userId, Long productId) {
        logger.info(LoggerMessages.CART_REMOVE_PRODUCT, productId, userId);
        Cart cart = cartRepository.findByUser_Id(userId).orElse(new Cart());
        
        cart.getItems().removeIf(item -> item.getProduct().getProductId().equals(productId));
        Cart savedCart = cartRepository.save(cart);
        return cartMapper.toDTO(savedCart);
    }

    @Override
    public void emptyCart(Integer userId) {
        logger.info(LoggerMessages.CART_CLEAR, userId);
        Cart cart = cartRepository.findByUser_Id(userId).orElse(new Cart());
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    @Override
    public List<CartItemDTO> getCartItems(Integer userId) {
        logger.info(LoggerMessages.CART_VIEW, userId);
        Cart cart = cartRepository.findByUser_Id(userId).orElse(new Cart());
        return cartItemMapper.toDTOList(cart.getItems()) ;
    }

    @Override
    public Boolean checkAvailability(CartDTO cartDTO) {
        return cartDTO.getItems().stream()
                .allMatch(item -> {
                    Product product = productService.getProductById(item.getProductId())
                            .orElseThrow(() -> new IllegalArgumentException(
                                String.format(ErrorMessages.PRODUCT_NOT_FOUND, item.getProductId())));
                    return product.getStockQuantity() >= item.getQuantity();
                });
    }
}
