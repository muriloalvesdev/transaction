package br.com.transaction.core.usecase.transaction;

import br.com.transaction.core.exception.AccountNotFoundException;
import br.com.transaction.core.exception.InvalidAmountException;
import br.com.transaction.core.gateway.AccountGateway;
import br.com.transaction.core.gateway.TransactionGateway;
import br.com.transaction.dataprovider.database.entity.Account;
import br.com.transaction.dataprovider.database.entity.OperationsType;
import br.com.transaction.dataprovider.database.entity.Transaction;
import br.com.transaction.entrypoint.dto.TransactionDto;

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

    public UUID save(final TransactionDto dto) {
        final OperationsType type = OperationsType.fromString(dto.getOperationType());

        final var builder = Transaction.builder()
            .type(type);

        setAmount(dto, type, builder);

        final Account accountEntity = getAccountEntity(dto);

        final Transaction transactionEntity = builder.account(accountEntity).build();

        return this.transactionGateway.save(transactionEntity).getUuid();
    }

    private void setAmount(
        final TransactionDto transactionDto,
        final OperationsType type,
        final Transaction.TransactionBuilder builder) {

        if (amountInvalid(transactionDto, type)) {
            throw new InvalidAmountException(type);
        }

        final var amount = type.defineAmount(transactionDto.getAmount());
        builder.amount(amount);
    }

    private boolean amountInvalid(
        final TransactionDto transactionDto,
        final OperationsType type) {
        return transactionDto.isAmountPositive() && type.isNegative() ||
            transactionDto.isAmountNegative() && type.isPositive();
    }

    private Account getAccountEntity(
        final TransactionDto dto) {
        return this.accountGateway.find(dto.getAccountId())
            .orElseThrow(() -> new AccountNotFoundException(dto.getAccountId()));
    }

}
