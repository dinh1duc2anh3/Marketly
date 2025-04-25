package com.darian.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "product")
public class Product {
    // Primary key
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    // Category of the product (1-1 relationship)
    @JoinColumn(name = "category_id", nullable = false)
    @ManyToOne
    private Category category;

    // Name of the product
    @Column(name = "product_name", nullable = false)
    private String name;

    // Price of the product
    @Column(name = "product_price")
    private Float price;

    // Value (could be cost or another metric)
    @Column(name = "product_value")
    private Float value;

    // Barcode of the product
    @Column(name = "product_barcode")
    private String barcode;

    // Description of the product
    @Column(name = "product_description")
    private String description;

    // Product specifications
    @Column(name = "product_specification")
    private String specifications;

    // Quantity in stock
    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    // Date the product entered the warehouse
    @Column(name = "warehouse_entry_timestamp")
    private LocalDateTime warehouseEntryDate;

    // List of edit history records (1-* relationship)
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true) //dòng này có đúng ko nhỉ ?
    private List<ProductEditHistory> editHistory;

    // List of product images (1-* relationship)
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    /*
    OneToMany: Một sản phẩm có thể có nhiều ảnh.- CascadeType.ALL: Khi xóa sản phẩm, ảnh cũng bị xóa theo.
    FetchType.LAZY: Chỉ tải ảnh khi cần thiết (tăng hiệu suất). - orphanRemoval = true: Nếu ảnh không còn liên kết với sản phẩm nào, nó sẽ bị xóa.
     */
    private List<ProductImage> images;


}
