package br.com.transaction.core.usecase.operation;

import java.math.BigDecimal;

public abstract class OperationRule {

    public boolean isOperationInvalid(final BigDecimal amount) {
        return isAmountPositive(amount) && mustBeNegative() ||
            isAmountNegative(amount) && mustBePositive() ||
            isAmountZero(amount);
    }

    private boolean isAmountZero(final BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) == 0;
    }

    private boolean isAmountNegative(final BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) < 0;
    }

    public boolean isAmountPositive(final BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }

    abstract boolean mustBePositive();

    abstract boolean mustBeNegative();

}
