package com.example.mywebquizengine.Model;

import javax.persistence.*;

@Entity(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer order_id;

    @ManyToOne
    private User user;

    private String operation_id;

    private Integer coins;

    private String amount;



    public Order() {

    }

    public String getAmount() {
        return amount;
    }

    public Integer getCoins() {
        return coins;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOperation_id() {
        return operation_id;
    }

    public void setOperation_id(String operation_id) {
        this.operation_id = operation_id;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setCoins(Integer coins) {
        this.coins = coins;
    }

}
