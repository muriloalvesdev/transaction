package br.com.transaction.core.gateway;

import br.com.transaction.dataprovider.database.entity.Account;
import br.com.transaction.dataprovider.database.entity.Balance;

public interface BalanceGateway {

    Balance save(final Balance balance);

    Balance findByAccount(final Account account);

}
