package com.microservices.orderservice.infrastructure.config;

import com.microservices.orderservice.application.CancelOrderUseCase;
import com.microservices.orderservice.application.ConfirmOrderUseCase;
import com.microservices.orderservice.application.CreateOrderUseCase;
import com.microservices.orderservice.application.OrderEventPublisher;
import com.microservices.orderservice.domain.OrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {
    @Bean
    public CreateOrderUseCase createOrderUseCase(OrderRepository repository, OrderEventPublisher publisher) {
        return new CreateOrderUseCase(repository, publisher);
    }

    @Bean
    public ConfirmOrderUseCase confirmOrderUseCase(OrderRepository repository) {
        return new ConfirmOrderUseCase(repository);
    }

    @Bean
    public CancelOrderUseCase cancelOrderUseCase(OrderRepository repository) {
        return new CancelOrderUseCase(repository);
    }
}
