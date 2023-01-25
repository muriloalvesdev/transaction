package br.com.transaction.core.usecase.operation;

public class UseCaseOperationPayment extends OperationRule {

    public boolean mustBePositive() {
        return true;
    }

    public boolean mustBeNegative() {
        return false;
    }

}
