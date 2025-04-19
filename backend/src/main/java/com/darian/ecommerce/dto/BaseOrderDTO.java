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
    private Long orderId;
    private Integer customerId;
    private List<OrderItemDTO> items;
    private String status;
    private DeliveryInfoDTO deliveryInfo;
    private Float subtotal;
    private Float shippingFee;
    private Float total;
    private LocalDateTime createdDate;
}
