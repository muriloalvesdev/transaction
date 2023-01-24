package br.com.transaction.core.gateway;

import br.com.transaction.dataprovider.database.entity.Transaction;

public interface TransactionGateway {

    Transaction save(Transaction entity);

}
