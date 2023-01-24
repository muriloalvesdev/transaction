package br.com.transaction.dataprovider.database.gateway;

import br.com.transaction.core.gateway.TransactionGateway;
import br.com.transaction.dataprovider.database.entity.Transaction;
import br.com.transaction.dataprovider.database.repository.TransactionRepository;

public class TransactionGatewayImpl implements TransactionGateway {

    private final TransactionRepository repository;

    public TransactionGatewayImpl(final TransactionRepository repository) {
        this.repository = repository;
    }

    public Transaction save(final Transaction entity) {
        return this.repository.saveAndFlush(entity);
    }

}
