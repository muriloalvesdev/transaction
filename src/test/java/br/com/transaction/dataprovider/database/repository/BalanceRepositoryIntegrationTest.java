package br.com.transaction.dataprovider.database.repository;

import br.com.transaction.BaseDataBaseTest;
import br.com.transaction.dataprovider.database.entity.Account;
import br.com.transaction.dataprovider.database.entity.Balance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class BalanceRepositoryIntegrationTest extends BaseDataBaseTest {

    private Balance balance;
    private Account accountEntity;

    private final AccountRepository accountRepository;
    private final BalanceRepository balanceRepository;

    @Autowired
    public BalanceRepositoryIntegrationTest(
        final AccountRepository accountRepository,
        final BalanceRepository balanceRepository) {
        this.accountRepository = accountRepository;
        this.balanceRepository = balanceRepository;
    }

    @BeforeEach
    void setUp() {
        this.accountEntity = toAccountEntity(UUID.randomUUID().toString());

        this.balance = Balance.builder()
            .availableCreditLimit(BigDecimal.ZERO)
            .uuid(UUID.randomUUID().toString())
            .account(this.accountRepository.saveAndFlush(accountEntity))
            .build();
        this.balanceRepository.saveAndFlush(this.balance);
    }

    @Test
    void shouldsave() {
        final var balanceById = this.balanceRepository.findById(balance.getUuid());

        assertFalse(balanceById.isEmpty());
    }

    @Test
    void shouldGetBalanceByAccount() {
        final var accountEntity = this.balance.getAccount();
        final var balanceByAccount = this.balanceRepository.findByAccount(accountEntity);
        assertFalse(balanceByAccount.isEmpty());
        compareUsingRecursiveComparison(balanceByAccount.get(), this.balance, "createdAt", "updatedAt");
    }

}
