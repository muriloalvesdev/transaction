package br.com.transaction.core.usecase.account;

import br.com.transaction.BaseUnitTest;
import br.com.transaction.core.exception.AccountNotFoundException;
import br.com.transaction.core.exception.AlreadyDocumentNumberException;
import br.com.transaction.dataprovider.database.entity.Account;
import br.com.transaction.entrypoint.dto.AccountDto;
import br.com.transaction.providers.AccountDtoProviderTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class UseCaseAccountImplUnitTest extends BaseUnitTest {

    private final UseCaseAccount usecase = new UseCaseAccountImpl(this.accountGatewayMock);

    @ParameterizedTest
    @ArgumentsSource(AccountDtoProviderTests.class)
    @DisplayName("Mock - Deve salvar uma conta na base de dados com sucesso.")
    void shouldSaveAccountWithSuccess(final AccountDto dto) {
        //GIVEN
        final var accountEntity = toAccountEntity(dto);
        given(this.accountGatewayMock.existsByDocumentNumber(dto.getDocumentNumber()))
            .willReturn(false);

        given(this.accountGatewayMock.save(any(Account.class))).willReturn(accountEntity);

        //WHEN
        this.usecase.save(dto);

        //THEN
        verifyWasInvoked(this.accountGatewayMock, 1)
            .existsByDocumentNumber(dto.getDocumentNumber());
        verifyWasInvoked(this.accountGatewayMock, 1)
            .save(any(Account.class));
    }

    @ParameterizedTest
    @ArgumentsSource(AccountDtoProviderTests.class)
    @DisplayName("Mock - Deve tentar salvar uma conta que já existe na base de dados e retornar erro.")
    void shouldTrySaveAccountButReturnError(final AccountDto dto) {
        //GIVEN
        final var exceptionExpected = new AlreadyDocumentNumberException(dto.getDocumentNumber());
        given(this.accountGatewayMock.existsByDocumentNumber(dto.getDocumentNumber()))
            .willReturn(true);

        //WHEN
        final var exceptionActual =
            assertThrows(AlreadyDocumentNumberException.class, () -> this.usecase.save(dto));

        //THEN
        verifyWasInvoked(this.accountGatewayMock, 1)
            .existsByDocumentNumber(dto.getDocumentNumber());
        verifyWasInvoked(this.accountGatewayMock, 0)
            .save(any(Account.class));

        isEquals(exceptionExpected.getMessage(), exceptionActual.getMessage());
    }

    @ParameterizedTest
    @ArgumentsSource(AccountDtoProviderTests.class)
    @DisplayName("Mock - Deve buscar uma conta com sucesso.")
    void shouldFindAccountWithSuccess(final AccountDto dto) {
        //GIVEN
        final var accountEntity = toAccountEntity(dto);

        given(this.accountGatewayMock.find(dto.getAccountId()))
            .willReturn(Optional.ofNullable(accountEntity));

        //WHEN
        final var response = this.usecase.find(dto.getAccountId());

        //THEN
        compareUsingRecursiveComparison(dto, response);
        verifyWasInvoked(this.accountGatewayMock, 1).find(dto.getAccountId());
    }

    @ParameterizedTest
    @ArgumentsSource(AccountDtoProviderTests.class)
    @DisplayName("Mock - Deve tentar buscar uma conta e não encontrar.")
    void shouldTryFindAccountButReturnError(final AccountDto dto) {
        //GIVEN
        final var exceptionExpected = new AccountNotFoundException(dto.getAccountId());

        given(this.accountGatewayMock.find(dto.getAccountId()))
            .willReturn(Optional.empty());

        //WHEN
        final var expcetionActual =
            assertThrows(AccountNotFoundException.class, () -> this.usecase.find(dto.getAccountId()));

        //THEN
        verifyWasInvoked(this.accountGatewayMock, 1).find(dto.getAccountId());
        isEquals(exceptionExpected.getMessage(), expcetionActual.getMessage());
    }

}
