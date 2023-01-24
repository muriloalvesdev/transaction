package br.com.transaction.dataprovider.database.repository;

import br.com.transaction.dataprovider.database.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

}
