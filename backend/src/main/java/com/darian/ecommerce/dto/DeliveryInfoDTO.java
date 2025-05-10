package com.darian.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryInfoDTO {
    private String recipientName;
    private String phoneNumber;
    private String email;
    private String provinceCity;
    private String address;
    private String shippingInstructions;
}
