package br.com.transaction.infrastructure;

import br.com.transaction.core.gateway.AccountGateway;
import br.com.transaction.core.gateway.BalanceGateway;
import br.com.transaction.core.gateway.TransactionGateway;
import br.com.transaction.core.usecase.account.UseCaseAccount;
import br.com.transaction.core.usecase.account.UseCaseAccountImpl;
import br.com.transaction.core.usecase.transaction.UseCaseTransaction;
import br.com.transaction.core.usecase.transaction.UseCaseTransactionImpl;
import br.com.transaction.dataprovider.database.gateway.AccountGatewayImpl;
import br.com.transaction.dataprovider.database.gateway.BalanceGatewayImpl;
import br.com.transaction.dataprovider.database.gateway.TransactionGatewayImpl;
import br.com.transaction.dataprovider.database.repository.AccountRepository;
import br.com.transaction.dataprovider.database.repository.BalanceRepository;
import br.com.transaction.dataprovider.database.repository.TransactionRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    private final BalanceRepository balanceRepository;

    ApplicationConfig(
        final AccountRepository accountRepository,
        final TransactionRepository transactionRepository,
        final BalanceRepository balanceRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.balanceRepository = balanceRepository;
    }

    @Bean
    @ConditionalOnMissingBean(BalanceGateway.class)
    public BalanceGateway balanceGateway() {
        return new BalanceGatewayImpl(this.balanceRepository);
    }

    @Bean
    @ConditionalOnMissingBean(AccountGateway.class)
    public AccountGateway accountGateway() {
        return new AccountGatewayImpl(this.accountRepository);
    }

    @Bean
    @ConditionalOnMissingBean(TransactionGateway.class)
    public TransactionGateway transactionGateway() {
        return new TransactionGatewayImpl(this.transactionRepository);
    }

    @Bean
    @ConditionalOnMissingBean(UseCaseTransaction.class)
    public UseCaseTransaction useCaseTransaction(
        final AccountGateway accountGateway,
        final TransactionGateway transactionGateway,
        final BalanceGateway balanceGateway) {
        return new UseCaseTransactionImpl(transactionGateway, accountGateway, balanceGateway);
    }

    @Bean
    @ConditionalOnMissingBean(UseCaseAccount.class)
    public UseCaseAccount useCaseAccount(
        final AccountGateway accountGateway,
        final BalanceGateway balanceGateway) {
        return new UseCaseAccountImpl(accountGateway, balanceGateway);
    }

}
