package com.darian.ecommerce.service.impl;

import com.darian.ecommerce.businesslogic.mapper.productmapper.ProductReviewMapper;
import com.darian.ecommerce.dto.ProductReviewDTO;
import com.darian.ecommerce.entity.ProductReview;
import com.darian.ecommerce.repository.ProductReviewRepository;
import com.darian.ecommerce.service.ProductReviewService;
import com.darian.ecommerce.service.ProductService;
import com.darian.ecommerce.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductReviewServiceImpl implements ProductReviewService {
    // Logger for logging actions and errors
    private static final Logger logger = LoggerFactory.getLogger(ProductReviewServiceImpl.class);

    // Dependencies injected via constructor
    private final ProductReviewRepository productReviewRepository;
    private final ProductService productService;
    private final UserService userService;
    private final ProductReviewMapper productReviewMapper;


    // Constructor for dependency injection
    public ProductReviewServiceImpl(ProductReviewRepository productReviewRepository, ProductService productService, UserService userService, ProductReviewMapper productReviewMapper) {
        this.productReviewRepository = productReviewRepository;
        this.productService = productService;
        this.userService = userService;
        this.productReviewMapper = productReviewMapper;
    }

    // Add a review for a product
    @Override
    public ProductReviewDTO addReview(ProductReviewDTO reviewDTO) {
        //create new productReview Entity
        ProductReview review = productReviewMapper.toEntity(reviewDTO);

        //save productReview
        ProductReview savedReview = productReviewRepository.save(review);
        logger.info("Review added for product: {}", savedReview.getProduct().getProductId());

        return productReviewMapper.toDTO(savedReview);
    }

    // Get all reviews for a product
    @Override
    public List<ProductReviewDTO> getReviews(Long productId) {
        logger.info("Fetching reviews for product: {}", productId);
        List<ProductReview> reviews = productReviewRepository.findByProduct_ProductId(productId);
        return reviews.stream().map(productReviewMapper::toDTO).toList();
    }

}
