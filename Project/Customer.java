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
    private static final long serialVersionUID = 3L;

    private Integer ID = 0;
    private String name = "";
    private String address = "";
    private String email = "";
    private Integer phone = 0;

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
                System.out.println("\n***ERROR*** Name field cannot be NULL.\nPlease try again.\n");
            }
        }

        // Set addr via input
        boolean validAddr = false;
        while (!validAddr) {
            System.out.println("\nPlease enter customer address (NULL accepted): ");
            String input = scanner.nextLine();
            this.address = input;
            validAddr = true;  // While loop exists for future validation implementations, right now no validation runs on addr
        }

        // Set email via input
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

        // Set phone via input
        boolean validPhone = false;
        while (!validPhone) {
            System.out.println("\nPlease enter customer phone # (digits only): ");
            try {
                Integer input = scanner.nextInt();
                scanner.nextLine();
                if (input > 999999999) {
                    this.phone = input;
                    validPhone = true;
                }
                
                
                else {
                    System.out.println("\n***ERROR*** Phone # must be at least 10 digits.\nPlease try again.");
                }
            }

            catch (Exception exception) {
                System.out.println("\n***ERROR*** Phone # must be entered using DIGITS only. (EXAMPLE: 18005551234)\nPlease try again.\n");
            }
        }

        // For debugging
        System.out.println("\n\n************CREATED NEW CUSTOMER**************");
        this.SelfReport();
    }

    // GETTERS
    public Integer ID() {
        return ID;
    }

    public String Name() {
        return name;
    }

    public String Address() {
        return address;
    }

    public String Email() {
        return email;
    }

    public Integer Phone() {
        return phone;
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
            try {
                Integer input = scanner.nextInt();
                scanner.nextLine();
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
    }

    // Reporting
    public void SelfReport() {
        String report = "\n" +
            "Book ID: " + this.ID.toString() + " | " +
            "Name: " + this.name + " | " +
            "Address: " + this.address + " | " +
            "Email: " + this.email + " | " +
            "Phone: " + this.phone.toString() + " | ";

        System.out.println(report);
    }
}
