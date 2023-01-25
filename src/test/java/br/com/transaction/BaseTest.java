package br.com.transaction;

import br.com.transaction.dataprovider.database.entity.Account;
import br.com.transaction.dataprovider.database.entity.OperationsType;
import br.com.transaction.dataprovider.database.entity.Transaction;
import br.com.transaction.entrypoint.dto.AccountDto;
import br.com.transaction.entrypoint.dto.TransactionDto;
import org.junit.jupiter.params.provider.Arguments;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;
import java.util.stream.Stream;

import static br.com.transaction.ConstantsTests.ACCOUNT_ID;
import static br.com.transaction.ConstantsTests.AMOUNT_NEGATIVE;
import static br.com.transaction.ConstantsTests.AMOUNT_POSITIVE;
import static br.com.transaction.ConstantsTests.AMOUNT_ZERO;
import static br.com.transaction.ConstantsTests.COMPRA_A_VISTA;
import static br.com.transaction.ConstantsTests.COMPRA_PARCELADA;
import static br.com.transaction.ConstantsTests.DOCUMENT_NUMBER;
import static br.com.transaction.ConstantsTests.PAGAMENTO;
import static br.com.transaction.ConstantsTests.SAQUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;

@ActiveProfiles("test")
class BaseTest {

    /**
     *
     * @param AccountDTO
     * @Description Funciona como um conversor de DTO para entidade,
     * utilizado no corpo de métodos de teste.
     */
    protected Account toAccountEntity(final AccountDto dto) {
        return Account.builder().uuid(dto.getAccountId()).documentNumber(dto.getDocumentNumber()).build();
    }

    /**
     *
     * @param accountId
     * @Description Funciona como um factory,
     * cria-se uma conta a partir de um ID informado.
     * É utilizado em corpo de métodos de testes quem contém
     * o ID da conta, mas não o número de documento.
     *
     * Este método é estático porque é reutilizado em outros métodos de testes
     * que utilizam métodos como parâmetro.
     */
    protected static Account toAccountEntity(final String accountId) {
        return Account.builder().uuid(accountId).documentNumber(DOCUMENT_NUMBER).build();
    }

    /**
     *
     * @Description Funciona como um factory que cria sempre uma conta
     * contendo os mesmos dados. (ID e DocumentNumber).
     */
    protected Account toAccountEntity() {
        return Account.builder()
            .uuid(ACCOUNT_ID)
            .documentNumber(DOCUMENT_NUMBER)
            .build();
    }

    /**
     *
     * @Description Responsável por criar TransactionDTOs com cenários inválidos.
     * Cenários válidos: apenas a transacão de PAYMENT pode ter um valor positivo.
     *
     * Então esse teste cria transacões diferente de todos os cenários possíveis como, por exemplo,
     * Transacão de PAYMENT com valor negativo.
     * Todos os outros tipos de transacão com valor positivo.
     * Todos os tipos com valor zerado.
     */
    protected static Stream<Arguments> transactionDtosInvalids() {
        return Stream.of(new TransactionDto(ACCOUNT_ID, PAGAMENTO.getTypeName(), AMOUNT_NEGATIVE),
            new TransactionDto(ACCOUNT_ID, COMPRA_A_VISTA.getTypeName(), AMOUNT_POSITIVE),
            new TransactionDto(ACCOUNT_ID, COMPRA_PARCELADA.getTypeName(), AMOUNT_POSITIVE),
            new TransactionDto(ACCOUNT_ID, SAQUE.getTypeName(), AMOUNT_POSITIVE),
            new TransactionDto(ACCOUNT_ID, PAGAMENTO.getTypeName(), AMOUNT_ZERO),
            new TransactionDto(ACCOUNT_ID, COMPRA_A_VISTA.getTypeName(), AMOUNT_ZERO),
            new TransactionDto(ACCOUNT_ID, COMPRA_PARCELADA.getTypeName(), AMOUNT_ZERO),
            new TransactionDto(ACCOUNT_ID, SAQUE.getTypeName(), AMOUNT_ZERO)).map(Arguments::of);
    }

    /**
     *
     * @Description Responsável por criar TransactionDTOs com cenários válidos, ou sejja,
     * uma transacão de PAYMENT com valor positivo e todos os outros cenários com valores NEGATIVOS.
     */
    protected static Stream<Arguments> transactionDtosValids() {
        return Stream.of(new TransactionDto(UUID.randomUUID().toString(), PAGAMENTO.getTypeName(), AMOUNT_POSITIVE),
            new TransactionDto(UUID.randomUUID().toString(), COMPRA_A_VISTA.getTypeName(), AMOUNT_NEGATIVE),
            new TransactionDto(UUID.randomUUID().toString(), COMPRA_PARCELADA.getTypeName(), AMOUNT_NEGATIVE),
            new TransactionDto(UUID.randomUUID().toString(), SAQUE.getTypeName(), AMOUNT_NEGATIVE)).map(Arguments::of);
    }

    /**
     *
     * @param mock
     * @param quantityOfInvocation
     * @Description Esse método verifica se o MOCK informado foi invocado pela quantidade informada.
     * Testes assim validam se um método save(), por exemplo, foi chamado apenas uma vez para garantir
     * que o dado nunca será duplicado.
     */
    protected static <T> T verifyWasInvoked(final T mock, final int quantityOfInvocation) {
        return Mockito.verify(mock, times(quantityOfInvocation));
    }

    /**
     *
     * @param expected
     * @param actual
     *
     * @Description Esse método compara dois objetos para saber se ambos são iguais, muito utilizado em
     * testes que esperam um objeto X e após o processamento querem garantir que o objeto processado é
     * igual ao objeto (esperado) X.
     */
    protected static void isEquals(final Object expected, final Object actual) {
        assertThat(actual).isEqualTo(expected);
    }

    /**
     *
     * @param expected
     * @param actual
     * @param ignoreFields
     *
     * @Description Esse método compara dois objetos utilizando recursividade,
     * e também contém um terceiro parâmetro para ignorar alguns campos que não
     * devem ser comparados como, por exemplo, data de criacão, pois o objeto 'actual' (processado)
     * normalmente terá uma data diferente.
     */
    protected void compareUsingRecursiveComparison(final Object expected, final Object actual, final String... ignoreFields) {
        assertThat(actual).usingRecursiveComparison().ignoringFields(ignoreFields).isEqualTo(expected);
    }

    /**
     *
     * @param value
     * @Description Esse método verifica se o objeto/valor informado não é nulo.
     */
    protected void isNotNull(final Object value) {
        assertThat(value).isNotNull();
    }

}
