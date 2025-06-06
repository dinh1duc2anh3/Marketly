package com.darian.ecommerce.businesslogic.shippingfee;

import com.darian.ecommerce.dto.BaseOrderDTO;

public interface ShippingFeeCalculator {
    // Calculate shipping fee based on order details
    Float calculateShippingFee(BaseOrderDTO dto);
}
