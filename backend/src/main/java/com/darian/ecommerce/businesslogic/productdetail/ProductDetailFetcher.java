package com.darian.ecommerce.businesslogic.productdetail;

import com.darian.ecommerce.dto.ProductDTO;

public interface ProductDetailFetcher {
    // Fetch product details by product ID, returning ProductDTO or its subtypes
    <T extends ProductDTO> T fetchProductDetails(Long productId);
}
