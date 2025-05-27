package com.darian.ecommerce.service.impl;

import com.darian.ecommerce.businesslogic.mapper.productmapper.RelatedProductMapper;
import com.darian.ecommerce.dto.RelatedProductDTO;
import com.darian.ecommerce.entity.Product;
import com.darian.ecommerce.repository.RelatedProductRepository;
import com.darian.ecommerce.service.RelatedProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RelatedProductServiceImpl implements RelatedProductService {
    // Logger for logging actions and errors
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);


    // Dependencies injected via constructor
    private final RelatedProductRepository relatedProductRepository;
    private final RelatedProductMapper relatedProductMapper;

    public RelatedProductServiceImpl(RelatedProductMapper relatedProductMapper, RelatedProductRepository relatedProductRepository, RelatedProductMapper relatedProductMapper1) {
        this.relatedProductRepository = relatedProductRepository;
        this.relatedProductMapper = relatedProductMapper1;
    }

    // Suggest related products based on product ID
    @Override
    public List<RelatedProductDTO> suggestRelatedProducts(Long productId) {
        logger.info("Suggesting related products for productId: {}", productId);
        List<Product> relatedProducts = relatedProductRepository.findRelatedProductsByProductId(productId);
        return relatedProducts.stream().map(relatedProductMapper::toDTO).toList();

    }

    @Override
    public List<Product> getRelatedProducts(Long productId) {
        logger.info("Getting related products for productId: {}", productId);
        List<Product> relatedProducts = relatedProductRepository.findRelatedProductsByProductId(productId);
        return relatedProducts;
    }



}
