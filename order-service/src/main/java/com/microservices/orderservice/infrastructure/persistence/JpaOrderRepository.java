package com.microservices.orderservice.infrastructure.persistence;

import com.microservices.orderservice.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface OrderJpaRepository extends JpaRepository<Order, UUID> {
}
