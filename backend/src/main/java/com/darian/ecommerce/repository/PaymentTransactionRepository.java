package com.darian.ecommerce.repository;

import com.darian.ecommerce.entity.PaymentTransaction;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentTransactionRepository {
    public PaymentTransaction save(PaymentTransaction payment){

    }

    public Optional<PaymentTransaction> findByOrderId(String orderId){

    }

    public PaymentTransaction findByTransaction(String transactionId){

    }
}
