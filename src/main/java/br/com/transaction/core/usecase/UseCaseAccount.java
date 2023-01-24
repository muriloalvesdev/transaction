package br.com.transaction.core.usecase;

import br.com.transaction.entriypoint.dto.AccountDto;

import java.util.UUID;

public interface UseCaseAccount {

    UUID save(final AccountDto dto);

    AccountDto find(final UUID accountId);

}
