package br.com.transaction.entrypoint.api;

import br.com.transaction.BaseApiUnitTest;
import br.com.transaction.entrypoint.api.TransactionApi;
import br.com.transaction.entrypoint.dto.TransactionDto;
import br.com.transaction.providers.TransactionDtoProviderTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.http.HttpStatus;

import static org.mockito.BDDMockito.given;

class TransactionApiUnitTest extends BaseApiUnitTest {

    private final TransactionApi api = new TransactionApi(this.useCaseTransactionMock);

    @ParameterizedTest
    @ArgumentsSource(TransactionDtoProviderTests.class)
    @DisplayName("Mock - Deve salvar uma transacao na base de dados.")
    void shouldSave(final TransactionDto dto) {
        //GIVEN
        given(this.useCaseTransactionMock.save(dto))
            .willReturn(dto.getAccountId());

        //WHEN
        final var response = this.api.save(dto);

        //THEN
        isEquals(HttpStatus.CREATED, response.getStatusCode());
        isNotNull(response.getHeaders().getLocation());

        verifyWasInvoked(this.useCaseTransactionMock, 1)
            .save(dto);
    }

}
