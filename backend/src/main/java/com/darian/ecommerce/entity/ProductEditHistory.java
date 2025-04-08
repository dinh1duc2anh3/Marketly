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
public class ProductEditHistory {
    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Product being edited (1-* relationship)
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // ID or name of the editor
    private String editor;  // Người sửa

    // Description of changes made
    private String changes; // Nội dung chỉnh sửa

    // Date of the edit
    private Date editDate;
}
