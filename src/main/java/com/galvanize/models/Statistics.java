package com.galvanize.models;

public class Statistics {
    private int won;
    private int lost;
    private Double balance;

    public Statistics() {
    }

    public Statistics(int won, int lost, Double balance) {
        this.won = won;
        this.lost = lost;
        this.balance = balance;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
