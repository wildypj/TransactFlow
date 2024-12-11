package com.wildy.fraud_detection.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wildy.fraud_detection.dto.*;
import com.wildy.fraud_detection.service.FraudDetectionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaConsumerServiceTest {

    @Mock
    private FraudDetectionService fraudDetectionService;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private KafkaConsumerService kafkaConsumerService;


    @Test
    void consumeEvent_paymentEvent() throws Exception{
        //Arrange
        // Mock the event payload and its deserialization
        String eventPayload = "{\"eventType\":\"payment_intent.succeeded\",\"id\":\"payment123\"}";
        PaymentEvent paymentEvent = new PaymentEvent();
        paymentEvent.setId("payment123");

        // Mocking objectMapper for both Event and PaymentEvent
        Event event = new Event();
        // Ensure this matches the switch case in the consume method
        event.setEventType("payment_intent.succeeded");

        // Mock the deserialization of both Event and PaymentEvent
        when(objectMapper.readValue(eventPayload, Event.class)).thenReturn(event);
        when(objectMapper.readValue(eventPayload, PaymentEvent.class)).thenReturn(paymentEvent);

        // Act
        kafkaConsumerService.consumeEvent(eventPayload);

        // Assert--verify
        verify(fraudDetectionService, times(1)).handlePaymentEvent(any(PaymentEvent.class));
    }

    @Test
    void consumeEvent_disputeEvent() throws Exception{
        //Arrange
        // Mock the event payload and its deserialization
        String eventPayload = "{\"eventType\":\"charge.dispute.created\",\"id\":\"payment123\"}";
        DisputeEvent disputeEvent = new DisputeEvent();
        disputeEvent.setId("payment123");

        // Mocking objectMapper for both Event and PaymentEvent
        Event event = new Event();
        // Ensure this matches the switch case in the consume method
        event.setEventType("charge.dispute.created");

        // Mock the deserialization of both Event and PaymentEvent
        when(objectMapper.readValue(eventPayload, Event.class)).thenReturn(event);
        when(objectMapper.readValue(eventPayload, DisputeEvent.class)).thenReturn(disputeEvent);

        // Act
        kafkaConsumerService.consumeEvent(eventPayload);

        // Assert--verify
        verify(fraudDetectionService, times(1)).handleDisputeEvent(disputeEvent);
    }

    @Test
    void consumeEvent_refundEvent() throws Exception{
        //Arrange
        // Mock the event payload and its deserialization
        String eventPayload = "{\"eventType\":\"refund.created\",\"id\":\"payment123\"}";
        RefundEvent refundEvent = new RefundEvent();
        refundEvent.setId("payment123");

        // Mocking objectMapper for both Event and PaymentEvent
        Event event = new Event();
        // Ensure this matches the switch case in the consume method
        event.setEventType("refund.created");

        // Mock the deserialization of both Event and PaymentEvent
        when(objectMapper.readValue(eventPayload, Event.class)).thenReturn(event);
        when(objectMapper.readValue(eventPayload, RefundEvent.class)).thenReturn(refundEvent);

        // Act
        kafkaConsumerService.consumeEvent(eventPayload);

        // Assert--verify
        verify(fraudDetectionService, times(1)).handleRefundEvent(refundEvent);
    }

    @Test
    void consumeEvent_customerEvent() throws Exception{
        //Arrange
        // Mock the event payload and its deserialization
        String eventPayload = "{\"eventType\":\"customer.created\",\"id\":\"payment123\"}";
        CustomerEvent customerEvent = new CustomerEvent();
        customerEvent.setId("payment123");

        // Mocking objectMapper for both Event and PaymentEvent
        Event event = new Event();
        // Ensure this matches the switch case in the consume method
        event.setEventType("customer.created");

        // Mock the deserialization of both Event and PaymentEvent
        when(objectMapper.readValue(eventPayload, Event.class)).thenReturn(event);
        when(objectMapper.readValue(eventPayload, CustomerEvent.class)).thenReturn(customerEvent);

        // Act
        kafkaConsumerService.consumeEvent(eventPayload);

        // Assert--verify
        verify(fraudDetectionService, times(1)).handleCustomerEvent(customerEvent);
    }

    @Test
    void consumeEvent_payoutEvent() throws Exception{
        //Arrange
        // Mock the event payload and its deserialization
        String eventPayload = "{\"eventType\":\"payout.created\",\"id\":\"payment123\"}";
        PayoutEvent payoutEvent = new PayoutEvent();
        payoutEvent.setId("payment123");

        // Mocking objectMapper for both Event and PaymentEvent
        Event event = new Event();
        // Ensure this matches the switch case in the consume method
        event.setEventType("payout.created");

        // Mock the deserialization of both Event and PaymentEvent
        when(objectMapper.readValue(eventPayload, Event.class)).thenReturn(event);
        when(objectMapper.readValue(eventPayload, PayoutEvent.class)).thenReturn(payoutEvent);

        // Act
        kafkaConsumerService.consumeEvent(eventPayload);

        // Assert--verify
        verify(fraudDetectionService, times(1)).handlePayoutEvent(payoutEvent);
    }


}