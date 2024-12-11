package com.wildy.transaction_processing.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> allTransactions = transactionService.getAllTransaction();
        return ResponseEntity.ok(allTransactions);
    }


    @GetMapping("/{transactionId}")
    public ResponseEntity<?> getTransactionById(@PathVariable String transactionId) {
        Transaction found = transactionService.getTransactionById(transactionId);
        if (found == null) {
            return ResponseEntity.status(404)
                    .body("Transaction not found with ID: " + transactionId);
        }
        return ResponseEntity.ok(found);
    }

}
