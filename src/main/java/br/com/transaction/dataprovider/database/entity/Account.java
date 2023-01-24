package br.com.transaction.dataprovider.database.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;
import java.util.UUID;


@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "accounts", uniqueConstraints = {@UniqueConstraint(columnNames = {"document_number"})})
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected UUID uuid;

    @Column(name = "document_number", unique = true, nullable = false)
    private String documentNumber;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    public void addTransaction(final Transaction transaction) {
        this.transactions.add(transaction);
    }

}
