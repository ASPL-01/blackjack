package com.galvanize.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.galvanize.enums.Outcome;
import com.galvanize.models.Card;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="games")
public class Game {
//    private List<Card> cards;
    private int id;
    private int version;
    private Date created;
    private Date modified;
    private int bet;
    private User user;
    private Outcome outcome;
    private String playerCards;
    private String dealerCards;
    private Integer playerValue;
    private Integer dealerValue;

    public Game() {
    }

    public Game(int bet, User user) {
        this.bet = bet;
        this.user = user;
    }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Version
    public int getVersion() {
        return version;
    }
    public void setVersion(int version) {
        this.version = version;
    }

    @Min(value = 1)
    public int getBet() {
        return bet;
    }
    public void setBet(int bet) {
        this.bet = bet;
    }

    public String getPlayerCards() {
        return playerCards;
    }
    public void setPlayerCards(String playerCards) {
        this.playerCards = playerCards;
    }

    public String getDealerCards() {
        return dealerCards;
    }
    public void setDealerCards(String dealerCards) {
        this.dealerCards = dealerCards;
    }

    public Integer getPlayerValue() {
        return playerValue;
    }
    public void setPlayerValue(Integer playerValue) {
        this.playerValue = playerValue;
    }

    public Integer getDealerValue() {
        return dealerValue;
    }
    public void setDealerValue(Integer dealerValue) {
        this.dealerValue = dealerValue;
    }

    @Column(columnDefinition = "ENUM('WON', 'LOST')")
    @Enumerated(EnumType.STRING)
    public Outcome getOutcome() {
        return outcome;
    }
    public void setOutcome(Outcome outcome) {
        this.outcome = outcome;
    }

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonIgnore
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @CreationTimestamp
    public Date getCreated() {
        return created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }

    @UpdateTimestamp
    public Date getModified() {
        return modified;
    }
    public void setModified(Date modified) {
        this.modified = modified;
    }
}
