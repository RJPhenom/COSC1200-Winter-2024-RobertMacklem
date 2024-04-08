// ***************************************************************************************
//  Title:              Bookstore Management System -- Order
//  Author:             Robert Macklem
//  Date:               29 March 2024
//
//  Description:        The Bookstore Management System (BMS) is the main
//                      management software for the Library. It manages
//                      book, customer, and order records and facilitates
//                      transactions and Bookstore operations.
//
//                      This file contains source code for Order records.
//
//                      This software was developed for COSC1200-01, Winter
//                      2024 at Durham College.
//                      
// ***************************************************************************************
import java.util.ArrayList;
import java.io.Serializable;
import java.time.*;

public class Order implements Serializable {
    // PROPERTIES
    private Integer ID = 0;
    private Integer customerID = 0;
    private ArrayList<Integer> items;
    private ArrayList<Integer> qtys;
    private LocalDate date;

    // CONSTRUCTOR
    public Order(Integer orderID, Integer customer, ArrayList<Integer> orderItems, ArrayList<Integer> orderQtys) {
        this.customerID = customer;
        this.items = orderItems;
        this.qtys = orderQtys;
        this.date = LocalDate.now(); // Date should be when the order is made, and never change
    }

    // GETTERS
    public Integer ID() {
        return ID;
    }

    public Integer CustomerID() {
        return customerID;
    }

    public ArrayList<Integer> Items() {
        return items;
    }

    public ArrayList<Integer> Quantities() {
        return qtys;
    }

    public String Date() {
        return date.toString();
    }

    // No setters since order class is constructed on order finalization.
    // We do not need to update orders, ever. Returns and exchanges can be
    // handled as new orders.

    // Reporting
    public void SelfReport() {
        String report = "\n" +
            "Order ID: " + this.ID.toString() + " | " +
            "Customer ID: " + this.customerID.toString() + " | " +
            "Item(s): " + this.items.toString() + " | " +
            "Date: " + this.date.toString();
    
        System.out.println(report);
    }
}
