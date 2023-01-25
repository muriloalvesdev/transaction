package br.com.transaction.core.usecase.transaction;

import br.com.transaction.core.exception.AccountNotFoundException;
import br.com.transaction.core.exception.InvalidAmountException;
import br.com.transaction.core.gateway.AccountGateway;
import br.com.transaction.core.gateway.TransactionGateway;
import br.com.transaction.core.usecase.operation.OperationRule;
import br.com.transaction.dataprovider.database.entity.Account;
import br.com.transaction.dataprovider.database.entity.OperationsType;
import br.com.transaction.dataprovider.database.entity.Transaction;
import br.com.transaction.entrypoint.dto.TransactionDto;

import java.math.BigDecimal;
import java.util.UUID;

public class UseCaseTransactionImpl implements UseCaseTransaction {

    private final TransactionGateway transactionGateway;
    private final AccountGateway accountGateway;

    public UseCaseTransactionImpl(
        final TransactionGateway transactionGateway,
        final AccountGateway accountGateway) {
        this.transactionGateway = transactionGateway;
        this.accountGateway = accountGateway;
    }

    public String save(final TransactionDto dto) {
        final var type = OperationsType.fromString(dto.getOperationType());
        validateTransactionDto(dto, type);

        final var amount = type.getRule().defineAmount(dto.getAmount());
        final var accountEntity = getAccountEntity(dto);
        final var uuid = UUID.randomUUID().toString();

        final var transactionEntity = Transaction.builder()
            .type(type)
            .amount(amount)
            .account(accountEntity)
            .uuid(uuid)
            .build();

        return this.transactionGateway.save(transactionEntity).getUuid();
    }

    private void validateTransactionDto(final TransactionDto dto, final OperationsType type) {
        if (isAmountInvalid(dto.getAmount(), type.getRule())) {
            final var sign = type.getRule().isPositive() ? "POSITIVE" : "NEGATIVE";
            throw new InvalidAmountException(type, sign);
        }
    }

    private boolean isAmountInvalid(final BigDecimal amount, final OperationRule rule) {
        return rule.isAmountNegative(amount) && rule.isPositive() ||
            rule.isAmountPositive(amount) && rule.isNegative() ||
            rule.isAmountZero(amount);
    }


    private Account getAccountEntity(
        final TransactionDto dto) {
        final var accountId = dto.getAccountId();
        return this.accountGateway.find(accountId)
            .orElseThrow(() -> new AccountNotFoundException(dto.getAccountId()));
    }

}
