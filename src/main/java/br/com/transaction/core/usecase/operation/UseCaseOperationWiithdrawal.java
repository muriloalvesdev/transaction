package br.com.transaction.core.usecase.operation;

import java.math.BigDecimal;

public class UseCaseOperationWiithdrawal implements OperationRule {

    public BigDecimal defineAmount(final BigDecimal amount) {
        return amount.negate();
    }

    public boolean mustBePositive() {
        return false;
    }

    public boolean mustBeNegative() {
        return true;
    }

}
