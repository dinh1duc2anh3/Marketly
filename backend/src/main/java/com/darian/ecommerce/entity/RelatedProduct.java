package com.darian.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class RelatedProduct {
    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The main product (1-* relationship)
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product; // Sản phẩm gốc

    // The related product (1-* relationship)
    @ManyToOne
    @JoinColumn(name = "related_product_id")
    private Product relatedProduct; // Sản phẩm liên quan được gán

    // Type of relation (e.g., "similar", "accessory")
    private String relationType; // (Tùy chọn) Loại quan hệ: "SIMILAR", "FREQUENTLY_BOUGHT_TOGETHER", v.v.
}
