package br.com.transaction.core.usecase.operation;

public class UseCaseOperationWithoutInstallment extends OperationRule {

    public boolean mustBePositive() {
        return false;
    }

    public boolean mustBeNegative() {
        return true;
    }
}
