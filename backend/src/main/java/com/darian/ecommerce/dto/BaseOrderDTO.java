package com.darian.ecommerce.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseOrderDTO {
    private Long orderId;
    private Integer customerId;

    private String orderStatus;
    private DeliveryInfoDTO deliveryInfo;
    private Float subtotal;
    private Float shippingFee;
    private Float total;
    private LocalDateTime createdDate;
    private List<OrderItemDTO> items;
}
