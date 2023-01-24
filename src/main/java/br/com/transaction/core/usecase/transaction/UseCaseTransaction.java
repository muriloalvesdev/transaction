package br.com.transaction.core.usecase.transaction;

import br.com.transaction.entrypoint.dto.TransactionDto;

import java.util.UUID;

public interface UseCaseTransaction {

    UUID save(final TransactionDto dto);

}
