package com.darian.ecommerce.businesslogic.shippingfee;

import com.darian.ecommerce.dto.BaseOrderDTO;

public class StandardShippingFeeCalculator  implements ShippingFeeCalculator{
    // Calculate standard shipping fee (example logic)
    @Override
    public Integer calculateShippingFee(BaseOrderDTO dto) {
        // Base fee + additional based on subtotal
        return 10 + (dto.getSubtotal() > 100 ? 5 : 0);
    }
}
