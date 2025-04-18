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

@Table(name = "product")
public class Product {
    // Primary key
    @Id
    @Column(name = "product_id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)  tôi muốn product của tôi là string ,  chứ ko phỉa là long, và nó sẽ được sinh ra tự động dựa trên nguyên tắc : tên có chữ cái + số , kiểu pr123 hay gì đso ấy

    private String productId;

    // Name of the product
    @Column(name = "product_name", nullable = false)
    private String name;

    // Description of the product
    @Column(name = "product_description")
    private String description;

    // Price of the product
    @Column(name = "product_price")
    private float price;

    // Value (could be cost or another metric)
    @Column(name = "product_value")
    private float value;

    // Barcode of the product
    @Column(name = "product_barcode")
    private String barcode;

    // Quantity in stock
    @Column(name = "stock_quantity")
    private int stockQuantity;

    // Date the product entered the warehouse
    @Column(name = "warehouse_entry_date")
    private Date warehouseEntryDate;

    // List of edit history records (1-* relationship)
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true) //dòng này có đúng ko nhỉ ?
    private List<ProductEditHistory> editHistory;

    // Product specifications
    @Column(name = "product_specification")
    private String specifications;

    // List of product images (1-* relationship)
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    /*
    OneToMany: Một sản phẩm có thể có nhiều ảnh.- CascadeType.ALL: Khi xóa sản phẩm, ảnh cũng bị xóa theo.
    FetchType.LAZY: Chỉ tải ảnh khi cần thiết (tăng hiệu suất). - orphanRemoval = true: Nếu ảnh không còn liên kết với sản phẩm nào, nó sẽ bị xóa.
     */
    private List<ProductImage> images;

    // Category of the product (1-1 relationship)
    @JoinColumn(name = "category_id", nullable = false)
    @ManyToOne
    private Category category;
}
