package com.darian.ecommerce.service.impl;

import com.darian.ecommerce.dto.ProductReviewDTO;
import com.darian.ecommerce.entity.Product;
import com.darian.ecommerce.entity.ProductReview;
import com.darian.ecommerce.entity.User;
import com.darian.ecommerce.repository.ProductReviewRepository;
import com.darian.ecommerce.service.ProductReviewService;
import com.darian.ecommerce.service.ProductService;
import com.darian.ecommerce.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ProductReviewServiceImpl implements ProductReviewService {
    // Logger for logging actions and errors
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    // Dependencies injected via constructor
    private final ProductReviewRepository productReviewRepository;
    private final ProductService productService;
    private final UserService userService;


    // Constructor for dependency injection
    public ProductReviewServiceImpl(ProductReviewRepository productReviewRepository, ProductService productService, UserService userService) {
        this.productReviewRepository = productReviewRepository;
        this.productService = productService;
        this.userService = userService;
    }

    // Add a review for a product
    @Override
    public ProductReviewDTO addReview(ProductReviewDTO reviewDTO) {
        //create new productReview Entity
        ProductReview review = mapToReviewEntity(reviewDTO);

        //save productReview
        ProductReview savedReview = productReviewRepository.save(review);
        logger.info("Review added for product: {}", savedReview.getProduct().getProductId());

        return mapToReviewDTO(savedReview);
    }

    // Get all reviews for a product
    @Override
    public List<ProductReviewDTO> getReviews(Long productId) {
        logger.info("Fetching reviews for product: {}", productId);
        List<ProductReview> reviews = productReviewRepository.findByProductId(productId);
        return reviews.stream().map(this::mapToReviewDTO).toList();
    }

    // Chuyển từ DTO sang Entity
    private ProductReview mapToReviewEntity(ProductReviewDTO reviewDTO) {

        //find product from productId
        Product product = productService.getProductById(reviewDTO.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        //find user from customerId
        User user = userService.getUserById(reviewDTO.getCustomerId());

        //autofill id + createDate when first creating an object of productReview
        return ProductReview.builder()
                .product(product)
                .user(user)
                .rating(reviewDTO.getRating())
                .comment(reviewDTO.getComment())
                .build();
    }

    // Chuyển từ Entity sang DTO
    private ProductReviewDTO mapToReviewDTO(ProductReview review) {
        return ProductReviewDTO.builder()
                .productId(review.getProduct().getProductId())
                .customerId(review.getUser().getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdDate(review.getCreatedDate())
                .build();
    }
}
