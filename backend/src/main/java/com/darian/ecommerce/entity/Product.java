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

public class Product {
    // Primary key
    @Id
    private String productId;

    // Name of the product
    private String name;

    // Description of the product
    private String description;

    // Price of the product
    private float price;

    // Value (could be cost or another metric)
    private float value;

    // Barcode of the product
    private String barcode;

    // Quantity in stock
    private int stockQuantity;

    // Date the product entered the warehouse
    private Date warehouseEntryDate;

    // List of edit history records (1-* relationship)
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true) //dòng này có đúng ko nhỉ ?
    private List<ProductEditHistory> editHistory;

    // Product specifications
    private String specifications;

    // List of product images (1-* relationship)
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    /*
    OneToMany: Một sản phẩm có thể có nhiều ảnh.- CascadeType.ALL: Khi xóa sản phẩm, ảnh cũng bị xóa theo.
    FetchType.LAZY: Chỉ tải ảnh khi cần thiết (tăng hiệu suất). - orphanRemoval = true: Nếu ảnh không còn liên kết với sản phẩm nào, nó sẽ bị xóa.
     */
    private List<ProductImage> images;

    // Category of the product (1-1 relationship)
    @JoinColumn(name = "category_id")
    @ManyToOne
    private Category category;
}
