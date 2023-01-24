package br.com.transaction.dataprovider.database.entity;

import java.math.BigDecimal;

public sealed interface DefineAmount permits OperationsType {
    BigDecimal defineAmount(final BigDecimal amount);
}
