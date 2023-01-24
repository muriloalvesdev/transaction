package br.com.transaction.entrypoint.dto;

import br.com.transaction.dataprovider.database.entity.Account;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountDto {

    @NotNull
    private String accountId;

    @NotNull
    private String documentNumber;

    public static AccountDto build(final Account entity) {
        return new AccountDto(entity.getUuid(), entity.getDocumentNumber());
    }

}