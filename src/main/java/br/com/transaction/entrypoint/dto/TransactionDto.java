package br.com.transaction.entrypoint.dto;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionDto {

    @NotNull
    private String accountId;

    @NotNull
    private String operationType;

    @NotNull
    private BigDecimal amount;

}
