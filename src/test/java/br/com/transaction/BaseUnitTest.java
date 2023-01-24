package br.com.transaction;

import br.com.transaction.core.gateway.AccountGateway;
import br.com.transaction.core.gateway.TransactionGateway;
import br.com.transaction.core.usecase.UseCaseAccount;
import br.com.transaction.core.usecase.UseCaseTransaction;
import br.com.transaction.dataprovider.database.repository.AccountRepository;
import br.com.transaction.dataprovider.database.repository.TransactionRepository;
import org.junit.jupiter.api.Tag;

import static org.mockito.Mockito.mock;

@Tag("unit-test")
public class BaseUnitTest extends BaseTest {
    //GATEWAYS MOCK
    protected final AccountGateway accountGatewayMock = mock(AccountGateway.class);
    protected final TransactionGateway transactionGatewayMock = mock(TransactionGateway.class);

    //REPOSITORYS MOCK
    protected final AccountRepository accountRepositoryMock = mock(AccountRepository.class);
    protected final TransactionRepository transactionRepositoryMock = mock(TransactionRepository.class);

    // USECASES MOCK
    protected final UseCaseAccount useCaseAccountMock = mock(UseCaseAccount.class);
    protected final UseCaseTransaction useCaseTransactionMock = mock(UseCaseTransaction.class);
}
