package br.com.transaction.entriypoint.api;

import br.com.transaction.BaseApiUnitTest;
import br.com.transaction.entriypoint.dto.AccountDto;
import br.com.transaction.providers.AccountDtoProviderTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.http.HttpStatus;

import static org.mockito.BDDMockito.given;

class AccountsApiUnitTest extends BaseApiUnitTest {

    private final AccountsApi api = new AccountsApi(this.useCaseAccountMock);

    @ParameterizedTest
    @ArgumentsSource(AccountDtoProviderTests.class)
    @DisplayName("Mock - Deve salvar uma conta na base de dados.")
    void shouldSave(final AccountDto dto) {
        //GIVEN
        given(this.useCaseAccountMock.save(dto))
            .willReturn(dto.getAccountId());

        //WHEN
        final var response = this.api.save(dto);

        //THEN
        isEquals(HttpStatus.CREATED, response.getStatusCode());
        isNotNull(response.getHeaders().getLocation());

        verifyWasInvoked(this.useCaseAccountMock, 1)
            .save(dto);
    }

    @ParameterizedTest
    @ArgumentsSource(AccountDtoProviderTests.class)
    @DisplayName("Mock - Deve buscar uma conta salva por ID.")
    void shouldFindById(final AccountDto dto) {
        //GIVEN
        given(this.useCaseAccountMock.find(dto.getAccountId()))
            .willReturn(dto);

        //WHEN
        final var response = this.api.find(dto.getAccountId());

        //THEN
        verifyWasInvoked(this.useCaseAccountMock, 1)
            .find(dto.getAccountId());

        isEquals(HttpStatus.OK, response.getStatusCode());
        compareUsingRecursiveComparison(dto, response.getBody());
    }

}
