package com.darian.ecommerce.businesslogic.productdetail;

import com.darian.ecommerce.dto.ProductDTO;

public interface ProductDetailFetcher<T extends ProductDTO> {
    // Fetch product details by product ID, returning ProductDTO or its subtypes
     T fetchProductDetails(Long productId);
}
