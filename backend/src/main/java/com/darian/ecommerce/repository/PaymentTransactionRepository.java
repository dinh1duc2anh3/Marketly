package com.darian.ecommerce.repository;

import com.darian.ecommerce.entity.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
    // Functional Cohesion: Chỉ cung cấp các method thao tác dữ liệu giao dịch thanh toán
    // Không vi phạm SRP: Không xử lý logic nghiệp vụ, chỉ thao tác với DB

    public PaymentTransaction save(PaymentTransaction payment);

    public Optional<PaymentTransaction> findByOrderId(Long orderId);

    public PaymentTransaction findByTransaction(String transactionId);
}
