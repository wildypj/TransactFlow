package com.wildy.fraud_detection.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CustomerEvent {
    private String eventType;         // Type of event (e.g., "customer.created", "customer.updated", "customer.deleted")
    private String id;                // The customer ID (e.g., "cus_RHsvQkqMAZEr8K")
    private String customerId;        // Customer ID, same as 'id'
    private String email;             // Customer email (nullable)
    private Integer balance;          // Customer balance (nullable)
    private String description;       // A description of the customer
    private Boolean delinquent;       // Whether the customer is delinquent (boolean)
    private String invoicePrefix;     // Invoice prefix (nullable)
    private Integer nextInvoiceSequence; // Next invoice sequence
    private String taxExempt;         // Tax exemption status (e.g., "none")
    private Boolean livemode;         // Whether the event is in live mode (boolean)
    @JsonProperty("metadata")
    private Metadata metadata;        // Metadata related to the customer
    private Shipping shipping;        // Shipping details
    private String name;              // Customer name (nullable)
    private String phone;             // Customer phone number (nullable)
    private List<String> preferredLocales; // List of preferred locales
    private InvoiceSettings invoiceSettings; // Invoice settings
    @JsonProperty("timestamp")
    private String created;           // Timestamp of when the customer was created
    private Boolean testClock;        // Whether the customer is in test mode (nullable)
    private Address address;          // Customer address (nullable)

    // Represents the Metadata object
    @Data
    public static class Metadata {
        private String foo;           // Example field based on the payload provided
        private String notes;         // Can capture additional metadata fields
    }

    // Represents Shipping Information (if applicable)
    @Data
    public static class Shipping {
        private String address;       // Address details (nullable)
        private String carrier;       // Shipping carrier (nullable)
        private String method;        // Shipping method (nullable)
    }

    // Represents the Invoice Settings (if applicable)
    @Data
    public static class InvoiceSettings {
        private String customFields;  // Custom fields for invoices (nullable)
        private String footer;        // Footer for invoices (nullable)
        private String defaultPaymentMethod; // Default payment method (nullable)
    }

    // Represents the Address (if applicable)
    @Data
    public static class Address {
        private String line1;         // Address line 1
        private String city;          // City
        private String state;         // State
        private String postalCode;    // Postal Code
        private String country;       // Country
    }
}
//    private String eventType;
//    private String id;
//    private String timestamp;
//    private String customerId;
//    private String email;
//    private Metadata metadata;
//
//    @Data
//    public static class Metadata {
//        private String notes;
//    }
