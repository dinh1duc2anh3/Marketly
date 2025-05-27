package com.darian.ecommerce.businesslogic.productdetail;

import com.darian.ecommerce.businesslogic.mapper.productmapper.ProductMapper;
import com.darian.ecommerce.dto.ProductDTO;
import com.darian.ecommerce.enums.UserRole;
import com.darian.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductDetailFetcherFactory {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    // Constructor injection for ProductRepository
    public ProductDetailFetcherFactory(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    // Get the appropriate ProductDetailFetcher based on role
    public ProductDetailFetcher<? extends ProductDTO> getFetcher(UserRole role) {
        return switch (role) {
            case UserRole.CUSTOMER -> new CustomerProductDetailFetcher(productRepository, productMapper);
            case UserRole.MANAGER -> new ManagerProductDetailFetcher(productRepository, productMapper);
            default -> throw new IllegalArgumentException("Invalid role: " + role);
        };
    }
}
