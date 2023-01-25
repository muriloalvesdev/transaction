package br.com.transaction.core.usecase.transaction;

import br.com.transaction.BaseUnitTest;
import br.com.transaction.core.exception.InvalidAmountException;
import br.com.transaction.dataprovider.database.entity.OperationsType;
import br.com.transaction.dataprovider.database.entity.Transaction;
import br.com.transaction.entrypoint.dto.TransactionDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class UseCaseTransactionImplUnitTest extends BaseUnitTest {

    private final UseCaseTransaction usecase =
        new UseCaseTransactionImpl(this.transactionGatewayMock, this.accountGatewayMock);

    @ParameterizedTest
    @MethodSource("transactionDtosValids")
    @DisplayName("Mock - Deve salvar uma transacao para cada tipo de operacoes existentes com sucesso.")
    void shouldSaveTransactionsWithSuccess(final TransactionDto dto) {
        //GIVEN
        final var accountEntity = toAccountEntity(dto.getAccountId());
        final var transactionEntity = toTransactionEntity(dto);

        given(this.accountGatewayMock.find(dto.getAccountId()))
            .willReturn(Optional.ofNullable(accountEntity));

        given(this.transactionGatewayMock.save(any(Transaction.class)))
            .willReturn(transactionEntity);

        //WHEN
        this.usecase.save(dto);

        //THEN
        verifyWasInvoked(this.accountGatewayMock, 1)
            .find(dto.getAccountId());
        verifyWasInvoked(this.transactionGatewayMock, 1)
            .save(any(Transaction.class));
    }

    @ParameterizedTest
    @MethodSource("transactionDtosInvalids")
    @DisplayName(
        """
        Mock - Deve tentar salvar uma transacao para cada
        tipo de operacoes invalidas e retornar erro.
        """
    )
    void shouldTrySaveTransactionsButReturnExceptionWhenAmountIsInvalidForTypeSpecified(final TransactionDto dto) {
        //GIVEN is param
        final var operationsType = OperationsType.fromString(dto.getOperationType());
        final var sign = operationsType.getRule().isPositive() ? "POSITIVE" : "NEGATIVE";
        final var exceptionExpected = new InvalidAmountException(operationsType, sign);

        //WHEN
        final var exceptionActual =
            assertThrows(InvalidAmountException.class, () -> this.usecase.save(dto));

        //THEN
        verifyWasInvoked(this.accountGatewayMock, 0)
            .find(dto.getAccountId());
        verifyWasInvoked(this.transactionGatewayMock, 0)
            .save(any(Transaction.class));

        isEquals(exceptionExpected.getMessage(), exceptionActual.getMessage());
    }

}
