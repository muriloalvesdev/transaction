package br.com.transaction.dataprovider.database.repository;

import br.com.transaction.dataprovider.database.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
    Boolean existsByDocumentNumber(String documentNumber);
}
