package br.com.transaction.providers;

import br.com.transaction.dataprovider.database.entity.Account;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

import static br.com.transaction.ConstantsTests.ACCOUNT_ID;
import static br.com.transaction.ConstantsTests.DOCUMENT_NUMBER;

public class AccountEntityProviderTests implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(final ExtensionContext context) throws Exception {
        return Stream.of(Account.builder().uuid(ACCOUNT_ID).documentNumber(DOCUMENT_NUMBER).build()).map(Arguments::of);
    }

}
