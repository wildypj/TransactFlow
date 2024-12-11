package com.wildy.transaction_processing.transaction;

import com.wildy.transaction_processing.dto.PaymentEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void processPaymentEvent_shouldSavePaymentEvent() {
        //Arrange
        PaymentEvent paymentEvent = new PaymentEvent();
        paymentEvent.setId("123");
        paymentEvent.setAmount(200);
        paymentEvent.setCurrency("USD");
        paymentEvent.setStatus("succeeded");

        transactionService.processPaymentEvent(paymentEvent);

        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void getTransactionById() {
        //Arrange
        Transaction transaction = Transaction.builder()
                .transactionId("123")
                .amount(200)
                .build();

        //Act
        when(transactionRepository.findByTransactionId("123")).thenReturn(Optional.of(transaction));

        Transaction found = transactionService.getTransactionById("123");

        assertNotNull(found);
        assertEquals("123", found.getTransactionId());
    }

    @Test
    void getAllTransaction() {
        Transaction transaction1 = Transaction.builder().transactionId("123").build();
        Transaction transaction2 = Transaction.builder().transactionId("456").build();

        when(transactionRepository.findAll()).thenReturn(List.of(transaction1, transaction2));

        List<Transaction> allTransactions = transactionService.getAllTransaction();

        assertEquals(2, allTransactions.size());
    }
}