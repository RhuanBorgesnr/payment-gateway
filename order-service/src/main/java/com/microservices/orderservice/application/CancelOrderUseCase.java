package com.microservices.orderservice.application;

import com.microservices.orderservice.domain.Order;
import com.microservices.orderservice.domain.OrderRepository;
import java.util.UUID;

public class CancelOrderUseCase {
    private final OrderRepository repository;

    public CancelOrderUseCase(OrderRepository repository) {
        this.repository = repository;
    }

    public void execute(UUID orderId) {
        Order order = repository.findById(orderId);
        if (order != null) {
            order.cancel();
            repository.save(order);
        }
    }
}
