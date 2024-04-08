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
import java.util.Scanner;
import java.io.Serializable;

public class Book implements Serializable {
    // PROPERTIES
    private Integer ID = 0;
    private Long ISBN = 0000000000000l;
    private String title = "";
    private String author = "";
    private String publisher = "";
    private Float price = 0.0f;
    private Integer qty = 0;

    // CONSTRUCTOR
    public Book(Integer bookID, Scanner scanner, Long ISBN) {
        this.ID = bookID;
        this.ISBN =ISBN;

        // Set Title via input
        boolean validTitle = false;
        while (!validTitle) {
            System.out.println("\nPlease enter book title: ");
            String input = scanner.nextLine();

            if (input != "") {
                this.title = input;
                validTitle = true;
            }

            else {
                System.out.println("\n***ERROR*** Title cannot be NULL.\nPlease try again.\n");
            }
        }

        // Set Author via input
        boolean validAuthor = false;
        while (!validAuthor) {
            System.out.println("\nPlease enter book author: ");
            String input = scanner.nextLine();

            if (input != "") {
                this.author = input;
                validAuthor = true;
            }

            else {
                System.out.println("\n***ERROR*** Author cannot be NULL.\nPlease try again.\n");
            }
        }

        // Set Publisher via input
        boolean validPublisher = false;
        while (!validPublisher) {
            System.out.println("\nPlease enter book publisher (NULL accepted): ");
            String input = scanner.nextLine();
            this.publisher = input;
            validPublisher = true;
        }

        // Set book price
        boolean validPrice = false;
        while (!validPrice) {
            System.out.println("\nPlease enter book price (Enter $0.00 if not applicable): ");
            String input = scanner.nextLine();

            try {
                Float floatInput = Float.parseFloat(input);
                this.price = floatInput;
                validPrice = true;
            }

            catch (Exception exception) {
                System.out.println("\n***ERROR*** Input was not a valid price amount. Please use DIGITS only, " +
                "specifying cents with a decimal. (EXAMPLE: 0.50)\nPlease try again\n");
            }

            this.publisher = input;
            validPublisher = true;
        }
    }

    // GETTERS
    public Integer ID() {
        return this.ID;
    }

    public Long ISBN() {
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
        String report = "\n" +
            "Book ID: " + this.ID.toString() + " | " +
            "ISBN: " + this.ISBN.toString() + " | " +
            "Title: " + this.title + " | " +
            "Author: " + this.author + " | " +
            "Publisher: " + this.publisher + " | " +
            "Price: " + String.format("$%.2f", price);
    
        System.out.println(report);
    }
}
