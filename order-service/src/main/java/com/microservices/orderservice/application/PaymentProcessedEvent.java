package com.microservices.orderservice.application;

import java.util.UUID;

public record PaymentProcessedEvent(UUID orderId, String status) {
}
