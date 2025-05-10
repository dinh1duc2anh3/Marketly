package com.darian.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductEditHistoryDTO {
    private LocalDateTime editDate;
    private String editor;
    private String changes;
}
