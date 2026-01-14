package com.microservices.orderservice.application;

public interface OrderEventPublisher {
    void publish(OrderCreatedEvent event);
}
