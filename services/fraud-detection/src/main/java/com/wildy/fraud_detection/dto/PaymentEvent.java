package com.wildy.fraud_detection.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PaymentEvent {

    private String id;
    private Integer amount;
    @JsonProperty("amount_received")
    private Integer amountReceived;
    @JsonProperty("amount_capturable")
    private Integer amountCapturable;
    private String currency;
    private String status;
    @JsonProperty("client_secret")
    private String clientSecret;
    private String description;
    @JsonProperty("latest_charge")
    private String latestCharge;
    @JsonProperty("payment_method")
    private String paymentMethod;
    private List<String> paymentMethodTypes;  // Handling an array of payment methods
    @JsonProperty("capture_method")
    private String captureMethod;
    @JsonProperty("confirmation_method")
    private String confirmationMethod;
    @JsonProperty("created")
    private String created;
    @JsonProperty("shipping")
    private Shipping shipping;
    @JsonProperty("metadata")
    private Metadata metadata;

    @JsonProperty("invoice")
    private String invoice;

    @JsonProperty("customer")
    private String customer;

    @JsonProperty("receipt_email")
    private String receiptEmail;

    @Data
    public static class Metadata {
        private String orderId;
        private String description;
        private String refundReason;
        private String disputeReason;
    }

    @Data
    public static class Shipping {
        private String name;
        private String addressLine1;
        private String addressLine2;
        private String city;
        private String state;
        private String postalCode;
        private String country;
    }
}


//@Data
//public class PaymentEvent {
//    private String eventType;
//    private String id;
//    private String timestamp;
//    private Integer amount;   // Amount in smallest currency unit
//    private String currency;  // Currency code
//    private String status;    // Status of the event
//    private String reason;    // Reason for disputes or refunds
//    private String customerId;
//    private String paymentMethod;
//    private String country;
//    private String email;
//    private Metadata metadata;
//
//    @Data
//    public static class Metadata {
//        private String orderId;
//        private String description;
//        private String refundReason;
//        private String disputeReason;
//    }
//}
