package br.com.transaction.core.usecase.operation;

public class UseCaseOperationWithInstallment extends OperationRule {

    public boolean mustBePositive() {
        return false;
    }

    public boolean mustBeNegative() {
        return true;
    }
}
