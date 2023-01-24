package br.com.transaction.dataprovider.database.repository;

import br.com.transaction.BaseDataBaseTest;
import br.com.transaction.dataprovider.database.entity.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static br.com.transaction.ConstantsTests.AMOUNT_POSITIVE;
import static br.com.transaction.ConstantsTests.PAGAMENTO;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionTestRepositoryIntegration extends BaseDataBaseTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    private Transaction transactionEntity;

    @BeforeEach
    void setUp() {
        final var accountSaved = this.accountRepository.saveAndFlush(toAccountEntity());
        transactionEntity = this.transactionRepository
            .saveAndFlush(
                Transaction.builder()
                    .type(PAGAMENTO)
                    .amount(AMOUNT_POSITIVE)
                    .account(accountSaved)
                    .build());
    }

    @Test
    @DisplayName(
        """
            Integration - Deve buscar a transacao salva na base de dados por ID.
        """
    )
    void shouldFindByIdAndFound() {
        //GIVEN is transaction in beforeEach()

        //WHEN
        final var optionalTransaction = this.transactionRepository.findById(transactionEntity.getUuid());

        //THEN
        assertTrue(optionalTransaction.isPresent());
        compareUsingRecursiveComparison(transactionEntity, optionalTransaction.get(), "createdAt", "updatedAt");
    }

    @Test
    @DisplayName(
        """
            Integration - Deve buscar a transacao salva na base de dados por ID.
        """
    )
    void shouldRemoveTransactionEntityAndFindByIdAndNotFound() {
        //GIVEN is transaction in beforeEach()

        //WHEN
        this.transactionRepository.delete(transactionEntity);

        //THEN
        final var optionalTransaction = this.transactionRepository.findById(transactionEntity.getUuid());
        assertFalse(optionalTransaction.isPresent());
    }

}
