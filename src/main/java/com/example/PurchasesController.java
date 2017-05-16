package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
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
    public String home(Model model, HttpSession session, String category, String search, Integer page) {
        page = (page == null) ? 0 : page;
        // <expression> ? <thing to return if true> : <thing to return if false>
        //setting page to 0 if null.

        PageRequest pr = new PageRequest(page, 10);
        Page<Purchase> p;


        Page<Purchase> purchaseList; //storing results into Page object
        if (category != null) { //get the page of results from the category
            purchaseList = purchases.findByCategory(pr, category);
        }else{
            purchaseList = purchases.findAll(pr);
        }
        model.addAttribute("purchases", purchaseList);
        model.addAttribute("nextPage", page+1); //adding link to next page
        model.addAttribute("showNext", purchaseList.hasNext()); //decide wether or not we need a next page link
        model.addAttribute("previousPage", page-1);
        model.addAttribute("showBack", page > 0);
        model.addAttribute("category", category); //add the category we are in to model.
        //we need to know the category if it exists if we want to render the appropriate "next" link.
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
