package com.darian.ecommerce.businesslogic.productsearch;

import com.darian.ecommerce.dto.ProductDTO;

import java.util.List;

public interface ProductSearchFetcher {
    // Search products by keyword, returning a list of ProductDTO or its subtypes
    <T extends ProductDTO> List<T> searchProducts(String keyword);
}
