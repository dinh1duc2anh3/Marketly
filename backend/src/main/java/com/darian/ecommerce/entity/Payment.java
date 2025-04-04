package com.darian.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransaction {
    @Id
    private String transactionId;
    private String orderId;
    private float totalAmount;
    private String transactionContent;
    private Date transactionDate;
    private String paymentMethod;
    private String paymentStatus;
    private String refundStatus;
    private Date refundDate;

}
