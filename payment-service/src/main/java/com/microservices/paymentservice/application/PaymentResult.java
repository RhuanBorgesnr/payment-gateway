package com.microservices.paymentservice.application;

import com.microservices.paymentservice.domain.PaymentStatus;
import java.util.UUID;

public record PaymentResult(UUID paymentId, UUID orderId, PaymentStatus status) {
}
