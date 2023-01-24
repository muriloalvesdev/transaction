package br.com.transaction.core.usecase.account;

import br.com.transaction.entrypoint.dto.AccountDto;

import java.util.UUID;

public interface UseCaseAccount {

    UUID save(final AccountDto dto);

    AccountDto find(final UUID accountId);

}
