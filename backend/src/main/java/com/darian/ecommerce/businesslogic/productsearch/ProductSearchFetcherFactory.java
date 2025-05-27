package com.darian.ecommerce.businesslogic.productsearch;

import com.darian.ecommerce.businesslogic.mapper.productmapper.ProductMapper;
import com.darian.ecommerce.dto.ProductDTO;
import com.darian.ecommerce.enums.UserRole;
import com.darian.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductSearchFetcherFactory {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    // Constructor injection for ProductRepository
    public ProductSearchFetcherFactory(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    // Get the appropriate ProductSearchFetcher based on role
    public ProductSearchFetcher<? extends ProductDTO> getFetcher(UserRole role) {
        return switch (role){
            case CUSTOMER -> new CustomerProductSearchFetcher(productRepository,productMapper);
            case MANAGER -> new ManagerProductSearchFetcher(productRepository,productMapper);
            default -> throw new IllegalArgumentException("Invalid role: " + role);
        };
    }
}
