package br.com.transaction.core.usecase.operation;

import java.math.BigDecimal;

public class UseCaseOperationPayment implements OperationRule {

    public BigDecimal defineAmount(final BigDecimal amount) {
        return amount;
    }

    public boolean mustBePositive() {
        return true;
    }

    public boolean mustBeNegative() {
        return false;
    }

}
