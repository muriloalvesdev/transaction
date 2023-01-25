package br.com.transaction.dataprovider.database.gateway;

import br.com.transaction.BaseUnitTest;
import br.com.transaction.core.gateway.AccountGateway;
import br.com.transaction.dataprovider.database.entity.Account;
import br.com.transaction.providers.AccountEntityProviderTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.stream.Stream;

import static br.com.transaction.ConstantsTests.ACCOUNT_ID;
import static br.com.transaction.ConstantsTests.DOCUMENT_NUMBER;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

class AccountGatewayImplUnitTest extends BaseUnitTest {

    private final AccountGateway gateway = new AccountGatewayImpl(this.accountRepositoryMock);

    @ParameterizedTest
    @ArgumentsSource(AccountEntityProviderTests.class)
    @DisplayName("Mock - Deve salvar uma conta na base.")
    void shouldSave(final Account entity) {
        //GIVEN
        given(this.accountRepositoryMock.saveAndFlush(entity))
            .willReturn(entity);

        //WHEN
        final var entityResponse = this.gateway.save(entity);

        //THEN
        verifyWasInvoked(this.accountRepositoryMock, 1)
            .saveAndFlush(entity);
        compareUsingRecursiveComparison(entity, entityResponse);
    }

    @ParameterizedTest
    @ArgumentsSource(AccountEntityProviderTests.class)
    @DisplayName("Mock - Deve buscar uma conta na base e encontrá-la.")
    void mustSearchAndFound(final Account enttiy) {
        //GIVEN
        final var accountId = enttiy.getUuid();

        given(this.accountRepositoryMock.findById(accountId))
            .willReturn(Optional.of(enttiy));

        //WHEN
        final var responseOptional = this.gateway.find(accountId);

        //THEN
        verifyWasInvoked(this.accountRepositoryMock, 1)
            .findById(enttiy.getUuid());
        assertTrue(responseOptional.isPresent());
        compareUsingRecursiveComparison(enttiy, responseOptional.get());
    }

    @Test
    @DisplayName("Mock - Deve buscar uma conta na base e não encontrá-la.")
    void mustSearchAndNotFound() {
        //GIVEN
        given(this.accountRepositoryMock.findById(ACCOUNT_ID))
            .willReturn(Optional.empty());

        //WHEN
        final var responseOptional = this.gateway.find(ACCOUNT_ID);

        //THEN
        verifyWasInvoked(this.accountRepositoryMock, 1)
            .findById(ACCOUNT_ID);
        assertTrue(responseOptional.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("testAccountsExistsAndNotExists")
    @DisplayName("Mock - Deve validar se exist conta na base de dados com todos os cenarios, nesse caso. True e False.")
    void shouldValidateExistsAccountByDocumentNumberWithAllScenarios(final boolean exists) {
        //GIVEN
        given(this.accountRepositoryMock.existsByDocumentNumber(DOCUMENT_NUMBER))
            .willReturn(exists);

        //WHEN
        final var existsByDocumentNumber = this.gateway.existsByDocumentNumber(DOCUMENT_NUMBER);

        //THEN
        verifyWasInvoked(this.accountRepositoryMock, 1)
            .existsByDocumentNumber(DOCUMENT_NUMBER);

        isEquals(exists, existsByDocumentNumber);
    }

    private static Stream<Arguments> testAccountsExistsAndNotExists() {
        return Stream.of(true, false).map(Arguments::of);
    }

}
