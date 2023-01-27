package br.com.transaction.core.usecase.account;

import br.com.transaction.core.exception.AccountNotFoundException;
import br.com.transaction.core.exception.AlreadyDocumentNumberException;
import br.com.transaction.core.gateway.AccountGateway;
import br.com.transaction.core.gateway.BalanceGateway;
import br.com.transaction.dataprovider.database.entity.Account;
import br.com.transaction.dataprovider.database.entity.Balance;
import br.com.transaction.entrypoint.dto.AccountDto;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

public class UseCaseAccountImpl implements UseCaseAccount {

    private final AccountGateway gateway;
    private final BalanceGateway balanceGateway;

    public UseCaseAccountImpl(final AccountGateway gateway, final BalanceGateway balanceGateway) {
        this.gateway = gateway;
        this.balanceGateway = balanceGateway;
    }

    @Transactional
    public String save(final AccountDto accountDto) {
        if (this.gateway.existsByDocumentNumber(accountDto.getDocumentNumber()))
            throw new AlreadyDocumentNumberException(accountDto.getDocumentNumber());

        final var accountEntity = this.gateway.save(Account.builder()
            .documentNumber(accountDto.getDocumentNumber())
            .uuid(UUID.randomUUID().toString())
            .build());

        this.balanceGateway.save(Balance.builder()
            .account(accountEntity)
            .uuid(UUID.randomUUID().toString())
            .availableCreditLimit(BigDecimal.valueOf(1000))
            .build());

        return accountEntity.getUuid();
    }

    public AccountDto find(final String accountId) {
        return this.gateway.find(accountId)
            .map(AccountDto::build)
            .orElseThrow(() -> new AccountNotFoundException(accountId));
    }

}
