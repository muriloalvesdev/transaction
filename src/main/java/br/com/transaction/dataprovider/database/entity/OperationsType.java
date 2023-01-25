package br.com.transaction.dataprovider.database.entity;

import br.com.transaction.core.usecase.operation.OperationRule;
import br.com.transaction.core.usecase.operation.UseCaseOperationPayment;
import br.com.transaction.core.usecase.operation.UseCaseOperationWiithdrawal;
import br.com.transaction.core.usecase.operation.UseCaseOperationWithInstallment;
import br.com.transaction.core.usecase.operation.UseCaseOperationWithoutInstallment;
import br.com.transaction.dataprovider.database.exception.InvalidOperationTypeException;

import java.util.Arrays;

public enum OperationsType {
    COMPRA_A_VISTA("COMPRA A VISTA", new UseCaseOperationWithoutInstallment()),
    COMPRA_PARCELADA("COMPRA PARCELADA", new UseCaseOperationWithInstallment()),
    SAQUE("SAQUE", new UseCaseOperationWiithdrawal()),
    PAGAMENTO("PAGAMENTO", new UseCaseOperationPayment());

    private final String typeName;
    private final OperationRule rule;

    OperationsType(final String typeName, final OperationRule rule) {
        this.typeName = typeName;
        this.rule = rule;
    }

    public static OperationsType fromString(final String typeName) {
        return Arrays.stream(OperationsType.values())
            .filter(operationType -> operationType.getTypeName().equalsIgnoreCase(typeName))
            .findAny()
            .orElseThrow(() -> new InvalidOperationTypeException(typeName));
    }

    public OperationRule getRule() {
        return this.rule;
    }

    public String getTypeName() {
        return this.typeName;
    }
}
