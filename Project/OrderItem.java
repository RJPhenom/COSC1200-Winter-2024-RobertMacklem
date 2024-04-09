// ***************************************************************************************
//  Title:              Bookstore Management System -- Database Management System (DBMS)
//  Author:             Robert Macklem
//  Date:               8 April 2024
//
//  Description:        The Bookstore Management System (BMS) is the main
//                      management software for the Library. It manages
//                      book, customer, and order records and facilitates
//                      transactions and Bookstore operations.
//
//                      This file contains the struct for the Order Items bridge table.
//
//                      This software was developed for COSC1200-01, Winter
//                      2024 at Durham College.
//                      
// ***************************************************************************************
import java.io.Serializable;

public class OrderItem implements Serializable {
    // Bridge table struct
    public Integer orderID;
    public Integer itemID;
    public Integer qty;

    public OrderItem(Integer oID, Integer iID, Integer qty) {
        this.orderID = oID;
        this.itemID = iID;
        this.qty = qty;
    }

    public String GetSelfReport() {
        String report = "\nQty. " + this.qty.toString() + " | ";
        
        return report;
    }
}
