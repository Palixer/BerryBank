package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name= "native", strategy = "native")
    private Long id;
    private TransactionType type;
    private Double amount;
    private String description;
    private Date creationDate;

    private String ctaOrigen;
    private String ctaDestino;

    private String fromCbu;
    private String toCbu;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;

    public Transaction() {
    }

    public Transaction(TransactionType type, Double amount, String description, String ctaOrigen, String ctaDestino) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.creationDate = new Date();
        this.ctaOrigen = ctaOrigen;
        this.ctaDestino= ctaDestino;

;    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        creationDate = creationDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getFromCbu() {
        return fromCbu;
    }

    public void setFromCbu(String fromCbu) {
        this.fromCbu = fromCbu;
    }

    public String getToCbu() {
        return toCbu;
    }

    public void setToCbu(String toCbu) {
        this.toCbu = toCbu;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type=" + type +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                ", account=" + account +
                '}';
    }
}
