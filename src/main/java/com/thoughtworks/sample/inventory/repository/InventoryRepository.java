package com.thoughtworks.sample.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Integer> {

    @Query(value = "SELECT * FROM INVENTORY ORDER BY ID",nativeQuery = true)
    List<Inventory> getItems();
}
