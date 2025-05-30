package com.darian.ecommerce.service;

import com.darian.ecommerce.dto.RelatedProductDTO;
import com.darian.ecommerce.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RelatedProductService {
    // Suggest related products based on a product ID
    List<RelatedProductDTO> suggestRelatedProducts(Long productId);

    // get related products based on a product ID
    List<Product> getRelatedProducts(Long productId);
}
