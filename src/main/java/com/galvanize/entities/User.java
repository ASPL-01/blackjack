package com.galvanize.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.galvanize.models.Card;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="users")
@Data
public class User {
    private int id;
    private int version;
    private String username;
    private Double balance;
    private Date created;
    private List<Game> games;

    public User() {
    }

    public User(Double balance, String username) {
        this.balance = balance;
        this.username = username;
    }

    @Id
    @GeneratedValue
    public int getId() { return id; }
    public void setId(int id) {this.id = id;}

    @Version
    public int getVersion() {return version;}
    public void setVersion(int version) {this.version = version;}

    @Column(unique = true, nullable = false)
    @Size(min = 4)
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    @Column(nullable = false)
    @DecimalMin(value = "0")
    public Double getBalance() {
        return balance;
    }
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @CreationTimestamp
    public Date getCreated() {
        return created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnore
    public List<Game> getGames() {
        return games;
    }
    public void setGames(List<Game> games) {
        this.games = games;
    }
}
