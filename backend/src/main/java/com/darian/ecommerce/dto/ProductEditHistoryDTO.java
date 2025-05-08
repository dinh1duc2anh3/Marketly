package com.darian.ecommerce.dto;

import java.util.Date;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductEditHistoryDTO {
    private Date editDate;
    private String editor;
    private String changes;
}
