package com.thoughtworks.sample.cart.repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import com.thoughtworks.sample.inventory.repository.Inventory;

import javax.persistence.*;

@Entity
@Table
public class Cart {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty
    private int id;

    @ManyToOne
    @JoinColumn(name="inventory_id",referencedColumnName = "id")
    private Inventory inventory;

    @Column(name = "name", nullable = false)
    @JsonProperty
    @NotNull
    private String name;

    @Column(name = "itemscount", nullable = false)
    @JsonProperty
    @NotNull
    private int itemsCount;

    @Column(name = "unit", nullable = false)
    @JsonProperty
    @NotNull
    private String unit;

    public Cart() {
    }

    public Cart(Inventory inventory,String name, Integer itemsCount, String unit) {
        this.inventory = inventory;
        this.name = name;
        this.itemsCount = itemsCount;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public int getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(int itemsCount) {
        this.itemsCount = itemsCount;
    }
}
