package com.thoughtworks.sample.cart.repository;

import com.thoughtworks.sample.version.repository.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {

    @Query(value = "SELECT * FROM CART ORDER BY ID",nativeQuery = true)
    List<Cart> getItemDetails();

}
