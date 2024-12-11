package com.wildy.payment.stripe;

import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import com.stripe.exception.SignatureVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    @Value("${stripe.webhook.secret}")
    private String stripeWebhookSecret;

    // Validate webhook payload and signature, then parse it into an Event
    public Event validateAndParseWebhook(String payload, String signature) throws SignatureVerificationException {
        // Use Stripe's library to verify the signature
        return Webhook.constructEvent(payload, signature, stripeWebhookSecret);
    }

    // Handle supported event types
    public void handleEvent(Event event) {
        String eventType = event.getType();
        Object dataObject = event.getDataObjectDeserializer().getObject()
                .orElseThrow(() -> new IllegalArgumentException("Invalid event data"));

        switch (eventType) {
            case "payment_intent.payment_failed":
            case "payment_intent.succeeded":
            case "payment_intent.requires_action":
            case "payment_intent.canceled":
                handlePaymentIntentEvent(eventType, (PaymentIntent) dataObject);
                break;

            case "charge.dispute.created":
            case "charge.dispute.updated":
            case "charge.dispute.closed":
                handleDisputeEvent(eventType, dataObject);
                break;

            case "customer.source.created":
            case "customer.source.updated":
            case "customer.source.deleted":
            case "customer.updated":
                handleCustomerEvent(eventType, dataObject);
                break;

            case "invoice.payment_failed":
            case "refund.created":
            case "payout.created":
            case "payout.failed":
                handleGeneralEvent(eventType, dataObject);
                break;

            default:
                System.out.println("Unhandled event type: " + eventType);
                break;
        }
    }
    private void handlePaymentIntentEvent(String eventType, PaymentIntent paymentIntent) {
        System.out.println("Handled PaymentIntent event: " + eventType + " for ID: " + paymentIntent.getId());
    }

    private void handleDisputeEvent(String eventType, Object dataObject) {
        System.out.println("Handled Dispute event: " + eventType + " Data: " + dataObject);
    }

    private void handleCustomerEvent(String eventType, Object dataObject) {
        System.out.println("Handled Customer event: " + eventType + " Data: " + dataObject);
    }

    private void handleGeneralEvent(String eventType, Object dataObject) {
        System.out.println("Handled General event: " + eventType + " Data: " + dataObject);
    }

}

//// Handle supported event types
//public void handleEvent(Event event) {
//    switch (event.getType()) {
//
//        case "payment_intent.succeeded":
//            PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer()
//                    .getObject()
//                    .orElseThrow(() -> new IllegalArgumentException("Invalid payment intent event"));
//            System.out.println("Payment succeeded: " + paymentIntent.getId());
//            break;
//
//        case "payment_intent.payment_failed":
//            PaymentIntent failedPaymentIntent = (PaymentIntent) event.getDataObjectDeserializer()
//                    .getObject()
//                    .orElseThrow(() -> new IllegalArgumentException("Invalid payment failed event"));
//            System.out.println("Payment failed: " + failedPaymentIntent.getId());
//            break;
//
//        default:
//            System.out.println("Unhandled event type: " + event.getType());
//            break;
//    }
//}
