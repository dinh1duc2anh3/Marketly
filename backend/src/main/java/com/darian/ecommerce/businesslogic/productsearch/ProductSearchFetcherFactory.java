package com.darian.ecommerce.businesslogic.productsearch;

import com.darian.ecommerce.repository.ProductRepository;

public class ProductSearchFetcherFactory {
    private final ProductRepository productRepository;

    // Constructor injection for ProductRepository
    public ProductSearchFetcherFactory(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Get the appropriate ProductSearchFetcher based on role
    public ProductSearchFetcher getFetcher(String role) {
        if ("CUSTOMER".equalsIgnoreCase(role)) {
            return new CustomerProductSearchFetcher(productRepository);
        } else if ("MANAGER".equalsIgnoreCase(role)) {
            return new ManagerProductSearchFetcher(productRepository);
        } else {
            throw new IllegalArgumentException("Invalid role: " + role);
        }
    }
}
