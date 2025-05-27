package com.darian.ecommerce.businesslogic.shippingfee;

import com.darian.ecommerce.dto.BaseOrderDTO;

public class StandardShippingFeeCalculator  implements ShippingFeeCalculator{
    // Calculate standard shipping fee (example logic)
    @Override
    public Float calculateShippingFee(BaseOrderDTO dto) {
        // Base fee + additional based on subtotal
        return 10f + (dto.getSubtotal() > 100 ? 5f : 0f);
    }
}
