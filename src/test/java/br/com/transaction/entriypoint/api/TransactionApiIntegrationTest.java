package br.com.transaction.entriypoint.api;

import br.com.transaction.BaseIntegrationTests;
import br.com.transaction.core.exception.AccountNotFoundException;
import br.com.transaction.core.exception.InvalidAmountException;
import br.com.transaction.dataprovider.database.entity.OperationsType;
import br.com.transaction.dataprovider.database.entity.Transaction;
import br.com.transaction.dataprovider.database.exception.InvalidOperationTypeException;
import br.com.transaction.entriypoint.dto.ResponseError;
import br.com.transaction.entriypoint.dto.TransactionDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TransactionApiIntegrationTest extends BaseIntegrationTests {

    @ParameterizedTest
    @MethodSource("transactionDtosValids")
    @DisplayName("Integration - Deve salvar uma transacao para cada tipo de operacoes existentes com sucesso.")
    void shouldSaveWithSuccess(final TransactionDto dto) throws Exception {
        //GIVEN
        final var accountEntity = toAccountEntity(
            UUID.randomUUID(),
            String.valueOf(new Random().nextInt()));

        given(this.accountGatewayMock.find(dto.getAccountId()))
            .willReturn(Optional.of(accountEntity));
        given(this.transactionGateway.save(any(Transaction.class)))
            .willReturn(toTransactionEntity(dto, accountEntity));

        //WHEN
        requestPost(dto, URI.create(BASE_URL_TRANSACTIONS))
            .andExpect(status().isCreated());

        //THEN
        verifyWasInvoked(this.accountGatewayMock, 1)
            .find(dto.getAccountId());
        verifyWasInvoked(this.transactionGateway, 1)
            .save(any(Transaction.class));
    }

    @ParameterizedTest
    @MethodSource("transactionDtosValids")
    @DisplayName(
        """
        Integration - Deve tentar salvar uma transacao para cada tipo de operacoes validas existentes, 
        mas todas elas não contém uma conta válida na base de dados.
        """)
    void shouldTrySaveTransctionButAccountNotFound(final TransactionDto dto) throws Exception {
        //GIVEN
        final var exceptionExpected = new AccountNotFoundException(dto.getAccountId());
        final var responseErrorExpected = new ResponseError(exceptionExpected.getMessage(), 404);

        given(this.accountGatewayMock.find(dto.getAccountId()))
            .willReturn(Optional.empty());

        //WHEN
        final var resultActions = requestPost(dto, URI.create(BASE_URL_TRANSACTIONS));

        //THEN
        final var responseJson = resultActions
            .andExpect(status().isNotFound())
            .andReturn()
            .getResponse()
            .getContentAsString();

        final var responseErrorActual = this.objectMapper.readValue(responseJson, ResponseError.class);

        compareUsingRecursiveComparison(responseErrorExpected, responseErrorActual);
        verifyWasInvoked(this.accountGatewayMock, 1)
            .find(dto.getAccountId());
        verifyWasInvoked(this.transactionGateway, 0)
            .save(any(Transaction.class));
    }

    @ParameterizedTest
    @MethodSource("transactionDtosInvalids")
    @DisplayName("Integration - Deve tentar salvar uma transacao para cada tipo de operacoes invalidas e retornar erro.")
    void shouldTrySaveTransactionsButReturnExceptionWhenAmountIsInvalidForTypeSpecified(final TransactionDto dto) throws Exception {
        //GIVEN is param
        final var responseErrorExpected = new ResponseError(
            new InvalidAmountException(OperationsType.fromString(dto.getOperationType())).getMessage(),
            HttpStatus.BAD_REQUEST.value()
        );

        //WHEN
        final var resultActions = requestPost(dto, URI.create(BASE_URL_TRANSACTIONS));

        //THEN
        final var responseJson = resultActions
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();

        final var responseErrorActual = this.objectMapper.readValue(responseJson, ResponseError.class);

        compareUsingRecursiveComparison(responseErrorExpected, responseErrorActual);
        verifyWasInvoked(this.accountGatewayMock, 0)
            .find(dto.getAccountId());
        verifyWasInvoked(this.transactionGateway, 0)
            .save(any(Transaction.class));
    }

    @ParameterizedTest
    @MethodSource("transactionDtosInvalidOperationTypes")
    @DisplayName(
        """
        Integration - Deve tentar salvar uma transacao para cada tipo de operacoes 
        com operation_types invalidos e retornar erro.
        """)
    void shouldTrySaveTransactionsButReturnExceptionWhenOperationTypesIsInvalid(final TransactionDto dto) throws Exception {
        //GIVEN is param
        final var responseErrorExpected = new ResponseError(
            new InvalidOperationTypeException(dto.getOperationType()).getMessage(),
            HttpStatus.BAD_REQUEST.value()
        );

        //WHEN
        final var resultActions = requestPost(dto, URI.create(BASE_URL_TRANSACTIONS));

        //THEN
        final var responseJson = resultActions
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();

        final var responseErrorActual = this.objectMapper.readValue(responseJson, ResponseError.class);

        compareUsingRecursiveComparison(responseErrorExpected, responseErrorActual);
        verifyWasInvoked(this.accountGatewayMock, 0)
            .find(dto.getAccountId());
        verifyWasInvoked(this.transactionGateway, 0)
            .save(any(Transaction.class));
    }

}
