package br.com.transaction;

import br.com.transaction.dataprovider.database.entity.OperationsType;

import java.math.BigDecimal;
import java.util.UUID;

public interface ConstantsTests {
    String DOCUMENT_NUMBER = "12345678900";
    String ACCOUNT_ID = UUID.randomUUID().toString();
    OperationsType PAGAMENTO = OperationsType.PAGAMENTO;
    OperationsType COMPRA_A_VISTA = OperationsType.COMPRA_A_VISTA;
    OperationsType COMPRA_PARCELADA = OperationsType.COMPRA_PARCELADA;
    OperationsType SAQUE = OperationsType.SAQUE;
    BigDecimal AMOUNT_POSITIVE = BigDecimal.valueOf(100.00);
    BigDecimal AMOUNT_NEGATIVE = AMOUNT_POSITIVE.negate();
}
