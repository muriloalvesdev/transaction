package br.com.transaction.dataprovider.database.gateway;

import br.com.transaction.BaseUnitTest;
import br.com.transaction.core.gateway.TransactionGateway;
import br.com.transaction.dataprovider.database.entity.Transaction;
import br.com.transaction.providers.TransactionEntityProviderTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static org.mockito.BDDMockito.given;

class TransactionGatewayImplUnitTest extends BaseUnitTest {

    private final TransactionGateway gateway = new TransactionGatewayImpl(this.transactionRepositoryMock);

    @ParameterizedTest
    @ArgumentsSource(TransactionEntityProviderTests.class)
    @DisplayName("Mock - Deve salvar uma transacao na base de dados.")
    void shouldSave(final Transaction entity) {
        //GIVEN
        given(this.transactionRepositoryMock.saveAndFlush(entity))
            .willReturn(entity);

        //WHEN
        final var entityActual = this.gateway.save(entity);

        //THEN
        compareUsingRecursiveComparison(entity, entityActual);
        verifyWasInvoked(this.transactionRepositoryMock, 1)
            .saveAndFlush(entity);
    }

}
