package com.darian.ecommerce.businesslogic.shippingfee;

import com.darian.ecommerce.dto.BaseOrderDTO;

public class RushShippingFeeCalculator implements ShippingFeeCalculator{
    // Calculate rush shipping fee (example logic)
    @Override
    public Integer calculateShippingFee(BaseOrderDTO dto) {
        // Higher base fee for rush delivery
        return 20 + (dto.getSubtotal() > 100 ? 10 : 0);
    }
}
