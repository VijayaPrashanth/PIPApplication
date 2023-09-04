package com.thoughtworks.sample.inventory.repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="inventory")
public class Inventory {

    @Id
    @JsonProperty
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name",nullable = false)
    @JsonProperty
    @NotNull
    private String name;

    @Column(name = "price")
    @JsonProperty
    private BigDecimal price;
    public Inventory() {
    }

    public Inventory(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
