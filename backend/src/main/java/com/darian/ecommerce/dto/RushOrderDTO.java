package com.darian.ecommerce.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RushOrderDTO extends BaseOrderDTO {
    private LocalDateTime rushDeliveryTime;
}
