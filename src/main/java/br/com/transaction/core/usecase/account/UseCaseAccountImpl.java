package br.com.transaction.core.usecase.account;

import br.com.transaction.core.exception.AccountNotFoundException;
import br.com.transaction.core.exception.AlreadyDocumentNumberException;
import br.com.transaction.core.gateway.AccountGateway;
import br.com.transaction.dataprovider.database.entity.Account;
import br.com.transaction.entrypoint.dto.AccountDto;

import java.util.UUID;

public class UseCaseAccountImpl implements UseCaseAccount {

    private final AccountGateway gateway;

    public UseCaseAccountImpl(final AccountGateway gateway) {
        this.gateway = gateway;
    }

    public String save(final AccountDto dto) {
        if (this.gateway.existsByDocumentNumber(dto.getDocumentNumber()))
            throw new AlreadyDocumentNumberException(dto.getDocumentNumber());

        final Account entity = Account.builder()
            .documentNumber(dto.getDocumentNumber())
            .uuid(UUID.randomUUID().toString())
            .build();

        return this.gateway.save(entity).getUuid();
    }

    public AccountDto find(final String accountId) {
        return this.gateway.find(accountId)
            .map(AccountDto::build)
            .orElseThrow(() -> new AccountNotFoundException(accountId));
    }

}
