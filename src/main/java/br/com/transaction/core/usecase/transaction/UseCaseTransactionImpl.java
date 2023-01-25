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

    public String save(final TransactionDto transactionDto) {
        final var type = OperationsType.fromString(transactionDto.getOperationType());
        validateTransactionDto(transactionDto, type);

        final var amount = type.getRule().defineAmount(transactionDto.getAmount());
        final var accountEntity = getAccountEntity(transactionDto);
        final var uuid = UUID.randomUUID().toString();

        final var transactionEntity = Transaction.builder()
            .type(type)
            .amount(amount)
            .account(accountEntity)
            .uuid(uuid)
            .build();

        return this.transactionGateway.save(transactionEntity).getUuid();
    }

    /**
     *
     * @Description basicamente esse método verifica se as regras não estão invertidas, ou seja,
     * se o valor(amount) condiz com a regra(OperationRule).
     */
    private void validateTransactionDto(
        final TransactionDto transactionDto,
        final OperationsType type) {
        final var rule = type.getRule();
        final var amount = transactionDto.getAmount();
        if (isAmountInvalid(amount, rule)) {
            final var sign = rule.isAmountPositive(amount) ? "POSITIVE" : "NEGATIVE";
            throw new InvalidAmountException(type, sign);
        }
    }

    private boolean isAmountInvalid(final BigDecimal amount, final OperationRule rule) {
        return rule.isAmountPositive(amount) && rule.isAmountNegative(amount) ||
            rule.isAmountNegative(amount) && rule.isAmountPositive(amount) ||
            rule.isAmountZero(amount);
    }

    private Account getAccountEntity(
        final TransactionDto transactionDto) {
        final var accountId = transactionDto.getAccountId();
        return this.accountGateway.find(accountId)
            .orElseThrow(() -> new AccountNotFoundException(transactionDto.getAccountId()));
    }

}
