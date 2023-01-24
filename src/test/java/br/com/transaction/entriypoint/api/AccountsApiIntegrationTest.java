package br.com.transaction.entriypoint.api;

import br.com.transaction.BaseIntegrationTests;
import br.com.transaction.core.exception.AlreadyDocumentNumberException;
import br.com.transaction.dataprovider.database.entity.Account;
import br.com.transaction.entriypoint.dto.AccountDto;
import br.com.transaction.entriypoint.dto.ResponseError;
import br.com.transaction.providers.AccountDtoProviderTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.net.URI;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccountsApiIntegrationTest extends BaseIntegrationTests {

    @ParameterizedTest
    @ArgumentsSource(AccountDtoProviderTests.class)
    @DisplayName("Integration - Deve salvar uma conta na base de dados.")
    void shouldSaveWithSuccess(final AccountDto dto) throws Exception {
        //GIVEN
        final var accountEntity = toAccountEntity(dto);
        given(this.accountGatewayMock.existsByDocumentNumber(dto.getDocumentNumber()))
            .willReturn(false);
        given(this.accountGatewayMock.save(any(Account.class)))
            .willReturn(accountEntity);
        //WHEN
        final var resultActions = requestPost(dto, URI.create(BASE_URL_ACCOUNTS));

        //THEN
        resultActions.andExpect(status().isCreated());
    }

    @ParameterizedTest
    @ArgumentsSource(AccountDtoProviderTests.class)
    @DisplayName(
        """
        Integration - Deve tentar salvar uma conta que já existe na base de dados
        e validar se está retornando conflito.
        """)
    void shouldTrySaveButAlreadyExistsDocumentNumber(final AccountDto dto) throws Exception {
        //GIVEN
        final var responseErrorExpected = new ResponseError(
            new AlreadyDocumentNumberException(dto.getDocumentNumber()).getMessage(),
            409);
        given(this.accountGatewayMock.existsByDocumentNumber(dto.getDocumentNumber()))
            .willReturn(true);

        //WHEN
        final var resultActions = requestPost(dto, URI.create(BASE_URL_ACCOUNTS));

        //THEN
        final var responseJson = resultActions
            .andExpect(status().isConflict())
            .andReturn()
            .getResponse()
            .getContentAsString();

        final var responseErrorActual = this.objectMapper.readValue(responseJson, ResponseError.class);

        compareUsingRecursiveComparison(responseErrorExpected, responseErrorActual);
    }

    @ParameterizedTest
    @ArgumentsSource(AccountDtoProviderTests.class)
    @DisplayName(
        """
        Integration - Deve buscar uma conta que já está salva na base de dados e encontrá-la.
        """
    )
    void shouldFindWithSuccess(final AccountDto dto) throws Exception {
        //GIVEN
        final var accountEntity = toAccountEntity(dto);
        final var uri = URI.create(BASE_URL_ACCOUNTS.concat(dto.getAccountId().toString()));
        given(this.accountGatewayMock.find(dto.getAccountId()))
            .willReturn(Optional.of(accountEntity));

        //WHEN
        final var resultActions = requestGet(dto.getAccountId(), uri);

        //THEN
        final var responseJson = resultActions
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        final var responseDto = this.objectMapper.readValue(responseJson, AccountDto.class);

        compareUsingRecursiveComparison(dto, responseDto);
    }

}
