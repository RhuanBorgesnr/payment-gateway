package com.microservices.paymentservice.domain;

import java.util.UUID;

public class Payment {
    private UUID id;
    private UUID orderId;
    private PaymentStatus status;
    private Double amount;

    public Payment(UUID orderId, Double amount) {
        this.id = UUID.randomUUID();
        this.orderId = orderId;
        this.amount = amount;
        this.status = PaymentStatus.PENDING;
    }

    public UUID getId() {
        return id;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public Double getAmount() {
        return amount;
    }

    public void approve() {
        this.status = PaymentStatus.APPROVED;
    }

    public void reject() {
        this.status = PaymentStatus.REJECTED;
    }
}
