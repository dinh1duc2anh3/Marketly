package com.darian.ecommerce.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RushOrderDTO extends OrderDTO {
    private Date rushDeliveryTime;
}
