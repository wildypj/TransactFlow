package com.wildy.fraud_detection.dto;

import lombok.Data;

@Data
public class RefundEvent {

    private String eventType;         // Type of event (e.g., "refund.created", "refund.updated", "refund.failed")
    private String id;                // Refund ID (e.g., "re_1Nispe2eZvKYlo2Cd31jOCgZ")
    private String timestamp;         // Timestamp of the event (in seconds)
    private Integer amount;           // Refund amount (smallest currency unit, e.g., 1000 = $10.00)
    private String currency;          // Currency of the refund (e.g., "usd")
    private String status;            // Status of the refund (e.g., "succeeded", "failed")
    private String reason;            // Reason for the refund (nullable)
    private String customerId;        // Customer ID (if available, e.g., "cus_123456")
    private String chargeId;          // Charge ID related to this refund
    private String paymentIntentId;   // Payment intent ID related to this refund
    private DestinationDetails destinationDetails; // Destination details for the refund
    private Metadata metadata;        // Metadata related to the refund

    @Data
    public static class Metadata {
        private String orderId;         // Order ID (optional, depending on the event)
        private String refundReason;    // Refund reason (nullable)
    }

    @Data
    public static class DestinationDetails {
        private String type;            // Type of destination (e.g., "card")
        private Card card;              // Card details (nested object if type is "card")
    }

    @Data
    public static class Card {
        private String reference;       // Reference number for the card (e.g., "123456789012")
        private String referenceStatus; // Reference status (e.g., "available")
        private String referenceType;   // Reference type (e.g., "acquirer_reference_number")
        private String type;            // Type of refund (e.g., "refund")
    }

}

//    private String eventType;
//    private String id;
//    private String timestamp;
//    private Integer amount; // Smallest currency unit
//    private String currency;
//    private String status;
//    private String reason;
//    private String customerId;
//    private Metadata metadata;
//
//    @Data
//    public static class Metadata {
//        private String orderId;
//        private String refundReason;
//    }
