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
public class Product {
    @Id
    private String productId;

    private String name;
    private String description;
    private float price;
    private float value;
    private String barcode;
    private int stockQuantity;
    private Date warehouseEntryDate;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true) //dòng này có đúng ko nhỉ ?
    private List<ProductEditHistory> editHistory;

    private String specifications;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    /*
    OneToMany: Một sản phẩm có thể có nhiều ảnh.- CascadeType.ALL: Khi xóa sản phẩm, ảnh cũng bị xóa theo.
    FetchType.LAZY: Chỉ tải ảnh khi cần thiết (tăng hiệu suất). - orphanRemoval = true: Nếu ảnh không còn liên kết với sản phẩm nào, nó sẽ bị xóa.
     */
    private List<ProductImage> images;

    @ManyToOne
    private Category category;
}
