package com.wildy.transaction_processing.transaction;

import com.wildy.transaction_processing.dto.PaymentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public void processPaymentEvent(PaymentEvent paymentEvent) {
        // Convert PaymentEvent to TransactionEntity
        Transaction transactionEntity = Transaction.builder()
                .transactionId(paymentEvent.getId())
                .amount(paymentEvent.getAmount())
                .currency(paymentEvent.getCurrency())
                .status(paymentEvent.getStatus())
                .created(paymentEvent.getCreated())
                .description(paymentEvent.getDescription())
                .paymentMethod(paymentEvent.getPaymentMethod())
                .customer(paymentEvent.getCustomer())
                .receiptEmail(paymentEvent.getReceiptEmail())
                .build();

        // Save the transaction to the database
        transactionRepository.save(transactionEntity);
    }


    public Transaction getTransactionById(String transactionId) {
        return transactionRepository.findByTransactionId(transactionId)
                .orElse(null);
    }


    public List<Transaction> getAllTransaction() {
        return transactionRepository.findAll();
    }
}
