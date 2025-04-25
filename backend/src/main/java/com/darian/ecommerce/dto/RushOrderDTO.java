package com.darian.ecommerce.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RushOrderDTO extends BaseOrderDTO {
    private LocalDateTime rushDeliveryTime;
}
