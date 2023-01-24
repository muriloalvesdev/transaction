package br.com.transaction.dataprovider.database.gateway;

import br.com.transaction.core.gateway.AccountGateway;
import br.com.transaction.dataprovider.database.entity.Account;
import br.com.transaction.dataprovider.database.repository.AccountRepository;

import java.util.Optional;
import java.util.UUID;

public class AccountGatewayImpl implements AccountGateway {

    private final AccountRepository repository;

    public AccountGatewayImpl(final AccountRepository repository) {
        this.repository = repository;
    }

    public Account save(final Account entity) {
        return this.repository.saveAndFlush(entity);
    }

    public Optional<Account> find(final UUID accountId) {
        return this.repository.findById(accountId);
    }
    public Boolean existsByDocumentNumber(final String documentNumber) {
        return this.repository.existsByDocumentNumber(documentNumber);
    }

}
