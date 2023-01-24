package br.com.transaction.dataprovider.database.repository;

import br.com.transaction.BaseDataBaseTest;
import br.com.transaction.dataprovider.database.entity.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountRepositoryIntegrationTest extends BaseDataBaseTest {

    private final AccountRepository repository;

    private Account accountEntity;

    @Autowired
    public AccountRepositoryIntegrationTest(final AccountRepository repository) {
        this.repository = repository;
    }

    @BeforeEach
    void setUp() {
        accountEntity = this.repository.saveAndFlush(toAccountEntity());
    }

    @Test
    @DisplayName(
        """
            Integration - Deve buscar a identidade salva no inicio da execucao dos testes
            para garantir que foi salva.
        """
    )
    void shouldSaveAndFindAccountWithSuccess() {
        //GIVEN is account in beforeEach()

        //WHEN
        final var entityFound = this.repository.findById(accountEntity.getUuid());

        //THEN
        assertTrue(entityFound.isPresent());
        compareUsingRecursiveComparison(accountEntity, entityFound.get(), "uuid", "createdAt", "updatedAt");
    }

    @Test
    @DisplayName(
        """
            Integration - Deve verificar se existe a identidade salva no inicio da execucao dos testes,
            utilizando o método existsByDocumentNumber() do repository.
        """
    )
    void shouldValidateExistsByDocumentNumber() {
        //GIVEN is account in beforeEach()

        //WHEN
        final var existsByDocumentNumber = this.repository.existsByDocumentNumber(accountEntity.getDocumentNumber());

        //THEN
        assertTrue(existsByDocumentNumber);
    }

    @Test
    @DisplayName(
        """
            Integration - Deve deletar a conta criada e verificar se realmente foi deletada.
        """
    )
    void shouldDeleteAccount() {
        //GIVEN is account in beforeEach()
        final var accountId = accountEntity.getUuid();
        //WHEN
        this.repository.delete(accountEntity);

        //THEN
        final var accountOptional = this.repository.findById(accountId);

        assertTrue(accountOptional.isEmpty());
    }

}
