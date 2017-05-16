package com.example;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Jake on 5/10/17.
 */
public interface PurchaseRepo extends PagingAndSortingRepository<Purchase, Integer> {
//    List<Purchase> findByCategory(String category); // gets all purchases by category.
    Page<Purchase> findByCategory(Pageable pageable, String category); //gets all purchases by page and category
    @Query("SELECT g FROM Customer g WHERE g.name LIKE ?1%")
    List<Purchase> findByNameStartsWith(String name);

}
