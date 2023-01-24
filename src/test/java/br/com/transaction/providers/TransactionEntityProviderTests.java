package br.com.transaction.providers;

import br.com.transaction.dataprovider.database.entity.OperationsType;
import br.com.transaction.dataprovider.database.entity.Transaction;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

import static br.com.transaction.ConstantsTests.AMOUNT_POSITIVE;

public class TransactionEntityProviderTests implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(final ExtensionContext context) throws Exception {
        final var transactionEntity = Transaction.builder().amount(AMOUNT_POSITIVE).type(OperationsType.PAGAMENTO).build();
        return Stream.of(transactionEntity).map(Arguments::of);
    }

}
