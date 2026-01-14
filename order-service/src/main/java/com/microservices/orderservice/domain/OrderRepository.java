package com.microservices.orderservice.domain;

import java.util.UUID;

public interface OrderRepository {
    void save(Order order);
    Order findById(UUID id);
}
