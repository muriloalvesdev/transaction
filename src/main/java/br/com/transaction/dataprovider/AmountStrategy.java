package br.com.transaction.dataprovider;

import java.math.BigDecimal;

public interface AmountStrategy {
    BigDecimal convertAmount(final BigDecimal amount);

}
