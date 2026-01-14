package com.microservices.orderservice.infrastructure.persistence;

import com.microservices.orderservice.domain.Order;
import com.microservices.orderservice.domain.OrderRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderRepositoryAdapter implements OrderRepository {
    private final OrderJpaRepository jpaRepository;

    public OrderRepositoryAdapter(OrderJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Order order) {
        jpaRepository.save(order);
    }

    @Override
    public Order findById(UUID id) {
        return jpaRepository.findById(id).orElse(null);
    }
}
