package com.darian.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class RelatedProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product; // Sản phẩm gốc

    @ManyToOne
    @JoinColumn(name = "related_product_id")
    private Product relatedProduct; // Sản phẩm liên quan được gán

    private String relationType; // (Tùy chọn) Loại quan hệ: "SIMILAR", "FREQUENTLY_BOUGHT_TOGETHER", v.v.
}
