package com.darian.ecommerce.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseOrderDTO {
    private String orderId;
    private String customerId;
    private List<OrderItemDTO> items;
    private String status;
    private DeliveryInfoDTO deliveryInfo;
    private int subtotal;
    private int shippingFee;
    private int total;
    private Date createdDate;
}
