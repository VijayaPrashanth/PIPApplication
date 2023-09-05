package com.thoughtworks.sample.cart.repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty
    private int id;

    @Column(name = "name", nullable = false)
    @JsonProperty
    @NotNull
    private String name;

    @Column(name = "quantity", nullable = false)
    @JsonProperty
    @NotNull
    private int quantity;

    @Column(name = "unit", nullable = false)
    @JsonProperty
    @NotNull
    private String unit;

    public Cart() {
    }

    public Cart(String name, Integer quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
