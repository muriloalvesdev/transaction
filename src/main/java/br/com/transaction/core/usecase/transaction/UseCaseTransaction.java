package br.com.transaction.core.usecase.transaction;

import br.com.transaction.entrypoint.dto.TransactionDto;

public interface UseCaseTransaction {

    String save(final TransactionDto dto);

}
