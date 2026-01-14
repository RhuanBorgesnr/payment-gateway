package com.microservices.orderservice.application;

import java.util.UUID;

public record OrderCreatedEvent(UUID orderId) {
}
