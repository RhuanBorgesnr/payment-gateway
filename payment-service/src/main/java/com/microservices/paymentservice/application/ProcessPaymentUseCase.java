package com.microservices.paymentservice.application;

import com.microservices.paymentservice.domain.Payment;
import com.microservices.paymentservice.domain.PaymentStatus;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class ProcessPaymentUseCase {
    private static final String PAYMENT_SERVICE = "paymentService";
    private final Random random = new Random();

    @CircuitBreaker(name = PAYMENT_SERVICE, fallbackMethod = "processPaymentFallback")
    @Retry(name = PAYMENT_SERVICE)
    public PaymentResult execute(UUID orderId, Double amount) {
        Payment payment = new Payment(orderId, amount);
        
        boolean success = simulatePaymentProcessing();
        
        if (success) {
            payment.approve();
            return new PaymentResult(payment.getId(), payment.getOrderId(), PaymentStatus.APPROVED);
        } else {
            payment.reject();
            return new PaymentResult(payment.getId(), payment.getOrderId(), PaymentStatus.REJECTED);
        }
    }

    private boolean simulatePaymentProcessing() {
        int randomValue = random.nextInt(100);
        return randomValue > 20;
    }

    public PaymentResult processPaymentFallback(UUID orderId, Double amount, Exception ex) {
        return new PaymentResult(null, orderId, PaymentStatus.REJECTED);
    }
}
