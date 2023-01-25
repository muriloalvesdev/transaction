package br.com.transaction.core.usecase.operation;

public class UseCaseOperationWiithdrawal extends OperationRule {

    public boolean mustBePositive() {
        return false;
    }

    public boolean mustBeNegative() {
        return true;
    }

}
