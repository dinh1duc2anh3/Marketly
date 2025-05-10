package com.darian.ecommerce.dto;

import com.darian.ecommerce.enums.OrderStatus;
import com.darian.ecommerce.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseOrderDTO {
    private Long orderId;
    private Integer customerId;

    private OrderStatus orderStatus;
    private DeliveryInfoDTO deliveryInfo;
    private Float subtotal;
    private Float shippingFee;
    private Float total;
    private LocalDateTime createdDate;
    private Float discount;
    private PaymentStatus paymentStatus;
    private List<OrderItemDTO> items;
}
