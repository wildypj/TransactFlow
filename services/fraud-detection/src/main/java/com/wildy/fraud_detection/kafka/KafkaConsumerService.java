package com.wildy.fraud_detection.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wildy.fraud_detection.dto.*;
import com.wildy.fraud_detection.service.FraudDetectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {
    private final FraudDetectionService fraudDetectionService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${spring.kafka.topics.raw-payments-topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeEvent(String eventPayload) {
        try {
            // Deserialize the eventPayload to determine the event type
            // This will help to determine which type of event it is (Payment, Dispute, etc.)
            Event event = objectMapper.readValue(eventPayload, Event.class);
            switch (event.getEventType()) {
                case "payment_intent.succeeded":
                case "payment_intent.payment_failed":
                case "payment_intent.requires_action":
                case "payment_intent.canceled":
                    PaymentEvent paymentEvent = objectMapper.readValue(eventPayload, PaymentEvent.class);
                    fraudDetectionService.handlePaymentEvent(paymentEvent);
                    break;
                case "charge.dispute.created":
                case "charge.dispute.updated":
                case "charge.dispute.closed":
                    DisputeEvent disputeEvent = objectMapper.readValue(eventPayload, DisputeEvent.class);
                    fraudDetectionService.handleDisputeEvent(disputeEvent);
                    break;
                case "refund.created":
                case "refund.updated":
                case "refund.failed":
                    RefundEvent refundEvent = objectMapper.readValue(eventPayload, RefundEvent.class);
                    fraudDetectionService.handleRefundEvent(refundEvent);
                    break;
                case "customer.created":
                case "customer.source.created":
                case "customer.source.updated":
                case "customer.source.deleted":
                    CustomerEvent customerEvent = objectMapper.readValue(eventPayload, CustomerEvent.class);
                    fraudDetectionService.handleCustomerEvent(customerEvent);
                    break;
                case "payout.created":
                case "payout.updated":
                    PayoutEvent payoutEvent = objectMapper.readValue(eventPayload, PayoutEvent.class);
                    fraudDetectionService.handlePayoutEvent(payoutEvent);
                    break;
                default:
                    System.err.println("Unrecognized event type: " + event.getEventType());
                    break;
            }
        } catch (Exception ex) {
            System.err.println("Failed to process event: " + ex.getMessage());
        }
    }
}


// This works
//@KafkaListener(topics = "${spring.kafka.topics.raw-payments-topic}", groupId = "${spring.kafka.consumer.group-id}")
//public void consumePaymentEvent(String eventPayload) {
//    try{
//        // Deserialize the event payload
//        PaymentEvent paymentEvent = objectMapper.readValue(eventPayload, PaymentEvent.class);
//
//        // Pass the event to the fraud detection service
//        fraudDetectionService.analyzePayment(paymentEvent);
//    } catch (Exception ex){
//        System.err.println("Failed to process event: " + ex.getMessage());
//    }
//}




