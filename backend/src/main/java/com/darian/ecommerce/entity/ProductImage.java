package com.darian.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage {
    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // URL of the image
    private String url;

    // Product this image belongs to (1-* relationship)
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
