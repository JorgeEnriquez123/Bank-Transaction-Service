package com.jorge.transactions.service.impl;

import com.jorge.transactions.mapper.TransactionMapper;
import com.jorge.transactions.model.Transaction;
import com.jorge.transactions.model.TransactionRequest;
import com.jorge.transactions.model.TransactionResponse;
import com.jorge.transactions.model.UpdateTransactionStatusRequest;
import com.jorge.transactions.repository.TransactionRepository;
import com.jorge.transactions.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;

    @Override
    public Flux<TransactionResponse> getTransactionsByAccountNumber(String accountNumber) {
        return transactionRepository.findByAccountNumber(accountNumber)
                .map(transactionMapper::mapToTransactionResponse);
    }

    @Override
    public Mono<TransactionResponse> getTransactionById(String id) {
        return transactionRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Movimiento con id: " + id + " no encontrado")))
                .map(transactionMapper::mapToTransactionResponse);
    }

    @Override
    public Mono<TransactionResponse> createTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = transactionMapper.mapToTransaction(transactionRequest);
        transaction.setTransactionDate(LocalDateTime.now());

        return transactionRepository.save(transaction)
                .map(transactionMapper::mapToTransactionResponse);
    }

    @Override
    public Mono<TransactionResponse> updateTransactionStatusByTransactionId(String transactionId, UpdateTransactionStatusRequest updateTransactionStatusRequest) {
        Mono<Transaction> transactionMono = transactionRepository.findById(transactionId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Movimiento con id: " + transactionId + " no encontrado")));
        return transactionMono.flatMap(
                transaction -> { transaction.setStatus(
                        Transaction.TransactionStatus.valueOf(updateTransactionStatusRequest.getStatus().name()));
                        return transactionRepository.save(transaction);
                })
                .map(transactionMapper::mapToTransactionResponse);
    }
}
