package br.com.transaction.dataprovider.database.gateway;

import br.com.transaction.core.exception.AccountNotFoundException;
import br.com.transaction.core.gateway.BalanceGateway;
import br.com.transaction.dataprovider.database.entity.Account;
import br.com.transaction.dataprovider.database.entity.Balance;
import br.com.transaction.dataprovider.database.repository.BalanceRepository;

public class BalanceGatewayImpl implements BalanceGateway {

    private final BalanceRepository repository;
    
    public BalanceGatewayImpl(final BalanceRepository repository) {
        this.repository = repository;
    }

    public Balance save(final Balance balance) {
        return this.repository.saveAndFlush(balance);
    }

    public Balance findByAccount(final Account account) {
        return this.repository.findByAccount(account)
            .orElseThrow(() -> new AccountNotFoundException(account.getUuid()));
    }

}
