package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

//Entity convierte la clase en una tabla de base de datos
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name= "native", strategy = "native")
    private Long id;
    private String number;
    private Date creationDate;
    private Double balance;
    private String cbu;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
      private Client client;


    public Account() {
    }
    public Set<Transaction> getTransactions() {

        return transactions;
    }
    public Account(String number, Double balance, Client client) {
        this.number = number;
        this.creationDate = new Date();
        this.balance = balance;
        this.client= client;
        this.cbu = ("0017" + (int) (Math.random() * (100)) + (int) (Math.random() * (100000000))+ this.number.substring(4)+ (int) (Math.random() * (10)));
    }

    @OneToMany(mappedBy="account", fetch=FetchType.EAGER)
    Set<Transaction> transactions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {

        this.balance = balance;
    }
    @JsonIgnore
      public Client getClient() {
          return client;
      }

      public void setClient(Client client) {

        this.client = client;
      }

    public String getCbu() {
        return cbu;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
    }

    public void addTransaction(Transaction transaction) {
          transaction.setAccount(this);
          transactions.add(transaction);
      }


      @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", creationDate=" + creationDate +
                ", balance=" + balance +
                '}';
    }
}
