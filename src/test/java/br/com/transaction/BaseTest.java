package br.com.transaction;

import br.com.transaction.dataprovider.database.entity.Account;
import br.com.transaction.dataprovider.database.entity.OperationsType;
import br.com.transaction.dataprovider.database.entity.Transaction;
import br.com.transaction.entriypoint.dto.AccountDto;
import br.com.transaction.entriypoint.dto.TransactionDto;
import org.junit.jupiter.params.provider.Arguments;
import org.mockito.internal.MockitoCore;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;
import java.util.stream.Stream;

import static br.com.transaction.ConstantsTests.ACCOUNT_ID;
import static br.com.transaction.ConstantsTests.AMOUNT_NEGATIVE;
import static br.com.transaction.ConstantsTests.AMOUNT_POSITIVE;
import static br.com.transaction.ConstantsTests.COMPRA_A_VISTA;
import static br.com.transaction.ConstantsTests.COMPRA_PARCELADA;
import static br.com.transaction.ConstantsTests.DOCUMENT_NUMBER;
import static br.com.transaction.ConstantsTests.PAGAMENTO;
import static br.com.transaction.ConstantsTests.SAQUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;

@ActiveProfiles("test")
class BaseTest {

    //MOCKITO
    private static final MockitoCore MOCKITO_CORE = new MockitoCore();

    protected Account toAccountEntity(final AccountDto dto) {
        return Account.builder().uuid(dto.getAccountId()).documentNumber(dto.getDocumentNumber()).build();
    }

    protected Account toAccountEntity(final UUID accountId) {
        return Account.builder().uuid(accountId).documentNumber(DOCUMENT_NUMBER).build();
    }

    protected Account toAccountEntity() {
        return Account.builder()
            .uuid(ACCOUNT_ID)
            .documentNumber(DOCUMENT_NUMBER)
            .build();
    }

    protected Account toAccountEntity(
        final UUID accountId,
        final String documentNumber) {
        return Account.builder()
            .uuid(accountId)
            .documentNumber(documentNumber)
            .build();
    }

    protected Transaction toTransactionEntity(final TransactionDto dto) {
        return Transaction.builder()
            .account(toAccountEntity(dto.getAccountId()))
            .type(OperationsType.fromString(dto.getOperationType()))
            .amount(dto.getAmount()).build();
    }

    protected Transaction toTransactionEntity(
        final TransactionDto dto,
        final Account accountEntity) {
        return Transaction.builder()
            .account(accountEntity)
            .type(OperationsType.fromString(dto.getOperationType()))
            .amount(dto.getAmount()).build();
    }

    protected static Stream<Arguments> transactionDtosInvalids() {
        return Stream.of(new TransactionDto(ACCOUNT_ID, PAGAMENTO.getTypeName(), AMOUNT_NEGATIVE),
            new TransactionDto(ACCOUNT_ID, COMPRA_A_VISTA.getTypeName(), AMOUNT_POSITIVE),
            new TransactionDto(ACCOUNT_ID, COMPRA_PARCELADA.getTypeName(), AMOUNT_POSITIVE),
            new TransactionDto(ACCOUNT_ID, SAQUE.getTypeName(), AMOUNT_POSITIVE)).map(Arguments::of);
    }

    protected static Stream<Arguments> transactionDtosInvalidOperationTypes() {
        return Stream.of(new TransactionDto(ACCOUNT_ID, "anything", AMOUNT_NEGATIVE),
            new TransactionDto(ACCOUNT_ID, "anything", AMOUNT_POSITIVE),
            new TransactionDto(ACCOUNT_ID, "anything", AMOUNT_POSITIVE),
            new TransactionDto(ACCOUNT_ID, "anything", AMOUNT_POSITIVE)).map(Arguments::of);
    }

    protected static Stream<Arguments> transactionDtosValids() {
        return Stream.of(new TransactionDto(UUID.randomUUID(), PAGAMENTO.getTypeName(), AMOUNT_POSITIVE),
            new TransactionDto(UUID.randomUUID(), COMPRA_A_VISTA.getTypeName(), AMOUNT_NEGATIVE),
            new TransactionDto(UUID.randomUUID(), COMPRA_PARCELADA.getTypeName(), AMOUNT_NEGATIVE),
            new TransactionDto(UUID.randomUUID(), SAQUE.getTypeName(), AMOUNT_NEGATIVE)).map(Arguments::of);
    }

    protected static <T> T verifyWasInvoked(final T mock, final int quantityOfInvocation) {
        return MOCKITO_CORE.verify(mock, times(quantityOfInvocation));
    }

    protected static void isEquals(final Object expected, final Object actual) {
        assertThat(actual).isEqualTo(expected);
    }

    protected void compareUsingRecursiveComparison(final Object expected, final Object actual, final String... ignoreFields) {
        assertThat(actual).usingRecursiveComparison().ignoringFields(ignoreFields).isEqualTo(expected);
    }

    protected void isNotNull(final Object value) {
        assertThat(value).isNotNull();
    }

}
