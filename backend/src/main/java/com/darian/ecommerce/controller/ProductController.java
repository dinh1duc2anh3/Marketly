package com.darian.ecommerce.controller;

import com.darian.ecommerce.dto.ManagerProductDTO;
import com.darian.ecommerce.dto.ProductDTO;
import com.darian.ecommerce.service.ProductService;
import com.darian.ecommerce.service.impl.ProductServiceImpl;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ProductController {
    private ProductService productService;

    public ResponseEntity<List<ProductDTO>> getProductList(String role){

    }

    public ResponseEntity<? extends ProductDTO> getProductDetails(Integer userId, Long productId, String role){

    }

    public ResponseEntity<? extends ProductDTO> searchProducts(Integer userId, String keyword, String role){

    }

    public ResponseEntity<ManagerProductDTO> addProduct(ProductDTO productDTO){

    }

    public ResponseEntity<Void> deleteProduct(Long productId,Integer userId){

    }

    public ResponseEntity<ManagerProductDTO> updateProduct(Long productId, ProductDTO productDTO){

    }

    public ResponseEntity<List<ProductDTO>> getRelaetedProducts(Long productId){

    }

}
