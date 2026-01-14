package com.microservices.paymentservice.infrastructure.messaging;

import com.microservices.paymentservice.application.PaymentEventPublisher;
import com.microservices.paymentservice.application.PaymentProcessedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaPaymentEventPublisher implements PaymentEventPublisher {
    private static final String PAYMENT_PROCESSED_TOPIC = "payment-processed";
    private static final String PAYMENT_FAILED_TOPIC = "payment-failed";
    private final KafkaTemplate<String, PaymentProcessedEvent> kafkaTemplate;

    public KafkaPaymentEventPublisher(KafkaTemplate<String, PaymentProcessedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publishPaymentProcessed(PaymentProcessedEvent event) {
        kafkaTemplate.send(PAYMENT_PROCESSED_TOPIC, event);
    }

    @Override
    public void publishPaymentFailed(PaymentProcessedEvent event) {
        kafkaTemplate.send(PAYMENT_FAILED_TOPIC, event);
    }
}
