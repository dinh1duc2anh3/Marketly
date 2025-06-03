package com.darian.ecommerce.businesslogic.mapper.productmapper;

import com.darian.ecommerce.dto.RelatedProductDTO;
import com.darian.ecommerce.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class RelatedProductMapper {

    // Chuyển từ Product sang RelatedProductDTO
    public RelatedProductDTO toDTO(Product relatedProduct) {
        return RelatedProductDTO.builder()
                .productId(relatedProduct.getProductId())
                .name(relatedProduct.getName())
                .price(relatedProduct.getPrice())
                .imageUrl(relatedProduct.getImages() == null || relatedProduct.getImages().isEmpty() ? 
                        "default.jpg" : relatedProduct.getImages().get(0).getUrl())
                .build();
    }
}
