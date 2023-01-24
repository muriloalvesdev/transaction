package br.com.transaction.core.usecase;

import br.com.transaction.entriypoint.dto.TransactionDto;

import java.util.UUID;

public interface UseCaseTransaction {

    UUID save(final TransactionDto dto);

}
