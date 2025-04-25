package com.darian.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_image")
public class ProductImage {
    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    // Product this image belongs to (1-* relationship)
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // URL of the image
    @Column(name = "image_url")
    private String url;


}
