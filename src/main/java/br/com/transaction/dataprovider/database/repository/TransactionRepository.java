package br.com.transaction.dataprovider.database.repository;

import br.com.transaction.dataprovider.database.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

}
