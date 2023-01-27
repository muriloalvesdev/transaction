package br.com.transaction.dataprovider.database.repository;

import br.com.transaction.dataprovider.database.entity.Account;
import br.com.transaction.dataprovider.database.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BalanceRepository extends JpaRepository<Balance, String> {

    Optional<Balance> findByAccount(Account account);

}
