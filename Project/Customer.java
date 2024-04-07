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

public class Customer {
    // PROPERTIES
    int ID = 0;
    String name = "";
    String address = "";
    String email = "";
    int phone = 0;
    int orders[];

    // GETTERS
    public int ID() {
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

    public int phone() {
        return phone;
    }

    public int[] orders() {
        return orders;
    }

    // SETTERS
    public void updateAddress(String newAddress) {
        this.address = newAddress;
    }

    public void updateEmail(String newEmail) {
        this.email = newEmail;
    }

    public void updatePhone(int newPhone) {
        this.phone = newPhone;
    }
}
