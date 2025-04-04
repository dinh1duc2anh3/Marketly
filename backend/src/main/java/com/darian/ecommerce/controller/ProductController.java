package com.darian.ecommerce.controller;

import com.darian.ecommerce.dto.ManagerProductDTO;
import com.darian.ecommerce.dto.ProductDTO;
import com.darian.ecommerce.service.impl.ProductServiceImpl;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ProductController {
    private ProductServiceImpl productService;

    public ResponseEntity<List<ProductDTO>> getProductList(String role){

    }

    public ResponseEntity<? extends ProductDTO> getProductDetails(String userId, String productId, String role){

    }

    public ResponseEntity<? extends ProductDTO> searchProducts(String userId, String keyword, String role){

    }

    public ResponseEntity<ManagerProductDTO> addProduct(ProductDTO productDTO){

    }

    public ResponseEntity<Void> deleteProduct(String productId){

    }

    public ResponseEntity<ManagerProductDTO> updateProduct(String productId, ProductDTO productDTO){

    }

    public ResponseEntity<List<ProductDTO>> getRelaetedProducts(String productId){

    }

}
