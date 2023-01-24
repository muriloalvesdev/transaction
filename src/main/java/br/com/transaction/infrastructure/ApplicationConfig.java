package br.com.transaction.infrastructure;

import br.com.transaction.core.gateway.AccountGateway;
import br.com.transaction.core.gateway.TransactionGateway;
import br.com.transaction.core.usecase.UseCaseAccount;
import br.com.transaction.core.usecase.UseCaseTransaction;
import br.com.transaction.core.usecase.impl.UseCaseAccountImpl;
import br.com.transaction.core.usecase.impl.UseCaseTransactionImpl;
import br.com.transaction.dataprovider.database.gateway.AccountGatewayImpl;
import br.com.transaction.dataprovider.database.gateway.TransactionGatewayImpl;
import br.com.transaction.dataprovider.database.repository.AccountRepository;
import br.com.transaction.dataprovider.database.repository.TransactionRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public ApplicationConfig(final AccountRepository accountRepository, final TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
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
    public UseCaseTransaction useCaseTransaction(final AccountGateway accountGateway, final TransactionGateway transactionGateway) {
        return new UseCaseTransactionImpl(transactionGateway, accountGateway);
    }

    @Bean
    @ConditionalOnMissingBean(UseCaseAccount.class)
    public UseCaseAccount useCaseAccount(final AccountGateway accountGateway) {
        return new UseCaseAccountImpl(accountGateway);
    }

}
