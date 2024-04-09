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
//                      This file contains source code for the custom Database 
//                      Management System (DBMS) that handles files and read/write on
//                      Book, Customer, and Order records.
//
//                      This software was developed for COSC1200-01, Winter
//                      2024 at Durham College.
//                      
// ***************************************************************************************
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DBMS {
    // PROPERTIES
    private ArrayList<File> db = new ArrayList<File>();

    // Tables
    private File ordersTable = new File("Orders.txt");
    private File customersTable = new File("Customers.txt");
    private File booksTable = new File("Books.txt");
    private File orderItemsTable = new File("OrderItems.txt");

    // Constructor (handles file init)
    public DBMS() {
        db.add(ordersTable);
        db.add(customersTable);
        db.add(booksTable);
        db.add(orderItemsTable);

        for (File table : db) {
            if (table.exists()) {
                //System.out.println("File found!");
            }

            else {
                try {
                    table.createNewFile();
                    System.out.println("File created: " + table.getName());
                } 
                
                catch (Exception exception) {
                    System.out.println("An error occurred while creating the file.");
                }
            }
        }
    }

    // Getters
    public File Books(){
        return this.booksTable;
    }
    public File Orders(){
        return this.ordersTable;
    }
    public File Customers(){
        return this.customersTable;
    }
    public File OrderItems(){
        return this.orderItemsTable;
    }


    // Writers
    private void WriteOrders(ArrayList<Order> orders) {
        try (ObjectOutputStream orderOOS = new ObjectOutputStream(new FileOutputStream(ordersTable))) {
            for (Order order : orders) {
                orderOOS.writeObject(order);
            }

            //System.out.println("\nOrder written successfully.");
        }

        catch (Exception exception) {
            //exception.printStackTrace();
        }
    }
    private void WriteCustomers(ArrayList<Customer> customers) {
        try (ObjectOutputStream customerOOS = new ObjectOutputStream(new FileOutputStream(customersTable))) {
            for (Customer customer : customers) {
                customerOOS.writeObject(customer);
            }

            //System.out.println("\nCustomers written successfully.");
        }

        catch (Exception exception) {
            //exception.printStackTrace();
        }
    }
    private void WriteBooks(ArrayList<Book> books) {
        try (ObjectOutputStream bookOOS = new ObjectOutputStream(new FileOutputStream(booksTable))) {
            for (Book book : books) {
                bookOOS.writeObject(book);
            }

            //System.out.println("\nBooks written successfully.");
        }

        catch (Exception exception) {
            //exception.printStackTrace();
        }
    }
    private void WriteOrderItems(ArrayList<OrderItem> items) {
        try (ObjectOutputStream oiOOS = new ObjectOutputStream(new FileOutputStream(orderItemsTable))) {
            for (OrderItem item : items) {
                oiOOS.writeObject(item);
            }

            //System.out.println("\nOrder items written successfully.");
        }

        catch (Exception exception) {
            //exception.printStackTrace();
        }
    }

    // Returns the specified table's index in the array, returns true if duplicate found
    public int IdentifyTable(File table) {
        for (int i =0; i < db.size(); i++) {
            if (table == db.get(i)) {
                return i;
            }
        }

        System.out.println("\nWARNING: Table not found. [IdentifyTable FAILED]");
        return -1;
    }
    public boolean IdentifyDuplicate(File table, Integer ID) {
        boolean duplicate = false;

        int tableIndex = IdentifyTable(table);
        switch (tableIndex) {
            case 0:
                ArrayList<Order> orders = SelectOrders();
                for (Order order : orders) {
                    if (order.ID() == ID) {
                        duplicate = true;
                        break;
                    }
                }

                break;

            case 1:
                ArrayList<Customer>  customers = SelectCustomers();
                for (Customer customer : customers) {
                    if (customer.ID() == ID) {
                        duplicate = true;
                        break;
                    }
                }

                break;

            case 2:
                ArrayList<Book> books = SelectBooks();
                for (Book book : books) {
                    if (book.ID() == ID) {
                        duplicate = true;
                        break;
                    }
                }

                break;
        
            default:
                System.out.println("\nWARNING: Table not found! [CheckDuplicate FAILED]");
                break;
        }

        return duplicate;
    }
    public Integer GenerateUniqueID(File table) {
        Integer nullID = -1;
        int highestID = 0;

        int tableIndex = IdentifyTable(table);

        switch (tableIndex) {
            case 0:
                ArrayList<Order> orders = SelectOrders();
                for (Order order : orders) {
                    if (order.ID() >= highestID) {
                        highestID = order.ID() + 1;
                    }
                }

                return highestID;

            case 1:
                ArrayList<Customer> customers = SelectCustomers();
                for (Customer customer : customers) {
                    if (customer.ID() >= highestID) {
                        highestID = customer.ID() + 1;
                    }
                }

                return highestID;

            case 2:
                ArrayList<Book> books = SelectBooks();
                for (Book book : books) {
                    if (book.ID() >= highestID) {
                        highestID = book.ID() + 1;
                    }
                }

                return highestID;

            default:
                break;
        }

        System.out.println("\nWARNING: ID failed to generate. (returned ID code -1)");
        return nullID;
    }

    // INSERT
    public boolean InsertOrder(Order inOrder) {
        ArrayList<Order> orders = SelectOrders();

        boolean duplicate = false;
        for (Order order : orders) {
            if (order.ID() == inOrder.ID()) {
                duplicate = true;

                System.out.println("\nWARNING: Duplicate insertion rejected. [InsertOrder FAILED]");

                break;
            }
        }

        if (!duplicate) {
            orders.add(inOrder);
            WriteOrders(orders);
        }

        return !duplicate;  // Returns true if insertion is successful
    }
    public boolean InsertCustomer(Customer inCustomer) {
        ArrayList<Customer> customers = SelectCustomers();

        boolean duplicate = false;
        for (Customer customer : customers) {
            if (customer.ID() == inCustomer.ID()) {
                duplicate = true;
                System.out.println("\nWARNING: Duplicate insertion rejected. [InsertOrderItem FAILED]");
            }
        }

        if (!duplicate) {
            customers.add(inCustomer);
            WriteCustomers(customers);
        }
        
        return !duplicate;  // Returns true if insertion is successful
    }
    public boolean InsertBook(Book inBook) {
        ArrayList<Book> books = SelectBooks();

        boolean duplicate = false;
        for (Book book : books) {
            if (book.ID() == inBook.ID()) {
                duplicate = true;
                System.out.println("\nWARNING: Duplicate insertion rejected. [InsertOrderItem FAILED]");
            }
        }

        if (!duplicate) {
            books.add(inBook);
            WriteBooks(books);
        }

        return !duplicate;  // Returns true if insertion is successful
    }
    public boolean InsertOrderItem(OrderItem inOrderItem) {
        ArrayList<OrderItem> items = SelectOrderItems();

        boolean duplicate = false;
        for (OrderItem orderItem : items) {
            if (orderItem.orderID == inOrderItem.orderID && orderItem.itemID == inOrderItem.itemID) {
                duplicate = true;
                System.out.println("\nWARNING: Duplicate insertion rejected. [InsertOrderItem FAILED]");
            }
        }

        if (!duplicate) {
            ArrayList<Book> books = SelectBooks();
            for (Book book : books) {
                // Subtract books from stock
                if (book.ID() == inOrderItem.itemID) {
                    UpdateBookByID(book.ID(), inOrderItem.qty * -1);
                }
            }

            items.add(inOrderItem);
            WriteOrderItems(items);
        }

        return !duplicate;  // Returns true if insertion is successful
    }

    // SELECT
    public ArrayList<Order> SelectOrders() {
        ArrayList<Order> orders = new ArrayList<Order>();

        try (ObjectInputStream orderOIS = new ObjectInputStream(new FileInputStream(ordersTable))) {
            while (true) {
                Order order = (Order) orderOIS.readObject();
                orders.add(order);
            }
        }

        catch (Exception exception) {
            //exception.printStackTrace();
        }

        return orders;
    }
    public Order SelectOrderByID(Integer ID) {
        Order identifiedOrder = null;

        try (ObjectInputStream orderOIS = new ObjectInputStream(new FileInputStream(ordersTable))) {
            while (true) {
                Order order = (Order) orderOIS.readObject();
                if (order.ID().equals(ID)) {
                    identifiedOrder = order;
                    //System.out.println("\nCustomer found!");

                    break;
                }
            }
        }

        catch (Exception exception) {
            System.out.println("\nWARNING: Order not found! [SelectOrderByID FAILED]");
            //exception.printStackTrace();
        }

        return identifiedOrder;
    }

    public ArrayList<Customer> SelectCustomers() {
        ArrayList<Customer> customers = new ArrayList<Customer>();

        try (ObjectInputStream customerOIS = new ObjectInputStream(new FileInputStream((customersTable)))) {
            while (true) {
                Customer customer = (Customer) customerOIS.readObject();
                customers.add(customer);
            }
        }

        catch (Exception exception) {
            //exception.printStackTrace();
        }

        return customers;
    }
    public Customer SelectCustomerByID(Integer ID) {
        Customer identifiedCustomer = null;

        try (ObjectInputStream customerOIS = new ObjectInputStream(new FileInputStream(customersTable))) {
            while (true) {
                Customer customer = (Customer) customerOIS.readObject();
                if (customer.ID().equals(ID)) {
                    identifiedCustomer = customer;
                    //System.out.println("\nCustomer found!");

                    return identifiedCustomer;
                }
            }
        }

        catch (Exception exception) {
            System.out.println("\nWARNING: Customer not found! [SelectCustomerByName FAILED]");
            //exception.printStackTrace();
        }
        
        return identifiedCustomer;
    }
    public Customer SelectCustomerByName(String name) {
        Customer identifiedCustomer = null;

        try (ObjectInputStream customerOIS = new ObjectInputStream(new FileInputStream(customersTable))) {
            while (true) {
                Customer customer = (Customer) customerOIS.readObject();
                if (customer.Name().equalsIgnoreCase(name)) {
                    identifiedCustomer = customer;
                    //System.out.println("\nCustomer found!");

                    return identifiedCustomer;
                }
            }
        }

        catch (Exception exception) {
            System.out.println("\nWARNING: Customer not found! [SelectCustomerByName FAILED]");
            //exception.printStackTrace();
        }
        
        return identifiedCustomer;
    }

    public ArrayList<Book> SelectBooks() {
        ArrayList<Book> books = new ArrayList<Book>();

        try (ObjectInputStream booksOIS = new ObjectInputStream(new FileInputStream(booksTable))) {
            while (true) {
                Book book = (Book) booksOIS.readObject();
                books.add(book);
            }
        }

        catch (Exception exception) {
            //exception.printStackTrace();
        }

        return books;
    }
    public Book SelectBookByID(Integer ID) {
        Book identifiedBook = null;

        try (ObjectInputStream bookOIS = new ObjectInputStream(new FileInputStream(booksTable))) {
            while (true) {
                Book book = (Book) bookOIS.readObject();
                if (book.ID().equals(ID)) {
                    identifiedBook = book;
                    //System.out.println("\nBook found!");

                    break;
                }
            }
        }

        catch (Exception exception) {
            System.out.println("\nWARNING: Book not found! [SelectBookByID FAILED]");
            //exception.printStackTrace();
        }

        
        return identifiedBook;
    }
    public Book SelectBookByISBN(String ISBN) {
        Book identifiedBook = null;

        try (ObjectInputStream bookOIS = new ObjectInputStream(new FileInputStream(booksTable))) {
            while (true) {
                Book book = (Book) bookOIS.readObject();
                if (book.ISBN().equalsIgnoreCase(ISBN)) {
                    identifiedBook = book;
                    //System.out.println("\nBook found!");
                    
                    break;
                }
            }
        }

        catch (Exception exception) {
            System.out.println("\nWARNING: Book not found! [SelectBookByISBN FAILED]");
            //exception.printStackTrace();
        }

        
        return identifiedBook;
    }

    private ArrayList<OrderItem> SelectOrderItems() {
        ArrayList<OrderItem> items = new ArrayList<OrderItem>();

        try (ObjectInputStream oiOIS = new ObjectInputStream(new FileInputStream(orderItemsTable))) {
            while (true) {
                OrderItem item = (OrderItem) oiOIS.readObject();
                items.add(item);
            }
        }

        catch (Exception exception) {
            //exception.printStackTrace();
        }
        
        return items;
    }
    public ArrayList<OrderItem> SelectOrderItemsByOrderID(Integer ID) {
        ArrayList<OrderItem> items = new ArrayList<OrderItem>();

        try (ObjectInputStream orderItemsOIS = new ObjectInputStream(new FileInputStream(orderItemsTable))) {
            while (true) {
                OrderItem item = (OrderItem) orderItemsOIS.readObject();
                if (item.orderID.equals(ID)) {
                    items.add(item);
                }
            }
        }

        catch (Exception exception) {
            //exception.printStackTrace();
        }

        return items;
    }
    public ArrayList<OrderItem> SelectOrderItemsByItemID(Integer ID) {
        ArrayList<OrderItem> items = new ArrayList<OrderItem>();

        try (ObjectInputStream orderItemsOIS = new ObjectInputStream(new FileInputStream(orderItemsTable))) {
            while (true) {
                OrderItem item = (OrderItem) orderItemsOIS.readObject();
                if (item.itemID.equals(ID)) {
                    items.add(item);
                }
            }
        }

        catch (Exception exception) {
            //exception.printStackTrace();
        }

        return items;
    }

    // UPDATE
    public void UpdateCustomerByID(Integer ID, Scanner scanner) {
        ArrayList<Customer> customers = SelectCustomers();

        boolean updated = false;
        for (Customer customer : customers) {
            if(customer.ID() == ID) {
                customer.UpdateAddress(scanner);
                customer.UpdateEmail(scanner);
                customer.UpdatePhone(scanner);
                updated = true;

                break;
            }
        }

        if (!updated) {
            System.out.println("\nWARNING: Customer not found! [UpdateCustomerByID FAILED]");

        }

        else {
            WriteCustomers(customers);
        }
    }
    public void UpdateCustomerByName(String name, Scanner scanner) {
        ArrayList<Customer> customers = SelectCustomers();

        boolean updated = false;
        for (Customer customer : customers) {
            if(customer.Name().equalsIgnoreCase(name)) {
                customer.UpdateAddress(scanner);
                customer.UpdateEmail(scanner);
                customer.UpdatePhone(scanner);
                updated = true;

                break;
            }
        }

        if (!updated) {
            System.out.println("\nWARNING: Customer not found! [UpdateCustomerByName FAILED]");

        }

        else {
            WriteCustomers(customers);
        }
    }
    public void UpdateBookByID(Integer ID, Integer amount) {
        ArrayList<Book> books = SelectBooks();

        boolean updated = false;
        for (Book book : books) {
            if(book.ID().equals(ID)) {
                book.UpdateQty(amount);
                updated = true;

                break;
            }
        }

        if (!updated) {
            System.out.println("\nWARNING: Book not found! [UpdateBookByID FAILED]");
        }

        else {
            WriteBooks(books);
        }
    }

    // DELETE
    public void RemoveOrder(Integer ID) {
        ArrayList<Order> orders = SelectOrders();

        boolean removed = false;
        for (Order order : orders) {
            if(order.ID().equals(ID)) {
                orders.remove(order);
                removed = true;

                break;
            }
        }

        if (!removed) {
            System.out.println("\nWARNING: Order not found! [RemoveOrder FAILED]");
        }

        else{
            WriteOrders(orders);
        }
    }
    public void RemoverOrderItemByOrderID(Integer ID) {
        ArrayList<OrderItem> orderItems = SelectOrderItems();

        for (OrderItem orderItem : orderItems) {
            if (orderItem.orderID.equals(ID)) {
                UpdateBookByID(orderItem.itemID, orderItem.qty);  // Reverse book qty subtraction
                orderItems.remove(orderItem);
            }
        }

        WriteOrderItems(orderItems);
    }
    
    // Note: Customers don't ever need to be deleted ( we can add this functionality if we really need it, 
    // like under data privacy rights etc... if this worl the real world). We also don't need to delete book
    // records, since books, once registered, can just drop to 0 qty.
}