package com.microservices.orderservice.application;

import com.microservices.orderservice.domain.Order;
import com.microservices.orderservice.domain.OrderRepository;
import java.util.UUID;

public class CreateOrderUseCase {
    private final OrderRepository repository;
    private final OrderEventPublisher publisher;

    public CreateOrderUseCase(OrderRepository repository, OrderEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    public UUID execute() {
        Order order = new Order(UUID.randomUUID());
        repository.save(order);
        publisher.publish(new OrderCreatedEvent(order.getId()));
        return order.getId();
    }
}
