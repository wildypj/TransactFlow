package com.wildy.fraud_detection.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DisputeEvent {
    private String eventType;
    private String id;
    private Integer amount;           // Smallest currency unit
    private String currency;
    private String reason;
    private String status;
    private String charge;
    private String paymentIntent;     // Payment Intent associated with the dispute
    private PaymentMethodDetails paymentMethodDetails;
    @JsonProperty("metadata")
    private Metadata metadata;
    private Evidence evidence;
    private EvidenceDetails evidenceDetails;
    private Boolean isChargeRefundable;
    private Boolean livemode;         // Indicates whether the event is in live mode
    @JsonProperty("timestamp")
    private String created;           // Timestamp of when the dispute was created

    @Data
    public static class Metadata {
        private String foo;  // Just an example, you can change this based on your actual metadata
    }

    @Data
    public static class PaymentMethodDetails {
        private Card card;
        private String type; // Payment method type (e.g., "card")

        @Data
        public static class Card {
            private String brand;
            private String caseType;
            private String networkReasonCode;
        }
    }

    @Data
    public static class Evidence {
        @JsonProperty("access_activity_log")
        private String accessActivityLog;
        @JsonProperty("billing_address")
        private String billingAddress;
        @JsonProperty("customer_email_address")
        private String customerEmailAddress;
        @JsonProperty("customer_name")
        private String customerName;
        @JsonProperty("duplicate_charge_documentation")
        private String duplicateChargeDocumentation;
        @JsonProperty("product_description")
        private String productDescription;
        @JsonProperty("refund_policy")
        private String refundPolicy;
        @JsonProperty("shipping_address")
        private String shippingAddress;
        @JsonProperty("shipping_tracking_number")
        private String shippingTrackingNumber;
        // Add more fields from the "evidence" object as needed
    }

    @Data
    public static class EvidenceDetails {
        private String dueBy;  // Due timestamp for submitting evidence
        private Boolean hasEvidence;
        private Boolean pastDue;
        private Integer submissionCount;
    }
}
