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

    @Column(name = "price",nullable = false)
    @JsonProperty
    private BigDecimal price;

    @Column(name = "unit",nullable = false)
    @JsonProperty
    @NotNull
    private String unit;
    public Inventory() {
    }

    public Inventory(String name, BigDecimal price,String unit) {
        this.name = name;
        this.price = price;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
