package com.wildy.fraud_detection.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendEvent(String topic, Object eventPayload) {
        try {
            String eventAsString = objectMapper.writeValueAsString(eventPayload);
            kafkaTemplate.send(topic, eventAsString);
        } catch (JsonProcessingException ex) {
            System.err.println("Failed to serialize event: " + ex.getMessage());
        }
    }
}
