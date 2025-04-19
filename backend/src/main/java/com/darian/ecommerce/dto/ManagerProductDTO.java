package com.darian.ecommerce.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ManagerProductDTO  extends ProductDTO{
    private Float value;
    private String barcode;
    private Integer stockQuantity;
    private Date warehouseEntryDate;
    private List<ProductEditHistoryDTO> editHistory;
}
