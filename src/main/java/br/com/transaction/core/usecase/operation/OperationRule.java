package br.com.transaction.core.usecase.operation;

import java.math.BigDecimal;

public interface OperationRule {

    BigDecimal defineAmount(BigDecimal amount);

    default boolean isAmountZero(final BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) == 0;
    }

    default boolean isAmountNegative(final BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) < 0;
    }

    default boolean isAmountPositive(final BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }

}
