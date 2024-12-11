package com.wildy.transaction_processing.transaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTransactions() {
        //Arrange
        Transaction transaction1 = Transaction.builder().transactionId("123`").build();
        Transaction transaction2 = Transaction.builder().transactionId("456`").build();

        when(transactionService.getAllTransaction()).thenReturn(List.of(transaction1, transaction2));

        //Act
        ResponseEntity<List<Transaction>> response = transactionController.getAllTransactions();


        assertEquals(2, response.getBody().size());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void getTransactionById() {
        Transaction transaction = Transaction.builder()
                .transactionId("123")
                .build();

        when(transactionService.getTransactionById("123")).thenReturn(transaction);

        ResponseEntity<?> response = transactionController.getTransactionById("123");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("123", ((Transaction) response.getBody()).getTransactionId());
    }

    @Test
    void getTransactionById_shouldReturn404WhenNotFound() {
        when(transactionService.getTransactionById("123")).thenReturn(null);

        ResponseEntity<?> response = transactionController.getTransactionById("123");

        assertEquals(404, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Transaction not found"));
    }
}