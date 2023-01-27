package br.com.transaction.core.usecase.transaction;

import br.com.transaction.core.exception.AccountNotFoundException;
import br.com.transaction.core.exception.BalanceNegativeException;
import br.com.transaction.core.exception.InvalidAmountException;
import br.com.transaction.core.exception.ZeroBalanceException;
import br.com.transaction.core.gateway.AccountGateway;
import br.com.transaction.core.gateway.BalanceGateway;
import br.com.transaction.core.gateway.TransactionGateway;
import br.com.transaction.dataprovider.database.entity.Account;
import br.com.transaction.dataprovider.database.entity.OperationsType;
import br.com.transaction.dataprovider.database.entity.Transaction;
import br.com.transaction.entrypoint.dto.TransactionDto;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

public class UseCaseTransactionImpl implements UseCaseTransaction {

    private final TransactionGateway transactionGateway;
    private final AccountGateway accountGateway;

    private BalanceGateway gateway;

    public UseCaseTransactionImpl(
        final TransactionGateway transactionGateway,
        final AccountGateway accountGateway,
        final BalanceGateway gateway) {
        this.transactionGateway = transactionGateway;
        this.accountGateway = accountGateway;
        this.gateway = gateway;
    }

    @Transactional
    public String save(final TransactionDto transactionDto) {
        final var type = OperationsType.fromString(transactionDto.getOperationType());
        validateTransactionDto(transactionDto, type);

        final var accountEntity = getAccountEntity(transactionDto);
        updateBalance(transactionDto, accountEntity);

        final var uuid = UUID.randomUUID().toString();
        final var amount = transactionDto.getAmount();
        final var transactionEntity = Transaction.builder()
            .type(type)
            .amount(amount)
            .account(accountEntity)
            .uuid(uuid)
            .build();

        return this.transactionGateway.save(transactionEntity).getUuid();
    }

    private void updateBalance(final TransactionDto transactionDto, final Account accountEntity) {
        final var balance = this.gateway.findByAccount(accountEntity);
        final var limit = balance.getAvailableCreditLimit();
        validateLimit(limit);
        final var newAmount = limit.add(transactionDto.getAmount());
        validateNewAmount(newAmount);

        balance.setAvailableCreditLimit(newAmount);
        this.gateway.save(balance);
    }

    private void validateTransactionDto(
        final TransactionDto transactionDto,
        final OperationsType type) {
        final var rule = type.getRule();
        final var amount = transactionDto.getAmount();
        if (rule.isOperationInvalid(amount)) {
            final var sign = rule.isPositive(amount) ? "POSITIVE" : "NEGATIVE";
            throw new InvalidAmountException(type, sign);
        }
    }

    private Account getAccountEntity(
        final TransactionDto transactionDto) {
        final var accountId = transactionDto.getAccountId();
        return this.accountGateway.find(accountId)
            .orElseThrow(() -> new AccountNotFoundException(transactionDto.getAccountId()));
    }

    private void validateLimit(
        final BigDecimal limit) {
        if (limit.intValue() == 0) {
            throw new ZeroBalanceException();
        }
    }

    private void validateNewAmount(final BigDecimal newAmount) {
        if (newAmount.intValue() < 0) {
            throw new BalanceNegativeException();
        }
    }

}
