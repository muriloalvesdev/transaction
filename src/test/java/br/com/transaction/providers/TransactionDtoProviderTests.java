package br.com.transaction.providers;

import br.com.transaction.entriypoint.dto.TransactionDto;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

import static br.com.transaction.ConstantsTests.ACCOUNT_ID;
import static br.com.transaction.ConstantsTests.AMOUNT_POSITIVE;
import static br.com.transaction.ConstantsTests.PAGAMENTO;

public class TransactionDtoProviderTests implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(final ExtensionContext context) throws Exception {
        return Stream.of(new TransactionDto(ACCOUNT_ID, PAGAMENTO.name(), AMOUNT_POSITIVE)).map(Arguments::of);
    }

}
