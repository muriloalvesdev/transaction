package br.com.transaction.entriypoint.dto;

import br.com.transaction.dataprovider.database.entity.Account;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountDto {

    @NotNull
    private UUID accountId;

    @NotNull
    private String documentNumber;

    public static AccountDto build(Account entity) {
        return new AccountDto(entity.getUuid(), entity.getDocumentNumber());
    }

}