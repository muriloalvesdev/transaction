package br.com.transaction.core.gateway;

import br.com.transaction.dataprovider.database.entity.Account;

import java.util.Optional;

public interface AccountGateway {

    Account save(final Account entity);

    Optional<Account> find(final String accountId);

    Boolean existsByDocumentNumber(String documentNumber);

}
