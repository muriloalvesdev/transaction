package br.com.transaction.entrypoint.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionDto {

    private String accountId;
    private String operationType;
    private BigDecimal amount;

    public boolean isAmountPositive() {
        return this.amount.intValue() > 0;
    }

    public boolean isAmountNegative() {
        return !isAmountPositive();
    }

}
