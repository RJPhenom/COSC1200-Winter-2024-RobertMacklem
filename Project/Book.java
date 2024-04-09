// ***************************************************************************************
//  Title:              Bookstore Management System -- Book
//  Author:             Robert Macklem
//  Date:               29 March 2024
//
//  Description:        The Bookstore Management System (BMS) is the main
//                      management software for the Library. It manages
//                      book, customer, and order records and facilitates
//                      transactions and Bookstore operations.
//
//                      This file contains source code for Book records.
//
//                      This software was developed for COSC1200-01, Winter
//                      2024 at Durham College.
//                      
// ***************************************************************************************
import java.io.Serializable;

public class Book implements Serializable {
    // PROPERTIES
    private Integer ID = 0;
    private String ISBN = "";
    private String title = "";
    private String author = "";
    private String publisher = "";
    private Float price = 0.0f;
    private Integer qty = 0;

    // CONSTRUCTOR
    public Book(Integer bookID, String ISBN, String title, String author, String publisher, Float price, Integer qty) {
        this.ID = bookID;
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
        this.qty = qty;
    }

    // GETTERS
    public Integer ID() {
        return this.ID;
    }

    public String ISBN() {
        return this.ISBN;
    }

    public String Title() {
        return this.title;
    }

    public String Author() {
        return this.author;
    }

    public String Publisher() {
        return this.publisher;
    }

    public Float Price() {
        return this.price;
    }

    public Integer Qty() {
        return this.qty;
    }

    // SETTERS (price only)
    public void SetPrice(Float price) {
        this.price = price;
    }

    public void UpdateQty(Integer amount) {
        this.qty += amount;
    }

    // Reporting
    public void SelfReport() {
        String report =
            "Book ID: " + this.ID.toString() + " | " +
            "ISBN: " + this.ISBN + " | " +
            "Title: " + this.title + " | " +
            "Author: " + this.author + " | " +
            "Publisher: " + this.publisher + " | " +
            "Price: " + String.format("$%.2f", price);
    
        System.out.println(report);
    }
}
