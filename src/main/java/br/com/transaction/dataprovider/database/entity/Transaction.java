package br.com.transaction.dataprovider.database.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

import static br.com.transaction.dataprovider.database.entity.OperationsType.PAGAMENTO;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected UUID uuid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "account_uuid",
        referencedColumnName = "uuid",
        foreignKey = @ForeignKey(name = "account_uuid"),
        nullable = false)
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private OperationsType type;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

}
