package br.com.transaction.dataprovider.database.entity;

import br.com.transaction.dataprovider.database.exception.InvalidOperationTypeException;

import java.math.BigDecimal;
import java.util.Arrays;

public enum OperationsType implements DefineAmount {
    COMPRA_A_VISTA("COMPRA A VISTA", "NEGATIVE") {
        public BigDecimal defineAmount(final BigDecimal amount) {
            return amount.negate();
        }
    },
    COMPRA_PARCELADA("COMPRA PARCELADA", "NEGATIVE") {
        public BigDecimal defineAmount(final BigDecimal amount) {
            return amount.negate();
        }
    },
    SAQUE("SAQUE", "NEGATIVE") {
        public BigDecimal defineAmount(final BigDecimal amount) {
            return amount.negate();
        }
    },
    PAGAMENTO("PAGAMENTO", "POSITIVE") {
        public BigDecimal defineAmount(final BigDecimal amount) {
            return amount;
        }
    };

    private String typeName;
    private String signal;

    OperationsType(final String typeName, final String signal) {
        this.typeName = typeName;
        this.signal = signal;
    }

    public static OperationsType fromString(final String typeName) {
        return Arrays.stream(OperationsType.values())
            .filter(operationType -> operationType.getTypeName().equalsIgnoreCase(typeName))
            .findAny()
            .orElseThrow(() -> new InvalidOperationTypeException(typeName));
    }

    public String getTypeName() {
        return this.typeName;
    }

    public String getSignal() {
        return this.signal;
    }

    public boolean isPositive() {
        return this.getSignal().equalsIgnoreCase("POSITIVE");
    }

    public boolean isNegative() {
        return !isPositive();
    }
}
