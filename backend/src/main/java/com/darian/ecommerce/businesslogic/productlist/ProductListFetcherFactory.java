package com.darian.ecommerce.businesslogic.productlist;

import com.darian.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductListFetcherFactory {
    private final ProductRepository productRepository;

    // Constructor injection for ProductRepository
    public ProductListFetcherFactory(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Get the appropriate ProductListFetcher based on role
    public ProductListFetcher getFetcher(String role) {
        if ("CUSTOMER".equalsIgnoreCase(role)) {
            return new CustomerProductListFetcher(productRepository);
        } else if ("MANAGER".equalsIgnoreCase(role)) {
            return new ManagerProductListFetcher(productRepository);
        } else {
            throw new IllegalArgumentException("Invalid role: " + role);
        }
    }
}
