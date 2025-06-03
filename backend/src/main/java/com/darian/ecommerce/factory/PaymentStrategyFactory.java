package com.darian.ecommerce.factory;

import com.darian.ecommerce.enums.PaymentMethod;
import com.darian.ecommerce.strategy.CreditCardStrategy;
import com.darian.ecommerce.strategy.DomesticCardStrategy;
import com.darian.ecommerce.strategy.PaymentStrategy;
import org.springframework.stereotype.Component;

@Component
public class PaymentStrategyFactory {
    private final CreditCardStrategy creditCardStrategy;
    private final DomesticCardStrategy domesticCardStrategy;

    public PaymentStrategyFactory(CreditCardStrategy creditCardStrategy, 
                                DomesticCardStrategy domesticCardStrategy) {
        this.creditCardStrategy = creditCardStrategy;
        this.domesticCardStrategy = domesticCardStrategy;
    }

    public PaymentStrategy createPaymentStrategy(PaymentMethod paymentMethod) {
        return switch (paymentMethod) {
            case CREDIT_CARD -> creditCardStrategy;
            case DOMESTIC_CARD -> domesticCardStrategy;
            default -> throw new IllegalArgumentException("Unsupported payment method: " + paymentMethod);
        };
    }
} 