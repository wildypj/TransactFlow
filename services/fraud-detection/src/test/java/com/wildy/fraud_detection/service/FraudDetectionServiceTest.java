package com.wildy.fraud_detection.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wildy.fraud_detection.dto.*;
import com.wildy.fraud_detection.kafka.KafkaProducerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FraudDetectionServiceTest {

    @Mock
    private KafkaProducerService kafkaProducerService;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private FraudDetectionService fraudDetectionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize the mocks
    }

    @Test
    void handlePaymentEvent_Fraudulent() throws Exception {
        // Arrange
        PaymentEvent.Metadata metadata = new PaymentEvent.Metadata();
        metadata.setDescription("high-risk-country");

        PaymentEvent paymentEvent = new PaymentEvent();
        paymentEvent.setId("payment123");
        paymentEvent.setAmount(1500);
        paymentEvent.setCurrency("usd");
        paymentEvent.setMetadata(metadata); // Set metadata

        // Mock the conversion to JSON
        when(objectMapper.writeValueAsString(any(PaymentEvent.class))).thenReturn("{\"id\":\"payment123\"}");

        // Act
        fraudDetectionService.handlePaymentEvent(paymentEvent);

        // Assert
        verify(kafkaProducerService, times(1)).sendEvent(eq("fraud-alerts-topic"), anyString());
    }

    @Test
    void handleDisputeEvent() throws Exception {
        // Arrange
        DisputeEvent disputeEvent = new DisputeEvent();
        disputeEvent.setId("dispute123");
        disputeEvent.setAmount(600);
        disputeEvent.setReason("fraudulent_reason");

        // Mock the conversion to JSON
        when(objectMapper.writeValueAsString(any(DisputeEvent.class))).thenReturn("{\"id\":\"dispute123\"}");

        // Call the method under test
        fraudDetectionService.handleDisputeEvent(disputeEvent);

        // Verify that Kafka producer service sends a fraud alert
        verify(kafkaProducerService, times(1)).sendEvent(eq("fraud-alerts-topic"), anyString());

    }

    @Test
    void handleCustomerEvent() throws JsonProcessingException {
        // Arrange
        CustomerEvent customerEvent = new CustomerEvent();
        customerEvent.setId("customer123");
        customerEvent.setEmail("suspicious@suspicious.com");

        // Mock the conversion to JSON
        when(objectMapper.writeValueAsString(any(CustomerEvent.class))).thenReturn("{\"id\":\"customer123\"}");

        // Call the method under test
        fraudDetectionService.handleCustomerEvent(customerEvent);

        // Verify that Kafka producer service sends a fraud alert
        verify(kafkaProducerService, times(1)).sendEvent(eq("fraud-alerts-topic"), anyString());
    }

    @Test
    void handleRefundEvent() throws JsonProcessingException {
        // Arrange
        RefundEvent refundEvent = new RefundEvent();
        refundEvent.setId("refund123");
        refundEvent.setAmount(600);
        refundEvent.setReason("fraudulent");

        // Mock the conversion to JSON
        when(objectMapper.writeValueAsString(any(RefundEvent.class))).thenReturn("{\"id\":\"refund123\"}");

        // Call the method under test
        fraudDetectionService.handleRefundEvent(refundEvent);

        // Verify that Kafka producer service sends a fraud alert
        verify(kafkaProducerService, times(1)).sendEvent(eq("fraud-alerts-topic"), anyString());
    }

    @Test
    void handlePayoutEvent() throws JsonProcessingException {
        //Arrange
        PayoutEvent payoutEvent = new PayoutEvent();
        payoutEvent.setId("payout123");
        payoutEvent.setAmount(1500);
        payoutEvent.setCurrency("eur");

        // Mock the conversion to JSON
        when(objectMapper.writeValueAsString(any(PayoutEvent.class))).thenReturn("{\"id\":\"payout123\"}");

        // Call the method under test
        fraudDetectionService.handlePayoutEvent(payoutEvent);

        // Verify that Kafka producer service sends a fraud alert
        verify(kafkaProducerService, times(1)).sendEvent(eq("fraud-alerts-topic"), anyString());
    }
}