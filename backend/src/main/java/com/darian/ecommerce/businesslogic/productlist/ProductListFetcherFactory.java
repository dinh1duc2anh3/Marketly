package com.darian.ecommerce.businesslogic.productlist;

import com.darian.ecommerce.businesslogic.mapper.ProductMapper;
import com.darian.ecommerce.enums.UserRole;
import com.darian.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductListFetcherFactory {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    // Constructor injection for ProductRepository
    public ProductListFetcherFactory(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    // Get the appropriate ProductListFetcher based on role
    public ProductListFetcher getFetcher(UserRole role) {
        if (role.equals(UserRole.CUSTOMER)) {
            return new CustomerProductListFetcher(productRepository,productMapper);
        } else if (role.equals(UserRole.MANAGER)) {
            return new ManagerProductListFetcher(productRepository);
        } else {
            throw new IllegalArgumentException("Invalid role: " + role);
        }
    }
}
