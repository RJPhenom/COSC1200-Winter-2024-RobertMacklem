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
import java.io.Serializable;
import java.time.*;

public class Order implements Serializable {
    // PROPERTIES
    private Integer ID = 0;
    private Integer customerID = 0;
    private LocalDate date;

    // CONSTRUCTOR
    public Order(Integer orderID, Integer customer, LocalDate date) {
        this.ID = orderID;
        this.customerID = customer;
        this.date = date;
    }

    // GETTERS
    public Integer ID() {
        return this.ID;
    }

    public Integer CustomerID() {
        return this.customerID;
    }

    public LocalDate Date() {
        return this.date;
    }

    // No setters since order class is constructed on order finalization.
    // We do not need to update orders, ever. Returns and exchanges can be
    // handled as new orders.

    // Reporting
    public void SelfReport() {
        String report = "\n" +
            "Order ID: " + this.ID.toString() + " | " +
            "Customer ID: " + this.customerID.toString() + " | " +
            "Date: " + this.date.toString();
    
        System.out.println(report);
    }
}
