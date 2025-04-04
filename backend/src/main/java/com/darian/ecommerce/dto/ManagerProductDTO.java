package com.darian.ecommerce.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ManagerProductDTO  extends ProductDTO{
    private float value;
    private String barcode;
    private int stockQuantity;
    private Date warehouseEntryDate;
    private List<ProductEditHistoryDTO> editHistory;
}
