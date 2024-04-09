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

public class Customer implements Serializable {
    // PROPERTIES
    private Integer ID = 0;
    private String name = "";
    private String address = "";
    private String email = "";
    private String phone = "";

    // CONSTRUCTOR
    public Customer(Integer customerID, String name, String address, String email, String phone) {
        this.ID = customerID;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone  = phone;


    }

    // GETTERS
    public Integer ID() {
        return this.ID;
    }

    public String Name() {
        return this.name;
    }

    public String Address() {
        return this.address;
    }

    public String Email() {
        return this.email;
    }

    public String Phone() {
        return this.phone;
    }

    // SETTERS
    // i.e.: Updating customer information
    public void UpdateAddress(Scanner scanner) {
        boolean validAddr = false;
        while (!validAddr) {
            System.out.println("\nPlease enter customer address (NULL accepted): ");
            String input = scanner.nextLine();
            this.address = input;
            validAddr = true;  // While loop exists for future validation implementations, right now no validation runs on addr
        }
    }

    public void UpdateEmail(Scanner scanner) {
        boolean validEmail = false;
        while (!validEmail) {
            System.out.println("\nPlease enter customer email (NULL accepted): ");
            String input = scanner.nextLine();

            if (input == "" || input.contains("@")) {
                this.email = input;
                validEmail = true;
            }

            else {
                System.out.println("\n***ERROR*** Emails must contain a '@'.\nIf you do not have an email, please enter NULL.\n");
            }
        }
    }

    public void UpdatePhone(Scanner scanner) {
        boolean validPhone = false;
        while (!validPhone) {
            System.out.println("\nPlease enter customer phone # (digits only): ");
            this.phone = scanner.nextLine();
            validPhone = true;
        }
    }

    // Reporting
    public void SelfReport() {
        String report =
            "Customer ID: " + this.ID.toString() + " | " +
            "Name: " + this.name + " | " +
            "Address: " + this.address + " | " +
            "Email: " + this.email + " | " +
            "Phone: " + this.phone.toString();

        System.out.println(report);
    }
}
