package com.microservices.paymentservice.application;

public interface PaymentEventPublisher {
    void publishPaymentProcessed(PaymentProcessedEvent event);
    void publishPaymentFailed(PaymentProcessedEvent event);
}
