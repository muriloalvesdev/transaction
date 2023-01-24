package br.com.transaction.core.usecase.account;

import br.com.transaction.entrypoint.dto.AccountDto;

public interface UseCaseAccount {

    String save(final AccountDto dto);

    AccountDto find(final String accountId);

}
