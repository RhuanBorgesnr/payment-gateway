package com.microservices.paymentservice.application;

import java.util.UUID;

public record OrderCreatedEvent(UUID orderId) {
}
