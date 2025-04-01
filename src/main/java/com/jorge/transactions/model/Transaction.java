package com.jorge.transactions.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "transactions")
public class Transaction {
    @Id
    private String id;
    // Transaction related to a Product of the bank | Can be ACCOUNT_ID, CREDIT_CARD_ID OR CREDIT_ID
    private String bankProductId;
    private TransactionType transactionType;
    private CurrencyType currencyType;
    private BigDecimal amount;
    private TransactionStatus status;
    private String description;
    private LocalDateTime transactionDate;
    private String relatedTransactionId;

    public enum TransactionType {
        DEBIT,
        CREDIT,
        DEPOSIT,
        WITHDRAWAL,
        CREDIT_PAYMENT,
        CREDIT_CARD_CONSUMPTION,
        CREDIT_CARD_PAYMENT
    }

    public enum TransactionStatus {
        PENDING,
        COMPLETED,
        FAILED
    }

    public enum CurrencyType{
        PEN, USD
    }
}
