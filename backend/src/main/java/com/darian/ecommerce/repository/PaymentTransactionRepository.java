package com.darian.ecommerce.repository;

import com.darian.ecommerce.entity.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
    public PaymentTransaction save(PaymentTransaction payment);

    public Optional<PaymentTransaction> findByOrderId(Long orderId);

    public PaymentTransaction findByTransaction(String transactionId);
}
