package com.microservices.paymentservice.application;

import java.util.UUID;

public record PaymentProcessedEvent(UUID orderId, String status) {
}
