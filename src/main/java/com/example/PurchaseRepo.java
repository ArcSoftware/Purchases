package com.example;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Jake on 5/10/17.
 */
public interface PurchaseRepo extends CrudRepository<Purchase, Integer> {
    List<Purchase> findByCategory(String category);
    @Query("SELECT g FROM Customer g WHERE g.name LIKE ?1%")
    List<Purchase> findByNameStartsWith(String name);


}
