package com.darian.ecommerce.repository;

import com.darian.ecommerce.entity.PaymentTransaction;

import java.util.Optional;

public interface PaymentTransactionRepository {
    public PaymentTransaction save(PaymentTransaction payment){

    }

    public Optional<PaymentTransaction> findByOrderId(String orderId){

    }

    public PaymentTransaction findByTransaction(String transactionId){

    }
}
