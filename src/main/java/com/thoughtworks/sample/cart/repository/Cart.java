package com.thoughtworks.sample.cart.repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty
    private int id;

    @Column(name = "name",nullable = false)
    @JsonProperty
    @NotNull
    private String name;

    @Column(name = "quantity",nullable = false)
    @JsonProperty
    @NotNull
    private Integer quantity;

    public Cart() {
    }

    public Cart(String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
