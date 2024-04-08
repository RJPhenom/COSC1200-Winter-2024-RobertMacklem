// ***************************************************************************************
//  Title:              Bookstore Management System -- Customer
//  Author:             Robert Macklem
//  Date:               29 March 2024
//
//  Description:        The Bookstore Management System (BMS) is the main
//                      management software for the Library. It manages
//                      book, customer, and order records and facilitates
//                      transactions and Bookstore operations.
//
//                      This file contains source code for Customer records.
//
//                      This software was developed for COSC1200-01, Winter
//                      2024 at Durham College.
//                      
// ***************************************************************************************
import java.io.Serializable;
import java.util.Scanner;
import java.util.ArrayList;

public class Customer implements Serializable {
    // PROPERTIES
    private Integer ID = 0;
    private String name = "";
    private String address = "";
    private String email = "";
    private Integer phone = 0;
    private ArrayList<Order> orders;

    // CONSTRUCTOR
    public Customer(Integer customerID, Scanner scanner) {
        this.ID = customerID;

        // Set name via input
        boolean validName = false;
        while (!validName) {
            System.out.println("\nPlease enter customer name: ");
            String input = scanner.nextLine();

            if (input != "") {
                this.name = input;
                validName = true;
            }

            else {
                System.out.println("\n***ERROR*** Name field cannot be NULL. Please re-enter name.\n");
            }
        }

        // Set addr via input
        boolean validAddr = false;
        while (!validAddr) {
            System.out.println("\nPlease enter customer address (null accepted): ");
            String input = scanner.nextLine();
            this.address = input;
            validAddr = true;  // While loop exists for future validation implementations, right now no validation runs on addr
        }

        // Set email via input
        boolean validEmail = false;
        while (!validEmail) {
            System.out.println("\nPlease enter customer email (null accepted): ");
            String input = scanner.nextLine();

            if (input == "" || input.contains("@")) {
                this.email = input;
                validEmail = true;
            }

            else {
                System.out.println("\n***ERROR*** Emails must contain a '@'.\nIf you do not have an email, please enter NULL.\n");
            }
        }

        // Set phone via input
        boolean validPhone = false;
        while (!validPhone) {
            System.out.println("\nPlease enter customer phone # (digits only): ");
            try {
                Integer input = scanner.nextInt();
                if (input > 999999999) {
                    this.phone = input;
                }
                
                
                else {
                    System.out.println("\n***ERROR*** Phone # must be at least 10 digits.\nPlease try again.]n");
                }
            }

            catch (Exception exception) {
                System.out.println("\n***ERROR*** Phone # must be entered using DIGITS only. (EXAMPLE: 18005551234)\nPlease try again.\n");
            }
        }

        // Orders inits as empty
        this.orders = new ArrayList<Order>();

        // For debugging
        System.out.println("\n\n************CREATED NEW CUSTOMER**************");
        this.SelfReport();
    }

    // GETTERS
    public Integer ID() {
        return ID;
    }

    public String name() {
        return name;
    }

    public String address() {
        return address;
    }

    public String email() {
        return email;
    }

    public Integer phone() {
        return phone;
    }

    // SETTERS
    // i.e.: Updating customer information
    public void updateAddress(String newAddress) {
        this.address = newAddress;
    }

    public void updateEmail(String newEmail) {
        this.email = newEmail;
    }

    public void updatePhone(Integer newPhone) {
        this.phone = newPhone;
    }

    // ORDERS HANDLING
    // Adding one or multiple orders
    public void AddOrder(Order order) {
        orders.add(order);
    }

    public void AddOrders(ArrayList<Order> orders) {
        for (Order order : orders) {
            AddOrder(order);
        }
    }

    // Retrieves all orders this customer has made (for reporitng)
    public ArrayList<Order> retrieveOrders() {
        ArrayList<Order> retrievedOrders = new ArrayList<Order>();

        for (Order order : orders) {
            retrievedOrders.add(order);
        }

        return retrievedOrders;
    }

    // These two method retrieve a single order, either by index in the list or
    // by the orderID (likely the preferable method).
    public Order retrieveOrderByIndex(int index) {
        return orders.get(index);
    }

    public Order retrieveOrderByID(Integer orderID) {
        for (Order order : orders) {
            if (order.ID() == orderID) {
                return order;
            }
        }

        return null;
    }

    // Reporting
    public void SelfReport() {
        String report = "\n" +
            "Book ID: " + this.ID.toString() + " | " +
            "Name: " + this.name + " | " +
            "Address: " + this.address + " | " +
            "Email: " + this.email + " | " +
            "Phone: " + this.phone.toString() + " | " +
            "Order(s): " + this.orders.toString();
    
        System.out.println(report);
    }
}
