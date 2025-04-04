package com.darian.ecommerce.businesslogic.productlist;

import com.darian.ecommerce.dto.ProductDTO;

import java.util.List;

public interface ProductListFetcher {
    // Fetch a list of products, returning ProductDTO or its subtypes
    <T extends ProductDTO> List<T> fetchProductList();
}
