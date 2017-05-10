package com.example;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Jake on 5/10/17.
 */

@Entity
@Table(name = "purchases")
public class Purchase {
    @Id
    @GeneratedValue
    int id;


}
