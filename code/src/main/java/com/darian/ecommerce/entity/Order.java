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
public class Order {
    @Id
    private String orderId;
    private String customerId;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;
    private String status;
    @OneToOne(cascade = CascadeType.ALL)
    private DeliveryInfo deliveryInfo;
    private float subtotal;
    private float shippingFee;
    private float total;
    private Date createdDate;
    private float discount;

    // method getSubtotal , getVAT , getTotal
    public float getSubtotal(){
    }

    public float getVAT(){
        return getSubtotal() * 0.1f;
    }

    public float getTotal(){
        return getSubtotal() + shippingFee - discount;
    }


}
