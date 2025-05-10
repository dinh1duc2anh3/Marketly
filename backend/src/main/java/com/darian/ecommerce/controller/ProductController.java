package com.darian.ecommerce.controller;

import com.darian.ecommerce.dto.ManagerProductDTO;
import com.darian.ecommerce.dto.ProductDTO;
import com.darian.ecommerce.dto.RelatedProductDTO;
import com.darian.ecommerce.enums.UserRole;
import com.darian.ecommerce.service.ProductService;
import com.darian.ecommerce.service.RelatedProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final RelatedProductService relatedProductService;

    public ProductController(ProductService productService, RelatedProductService relatedProductService) {
        this.productService = productService;
        this.relatedProductService = relatedProductService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProductList(@RequestParam UserRole  role) {
        List<ProductDTO> products = productService.getProductList(role);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<? extends ProductDTO> getProductDetails(
            @RequestParam Integer userId,
            @PathVariable Long productId,
            @RequestParam UserRole role) {
        ProductDTO product = productService.getProductDetails(userId, productId, role);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/search")
    public ResponseEntity<List<? extends ProductDTO>> searchProducts(
            @RequestParam Integer userId,
            @RequestParam String keyword,
            @RequestParam UserRole  role) {
        List<ProductDTO> products = productService.searchProducts(userId, keyword, role);
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<ManagerProductDTO> addProduct(
            @RequestParam Integer userId,
            @RequestBody ProductDTO productDTO) {
        ManagerProductDTO createdProduct = productService.addProduct(userId, productDTO);
        return ResponseEntity.ok(createdProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long productId,
            @RequestParam Integer userId) {
        productService.deleteProduct(productId, userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ManagerProductDTO> updateProduct(
            @RequestParam Integer userId,
            @PathVariable Long productId,
            @RequestBody ProductDTO productDTO) {
        ManagerProductDTO updatedProduct = productService.updateProduct(userId, productId, productDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    @GetMapping("/{productId}/related")
    public ResponseEntity<List<RelatedProductDTO>> getRelatedProducts(@PathVariable Long productId) {
        List<RelatedProductDTO> relatedProducts = relatedProductService.suggestRelatedProducts(productId);
        return ResponseEntity.ok(relatedProducts);
    }

}
