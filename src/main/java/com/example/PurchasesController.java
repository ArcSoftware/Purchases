package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Jake on 5/10/17.
 */

@Controller
public class PurchasesController {
    @Autowired
    CustomerRepo customers;

    @Autowired
    PurchaseRepo purchases;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session, String category) {
        List<Purchase> purchaseList;
        if (category != null) {
            purchaseList = purchases.findByCategory(category);
        }else{
            purchaseList = (List)purchases.findAll();
        }
        model.addAttribute("purchases", purchaseList);
        return "home";

    }
    @PostConstruct
    public void init(){
        //read in data from CSV file
        Scanner customerScanner = null;
        try {
            customerScanner = new Scanner(new File("src/main/resources/customers.csv"));
            ///Users/Jake/Code/Purchases/src/main/resources/customers.csv
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner purchaseScanner = null;
        try {
            purchaseScanner = new Scanner(new File("src/main/resources/purchases.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        customerScanner.nextLine(); // will skip the first line in the CSV File
        purchaseScanner.nextLine();
        if (customers.findOne(1)== null) {
            while (customerScanner.hasNext()) {
                String line = customerScanner.nextLine();
                int columnLine = 0;
                String[] columns = line.split(",");
                Customer newCustomer = new Customer(columns[columnLine++], columns[columnLine++]);
                customers.save(newCustomer);
            }
            while (purchaseScanner.hasNext()) {
                String line = purchaseScanner.nextLine();
                int columnLine = 0;
                String[] columns = line.split(",");
                Customer customerFinder = customers.findOne(Integer.valueOf(columns[columnLine++]));
                Purchase newPurchase = new Purchase(customerFinder, columns[columnLine++], columns[columnLine++],
                        Integer.valueOf(columns[columnLine++]), columns[columnLine++]);
                purchases.save(newPurchase);
            }
        }


    }
}
