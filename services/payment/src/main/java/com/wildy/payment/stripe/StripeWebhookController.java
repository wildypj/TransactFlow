package com.wildy.payment.stripe;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.wildy.payment.kafka.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stripe")
@RequiredArgsConstructor
public class StripeWebhookController {
    private final StripeService stripeService;
    private final KafkaProducerService kafkaProducerService;

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String signature) {
        try {
            //validate webhook payload and Signature
            Event stripeEvent = stripeService.validateAndParseWebhook(payload, signature);
            stripeService.handleEvent(stripeEvent);

            //publish to Kafka
            kafkaProducerService.sendEvent("raw-payments-topic", stripeEvent.toJson());
            return ResponseEntity.ok("Webhook processed successfully");
        } catch (SignatureVerificationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Webhook processing failed");
        }

    }
}

