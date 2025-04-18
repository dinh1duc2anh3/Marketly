package com.darian.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "transaction_type")
    private String transactionType;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "total_amount")
    private float totalAmount;

    @Column(name = "transaction_content")
    private String transactionContent;

    @Column(name = "transaction_date")
    private Date transactionDate;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "refund_status")
    private String refundStatus;

    @Column(name = "refund_date")
    private Date refundDate;

}
