package com.wildy.payment.stripe;

import com.stripe.exception.SignatureVerificationException;
import com.wildy.payment.kafka.KafkaProducerService;
import com.stripe.model.Event;
import org.springframework.http.ResponseEntity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StripeWebhookControllerTest {

    @Mock
    private StripeService stripeService;

    @Mock
    private KafkaProducerService kafkaProducerService;

    @InjectMocks
    private StripeWebhookController stripeWebhookController;

    private final String payload = "mockPayload";
    private final String signature = "mockSignature";

    @Test
    void handleWebhook() throws Exception {
        //Arrange
        Event mockEvent = new Event();
        when(stripeService.validateAndParseWebhook(payload, signature)).thenReturn(mockEvent);

        //Act
        ResponseEntity<String> response = stripeWebhookController.handleWebhook(payload, signature);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Webhook processed successfully", response.getBody());
        verify(stripeService, times(1)).validateAndParseWebhook(payload, signature);
        verify(kafkaProducerService, times(1)).sendEvent(anyString(), anyString());

    }

    @Test
    void handleWebhook_invalidSignature_shouldReturnBadRequest() throws SignatureVerificationException {
        // Arrange: Mock StripeService to throw SignatureVerificationException with required arguments
        when(stripeService.validateAndParseWebhook(payload, signature))
                .thenThrow(new SignatureVerificationException("Invalid signature", "mock-sig-header"));

        // Act: Call the webhook handler
        ResponseEntity<String> response = stripeWebhookController.handleWebhook(payload, signature);

        // Assert: Verify the response has HTTP 400 and the correct message
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid signature", response.getBody());
    }

    @Test
    void handleWebhook_internalError_shouldReturnServerError() throws SignatureVerificationException {
        when(stripeService.validateAndParseWebhook(payload, signature)).thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<String> response = stripeWebhookController.handleWebhook(payload, signature);
        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Webhook processing failed", response.getBody());
    }
}