package com.example;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Jake on 5/10/17.
 */
public interface PurchaseRepo extends CrudRepository<Purchase, Integer> {
    List<Purchase> findByCategory(String category);
//    List<Purchase> findByAlcohol(String Alcohol);
//    List<Purchase> findByToiletries(String Toiletries);
//    List<Purchase> findByShoes(String Shoes);
//    List<Purchase> findByFood(String Food);
//    List<Purchase> findByJewelry(String Jewelry);

}
