package com.microservices.paymentservice.infrastructure.messaging;

import com.microservices.paymentservice.application.OrderCreatedEvent;
import com.microservices.paymentservice.application.PaymentEventPublisher;
import com.microservices.paymentservice.application.PaymentProcessedEvent;
import com.microservices.paymentservice.application.ProcessPaymentUseCase;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentListener {
    private final ProcessPaymentUseCase processPaymentUseCase;
    private final PaymentEventPublisher eventPublisher;

    public PaymentListener(ProcessPaymentUseCase processPaymentUseCase, PaymentEventPublisher eventPublisher) {
        this.processPaymentUseCase = processPaymentUseCase;
        this.eventPublisher = eventPublisher;
    }

    @KafkaListener(topics = "order-created")
    public void process(OrderCreatedEvent event) {
        try {
            var result = processPaymentUseCase.execute(event.orderId(), 100.0);
            
            if (result.status().name().equals("APPROVED")) {
                eventPublisher.publishPaymentProcessed(
                    new PaymentProcessedEvent(result.orderId(), "APPROVED")
                );
            } else {
                eventPublisher.publishPaymentFailed(
                    new PaymentProcessedEvent(result.orderId(), "REJECTED")
                );
            }
        } catch (Exception e) {
            eventPublisher.publishPaymentFailed(
                new PaymentProcessedEvent(event.orderId(), "FAILED")
            );
        }
    }
}
