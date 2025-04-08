package com.darian.ecommerce.businesslogic.shippingfee;

import com.darian.ecommerce.dto.BaseOrderDTO;
import com.darian.ecommerce.dto.RushOrderDTO;

public class ShippingFeeCalculatorFactory {
    // Get the appropriate calculator based on order type
    public ShippingFeeCalculator getCalculator(BaseOrderDTO dto) {
        if (dto instanceof RushOrderDTO) {
            return new RushShippingFeeCalculator();
        }
        return new StandardShippingFeeCalculator();
    }
}
