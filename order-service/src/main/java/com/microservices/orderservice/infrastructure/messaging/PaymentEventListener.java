package com.microservices.orderservice.infrastructure.messaging;

import com.microservices.orderservice.application.CancelOrderUseCase;
import com.microservices.orderservice.application.ConfirmOrderUseCase;
import com.microservices.orderservice.application.PaymentProcessedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventListener {
    private final ConfirmOrderUseCase confirmOrderUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;

    public PaymentEventListener(ConfirmOrderUseCase confirmOrderUseCase, CancelOrderUseCase cancelOrderUseCase) {
        this.confirmOrderUseCase = confirmOrderUseCase;
        this.cancelOrderUseCase = cancelOrderUseCase;
    }

    @KafkaListener(topics = "payment-processed")
    public void handlePaymentProcessed(PaymentProcessedEvent event) {
        if ("APPROVED".equals(event.status())) {
            confirmOrderUseCase.execute(event.orderId());
        }
    }

    @KafkaListener(topics = "payment-failed")
    public void handlePaymentFailed(PaymentProcessedEvent event) {
        cancelOrderUseCase.execute(event.orderId());
    }
}
