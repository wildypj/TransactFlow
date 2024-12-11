package com.wildy.transaction_processing.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wildy.transaction_processing.dto.Event;
import com.wildy.transaction_processing.dto.PaymentEvent;
import com.wildy.transaction_processing.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);
    private final ObjectMapper objectMapper;
    private final TransactionService transactionService;

    @KafkaListener(topics = "raw-payments-topic", groupId = "payment-group")
    public void consumePaymentEvent(String eventPayload) {
        try{
            logger.info("Received event payload: {}", eventPayload);

            Event event = objectMapper.readValue(eventPayload, Event.class);

            if(event.getEventType().equals("payment_intent.succeeded") || event.getEventType().equals("payment_intent.failed")) {
                PaymentEvent paymentEvent = objectMapper.readValue(eventPayload, PaymentEvent.class);
                transactionService.processPaymentEvent(paymentEvent);
            }
        }catch(Exception e) {
            logger.error("Error while processing payment event: {}", eventPayload, e);
        }


    }
}
