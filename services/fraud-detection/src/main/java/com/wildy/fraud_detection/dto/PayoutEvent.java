package com.wildy.fraud_detection.dto;

import lombok.Data;

@Data
public class PayoutEvent {

    private String eventType;         // Type of event (e.g., "payout.created", "payout.updated")
    private String id;                // Payout ID (e.g., "po_1OaFDbEcg9tTZuTgNYmX0PKB")
    private String timestamp;         // Timestamp of the event (in seconds)
    private Integer amount;           // Amount of the payout (smallest currency unit, e.g., 1100 = $11.00)
    private String currency;          // Currency of the payout (e.g., "usd")
    private String status;            // Status of the payout (e.g., "pending", "succeeded")
    private String method;            // Payout method (e.g., "standard")
    private String destination;       // Destination ID (e.g., "ba_1MtIhL2eZvKYlo2CAElKwKu2")
    private String balanceTransaction; // The balance transaction ID associated with the payout
    private String reconciliationStatus; // Reconciliation status (e.g., "not_applicable")
    private String arrivalDate;       // Arrival date of the payout (Unix timestamp)

    @Data
    public static class Metadata {
        private String orderId;         // Order ID (optional, depending on the event)
        private String bank;            // Bank (optional, if you want to capture the bank name)
        private String accountNumber;   // Account number (optional, if you want to capture the account number)
    }

}

//    private String eventType;
//    private String id;
//    private String timestamp;
//    private Integer amount; // Smallest currency unit
//    private String currency;
//    private String status;
//    private Metadata metadata;
//
//    @Data
//    public static class Metadata {
//        private String bank;
//        private String accountNumber;
//    }
