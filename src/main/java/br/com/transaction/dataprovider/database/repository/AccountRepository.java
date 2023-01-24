package br.com.transaction.dataprovider.database.repository;

import br.com.transaction.dataprovider.database.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    Boolean existsByDocumentNumber(String documentNumber);
}
