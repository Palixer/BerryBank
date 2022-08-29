package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name= "native", strategy = "native")
    private Long id;
    private String cardHolder;
    private String number;
    private int cvv;
    private Date thruDate;
    private Date fromDate;
    private CardType type;
    private CardColor color;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    public Card() {
    }

    public Card(String number, int cvv, CardType type, CardColor color, Client client) {
        this.cardHolder = client.getFirstName() + " " + client.getLastName();
        this.number = number;
        this.cvv = cvv;
        this.thruDate = obtenerFechaDeVencimiento();
        this.fromDate = new Date();
        this.type = type;
        this.color = color;
        this.client=client;
    }

    public Card(CardType type, CardColor color, Client client) {
        this.cardHolder = client.getFirstName() + " " + client.getLastName();
        this.thruDate = obtenerFechaDeVencimiento();
        this.fromDate = new Date();
        this.type = type;
        this.color = color;
        this.client = client;
    }
    public Card(String number, int cvv, CardType type, CardColor color) {
        this.cardHolder = client.getFirstName() + " " + client.getLastName();
        this.number = number;
        this.cvv = cvv;
        this.thruDate = obtenerFechaDeVencimiento();
        this.fromDate = new Date();
        this.type = type;
        this.color = color;

    }

    private Date obtenerFechaDeVencimiento() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, 5);

        return calendar.getTime();
    }


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public Date getThruDate() {
        return thruDate;
    }

    public void setThruDate(Date thruDate) {
        this.thruDate = thruDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }


    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public CardColor getColor() {
        return color;
    }

    public void setColor(CardColor color) {
        color = color;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardholder='" + cardHolder + '\'' +
                ", number='" + number + '\'' +
                ", cvv='" + cvv + '\'' +
                ", thruDate=" + thruDate +
                ", fromDate=" + fromDate +
                ", cardType=" + type +
                ", cardColor=" + color +
                ", client=" + client +
                '}';
    }
}
