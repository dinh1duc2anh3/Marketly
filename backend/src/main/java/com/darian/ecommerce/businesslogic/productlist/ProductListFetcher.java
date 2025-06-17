package com.darian.ecommerce.businesslogic.productlist;

import com.darian.ecommerce.product.dto.ProductDTO;

import java.util.List;

public interface ProductListFetcher<T extends ProductDTO> {
    // Fetch a list of products, returning ProductDTO or its subtypes
     List<T> fetchProductList();
}
