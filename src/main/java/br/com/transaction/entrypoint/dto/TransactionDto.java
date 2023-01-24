package br.com.transaction.entrypoint.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionDto {

    private UUID accountId;
    private String operationType;
    private BigDecimal amount;

    public boolean isAmountPositive() {
        return this.amount.intValue() > 0;
    }

    public boolean isAmountNegative() {
        return !isAmountPositive();
    }

}
