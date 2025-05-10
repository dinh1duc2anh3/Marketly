package com.darian.ecommerce.businesslogic.productdetail;

import com.darian.ecommerce.businesslogic.mapper.ProductMapper;
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
    public ProductDetailFetcher getFetcher(UserRole role) {
        if (role.equals(UserRole.CUSTOMER)) {
            return new CustomerProductDetailFetcher(productRepository,productMapper);
        } else if (role.equals(UserRole.MANAGER)) {
            return new ManagerProductDetailFetcher(productRepository);
        } else {
            throw new IllegalArgumentException("Invalid role: " + role);
        }
    }
}
