package com.wildy.transaction_processing.transaction;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionId;
    private Integer amount;
    private String currency;
    private String status;
    private String created;
    private String description;
    private String paymentMethod;
    private String customer;
    private String receiptEmail;
}
