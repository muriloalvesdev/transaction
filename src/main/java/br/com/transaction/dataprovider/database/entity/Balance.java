package br.com.transaction.dataprovider.database.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(
    name = "balance",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"account_document_number"})})
public class Balance extends BaseEntity {

    @Id
    private String uuid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "account_document_number",
        referencedColumnName = "document_number",
        foreignKey = @ForeignKey(name = "account_document_number"),
        nullable = false)
    private Account account;

    @Setter
    private BigDecimal availableCreditLimit;
}
