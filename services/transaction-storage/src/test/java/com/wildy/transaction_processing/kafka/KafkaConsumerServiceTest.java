package com.wildy.transaction_processing.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wildy.transaction_processing.dto.Event;
import com.wildy.transaction_processing.dto.PaymentEvent;
import com.wildy.transaction_processing.transaction.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KafkaConsumerServiceTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private TransactionService transactionService;

    @Mock
    private Logger logger;

    @InjectMocks
    private KafkaConsumerService kafkaConsumerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void consumePaymentEvent_shouldProcessValidPaymentEvent() throws Exception {
        String eventPayload = "{\"eventType\": \"payment_intent.succeeded\", \"id\": \"123\", \"amount\": 100}";
        Event event = new Event();
        event.setEventType("payment_intent.succeeded");

        PaymentEvent paymentEvent = new PaymentEvent();
        paymentEvent.setId("123");
        paymentEvent.setAmount(100);

        when(objectMapper.readValue(eventPayload, Event.class)).thenReturn(event);
        when(objectMapper.readValue(eventPayload, PaymentEvent.class)).thenReturn(paymentEvent);

        kafkaConsumerService.consumePaymentEvent(eventPayload);

        verify(transactionService, times(1)).processPaymentEvent(paymentEvent);
    }


}