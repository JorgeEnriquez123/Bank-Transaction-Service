package com.jorge.transactions.mapper;

import com.jorge.transactions.model.Transaction;
import com.jorge.transactions.model.TransactionRequest;
import com.jorge.transactions.model.TransactionResponse;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransactionMapper {
    public Transaction mapToTransaction(TransactionRequest transactionRequest) {
        return Transaction.builder()
                .bankProductId(transactionRequest.getBankProductId())
                .transactionType(Transaction.TransactionType.valueOf(transactionRequest.getTransactionType().name()))
                .currencyType(Transaction.CurrencyType.valueOf(transactionRequest.getCurrencyType().name()))
                .amount(transactionRequest.getAmount())
                .description(transactionRequest.getDescription())
                .build();
    }

    public TransactionResponse mapToTransactionResponse(Transaction transaction) {
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setId(transaction.getId());
        transactionResponse.setTransactionType(TransactionResponse.TransactionTypeEnum.valueOf(transaction.getTransactionType().name()));
        transactionResponse.setCurrencyType(TransactionResponse.CurrencyTypeEnum.valueOf(transaction.getCurrencyType().name()));
        transactionResponse.setAmount(transaction.getAmount());
        transactionResponse.setStatus(TransactionResponse.StatusEnum.valueOf(transaction.getStatus().name()));
        transactionResponse.setDescription(transaction.getDescription());
        transactionResponse.setTransactionDate(transaction.getTransactionDate());
        return transactionResponse;
    }
}
