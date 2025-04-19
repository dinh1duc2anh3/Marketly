package com.darian.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

//nay ko phai la mot entity duoc luu trong db
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    @Id
    private Long orderId;
    private Float amount;
    private String content;
}
