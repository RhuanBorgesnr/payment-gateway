package com.microservices.orderservice.application;

import com.microservices.orderservice.domain.Order;
import com.microservices.orderservice.domain.OrderRepository;
import java.util.UUID;

public class ConfirmOrderUseCase {
    private final OrderRepository repository;

    public ConfirmOrderUseCase(OrderRepository repository) {
        this.repository = repository;
    }

    public void execute(UUID orderId) {
        Order order = repository.findById(orderId);
        if (order != null) {
            order.confirm();
            repository.save(order);
        }
    }
}
