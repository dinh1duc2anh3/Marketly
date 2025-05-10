package com.darian.ecommerce.businesslogic.mapper;

import com.darian.ecommerce.dto.RelatedProductDTO;
import com.darian.ecommerce.entity.Product;

public class RelatedProductMapper {

    // Chuyển từ Product sang RelatedProductDTO
    public RelatedProductDTO mapToRelatedProductDTO(Product relatedProduct) {
        return RelatedProductDTO.builder()
                .productId(relatedProduct.getProductId())
                .name(relatedProduct.getName())
                .price(relatedProduct.getPrice())
                .imageUrl(relatedProduct.getImages().isEmpty() ?
                        "default.jpg" : relatedProduct.getImages().getFirst().getUrl())
                .build();
    }
}
