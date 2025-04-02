package com.jorge.transactions;

import com.jorge.transactions.api.TransactionsApiDelegate;
import com.jorge.transactions.model.TransactionRequest;
import com.jorge.transactions.model.TransactionResponse;
import com.jorge.transactions.model.UpdateTransactionStatusRequest;
import com.jorge.transactions.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TransactionApiDelegateImpl implements TransactionsApiDelegate {
    private final TransactionService transactionService;

    @Override
    public Mono<TransactionResponse> createTransaction(Mono<TransactionRequest> transactionRequest, ServerWebExchange exchange) {
        return transactionRequest.flatMap(transactionService::createTransaction);
    }

    @Override
    public Mono<TransactionResponse> getTransactionById(String id, ServerWebExchange exchange) {
        return transactionService.getTransactionById(id);
    }

    @Override
    public Flux<TransactionResponse> getTransactionsByBankProductId(String bankProductId, ServerWebExchange exchange) {
        return transactionService.getTransactionsByAccountNumber(bankProductId);
    }

    @Override
    public Mono<TransactionResponse> updateTransactionStatusByTransactionId(String id, Mono<UpdateTransactionStatusRequest> updateTransactionStatusRequest, ServerWebExchange exchange) {
        return updateTransactionStatusRequest.flatMap(
                updateTransactionStatusRequest1 ->
                transactionService.updateTransactionStatusByTransactionId(id, updateTransactionStatusRequest1));
    }
}
