package com.wildy.fraud_detection.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wildy.fraud_detection.dto.*;
import com.wildy.fraud_detection.kafka.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FraudDetectionService {

    private final KafkaProducerService kafkaProducerService;
    private final ObjectMapper objectMapper;

    // Handle Payment Events
    public void handlePaymentEvent(PaymentEvent paymentEvent) {
        // Example fraud detection logic for payments
        boolean isFraudulent = paymentEvent.getAmount() > 1000 && "high-risk-country".equals(paymentEvent.getMetadata().getDescription());

        if (isFraudulent) {
            // Serialize payment event to JSON string
            String paymentEventJson = convertToJson(paymentEvent);

            // Create FraudAlert with serialized event data
            FraudAlert fraudAlert = new FraudAlert(paymentEvent.getId(), "Potential fraud detected in payment", paymentEventJson);
            kafkaProducerService.sendEvent("fraud-alerts-topic", fraudAlert.toString());
        }
    }

    // Handle Dispute Events
    public void handleDisputeEvent(DisputeEvent disputeEvent) {
        // Example fraud detection logic for disputes
        boolean isHighRiskDispute = disputeEvent.getAmount() > 500 && "fraudulent_reason".equals(disputeEvent.getReason());

        if (isHighRiskDispute) {
            // Serialize dispute event to JSON string
            String disputeEventJson = convertToJson(disputeEvent);

            // Create FraudAlert with serialized event data
            FraudAlert fraudAlert = new FraudAlert(disputeEvent.getId(), "Potential fraud detected in dispute", disputeEventJson);
            kafkaProducerService.sendEvent("fraud-alerts-topic", fraudAlert.toString());
        }
    }

    // Handle Customer Events
    public void handleCustomerEvent(CustomerEvent customerEvent) {
        // Example fraud detection logic for customer activity
        boolean isSuspiciousCustomer = customerEvent.getEmail().endsWith("suspicious.com") || "high-risk-country".equals(customerEvent.getMetadata().getNotes());

        if (isSuspiciousCustomer) {
            // Serialize customer event to JSON string
            String customerEventJson = convertToJson(customerEvent);

            // Create FraudAlert with serialized event data
            FraudAlert fraudAlert = new FraudAlert(customerEvent.getId(), "Suspicious customer activity detected", customerEventJson);
            kafkaProducerService.sendEvent("fraud-alerts-topic", fraudAlert.toString());
        }
    }

    // Handle Refund Events
    public void handleRefundEvent(RefundEvent refundEvent) {
        // Example fraud detection logic for refunds
        boolean isLargeRefund = refundEvent.getAmount() > 500 && "fraudulent".equals(refundEvent.getReason());

        if (isLargeRefund) {
            // Serialize refund event to JSON string
            String refundEventJson = convertToJson(refundEvent);

            // Create FraudAlert with serialized event data
            FraudAlert fraudAlert = new FraudAlert(refundEvent.getId(), "Potential fraud detected in refund", refundEventJson);
            kafkaProducerService.sendEvent("fraud-alerts-topic", fraudAlert.toString());
        }
    }

    // Handle Payout Events
    public void handlePayoutEvent(PayoutEvent payoutEvent) {
        // Example fraud detection logic for payouts
        boolean isSuspiciousPayout = payoutEvent.getAmount() > 1000 && !"usd".equals(payoutEvent.getCurrency());

        if (isSuspiciousPayout) {
            // Serialize payout event to JSON string
            String payoutEventJson = convertToJson(payoutEvent);

            // Create FraudAlert with serialized event data
            FraudAlert fraudAlert = new FraudAlert(payoutEvent.getId(), "Potential fraud detected in payout", payoutEventJson);
            kafkaProducerService.sendEvent("fraud-alerts-topic", fraudAlert.toString());
        }
    }

    // Helper method to convert event to JSON
    private String convertToJson(Object event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}"; // Return empty object if serialization fails
        }
    }

}








