package com.darian.ecommerce.dto;

import lombok.*;

import java.time.LocalDateTime;
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
    private float subtotal;
    private float shippingFee;
    private float total;
    private LocalDateTime createdDate;
}
