package com.wildy.fraud_detection.dto;

import lombok.Data;

@Data
public class FraudAlert {
    private String eventId;
    private String alertMessage;
    // String representation of the event (or serialized object)
    private String eventDetails;

    public FraudAlert(String eventId, String alertMessage, Object eventDetails) {
        this.eventId = eventId;
        this.alertMessage = alertMessage;
        this.eventDetails = eventDetails.toString(); // Convert event to string (can be improved)
    }
}
