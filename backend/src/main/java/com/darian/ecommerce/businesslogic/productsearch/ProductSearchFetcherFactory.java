package com.darian.ecommerce.businesslogic.productsearch;

import com.darian.ecommerce.businesslogic.mapper.ProductMapper;
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
    public ProductSearchFetcher getFetcher(UserRole role) {
        if (role.equals(UserRole.CUSTOMER)) {
            return new CustomerProductSearchFetcher(productRepository,productMapper);
        } else if (role.equals(UserRole.MANAGER)) {
            return new ManagerProductSearchFetcher(productRepository);
        } else {
            throw new IllegalArgumentException("Invalid role: " + role);
        }
    }
}
