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
    private File ordersTable = new File("Orders");
    private File customersTable = new File("Customers");
    private File booksTable = new File("Books");
    private File orderItemsTable = new File("OrderItems");

    // Data
    ArrayList<Order> orders = new ArrayList<Order>();
    ArrayList<Customer> customers = new ArrayList<Customer>();
    ArrayList<Book> books = new ArrayList<Book>();
    ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();

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
                
                catch (IOException exception) {
                    System.out.println("An error occurred while creating the file.");
                }
            }
        }

        // Initialize our db by reading files
        Init();
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

    // INIT 
    private void Init() {
        // Init order data
        try (ObjectInputStream orderOIS = new ObjectInputStream(new FileInputStream(ordersTable))) {
            while (true) {
                Order order = (Order) orderOIS.readObject();
                orders.add(order);
            }
        }

        catch (Exception exception) {
            exception.printStackTrace();
        }

        // Init customer data
        try (ObjectInputStream customerOIS = new ObjectInputStream(new FileInputStream((customersTable)))) {
            while (true) {
                Customer customer = (Customer) customerOIS.readObject();
                customers.add(customer);
            }
        }

        catch (Exception exception) {
            exception.printStackTrace();
        }

        // Init book data
        try (ObjectInputStream booksOIS = new ObjectInputStream(new FileInputStream(booksTable))) {
            while (true) {
                Book book = (Book) booksOIS.readObject();
                books.add(book);
            }
        }

        catch (Exception exception) {
            exception.printStackTrace();
        }

        // Init item data
        try (ObjectInputStream orderItemsOIS = new ObjectInputStream(new FileInputStream(orderItemsTable))) {
            while (true) {
                OrderItem item = (OrderItem) orderItemsOIS.readObject();
                orderItems.add(item);
            }
        }

        catch (Exception exception) {
            exception.printStackTrace();
        }

        if (orders.isEmpty() || customers.isEmpty() || books.isEmpty() || orderItems.isEmpty()) {
            System.out.println(
                "\nWARNING: Database initialization complete." +
                "\nOne or more table was found empty." +
                "\n(Expected result on if you have not added records)"       
            );
        }
    }

    // OUT
    public void Out() {
        // Out order data
        try (ObjectOutput orderOOS = new ObjectOutputStream(new FileOutputStream(ordersTable))) {
            for (Order order : orders) {
                orderOOS.writeObject(order);
            }

            System.out.println("\nOrders file write successful.");
        }

        catch (Exception exception) {
            exception.printStackTrace();
        }

        // Out order data
        try (ObjectOutput cusomterOOS = new ObjectOutputStream(new FileOutputStream(customersTable))) {
            for (Customer customer : customers) {
                cusomterOOS.writeObject(customer);
            }

            System.out.println("Customers file write successful.");
        }

        catch (Exception exception) {
            exception.printStackTrace();
        }

        // Out order data
        try (ObjectOutput booksOOS = new ObjectOutputStream(new FileOutputStream(booksTable))) {
            for (Book book : books) {
                booksOOS.writeObject(book);
            }

            System.out.println("Books file write successful.");
        }

        catch (Exception exception) {
            exception.printStackTrace();
        }

        // Out items data
        try (ObjectOutput orderItemsOOS = new ObjectOutputStream(new FileOutputStream(orderItemsTable))) {
            for (OrderItem item : orderItems) {
                orderItemsOOS.writeObject(item);
            }

            System.out.println("Items file write successful.");
        }

        catch (Exception exception) {
            exception.printStackTrace();
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
                for (Order order : orders) {
                    if (order.ID() == ID) {
                        duplicate = true;
                        break;
                    }
                }

                break;

            case 1:
                for (Customer customer : customers) {
                    if (customer.ID() == ID) {
                        duplicate = true;
                        break;
                    }
                }

                break;

            case 2:
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
                for (Order order : orders) {
                    if (order.ID() >= highestID) {
                        highestID = order.ID() + 1;
                    }
                }

                return highestID;

            case 1:
                for (Customer customer : customers) {
                    if (customer.ID() >= highestID) {
                        highestID = customer.ID() + 1;
                    }
                }

                return highestID;

            case 2:
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
    public void InsertOrder(Order inOrder) {
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
        }
    }
    public void InsertCustomer(Customer inCustomer) {
        boolean duplicate = false;
        for (Customer customer : customers) {
            if (customer.ID() == inCustomer.ID()) {
                duplicate = true;
                System.out.println("\nWARNING: Duplicate insertion rejected. [InsertCustomer FAILED]");
            }
        }

        if (!duplicate) {
            customers.add(inCustomer);
        }
    }
    public void InsertBook(Book inBook) {
        boolean duplicate = false;
        for (Book book : books) {
            if (book.ID() == inBook.ID()) {
                duplicate = true;
                System.out.println("\nWARNING: Duplicate insertion rejected. [InsertBook FAILED]");
            }
        }

        if (!duplicate) {
            books.add(inBook);
        }
    }
    public void InsertOrderItem(OrderItem inOrderItem) {
        boolean duplicate = false;
        for (OrderItem orderItem : orderItems) {
            if (orderItem.orderID == inOrderItem.orderID && orderItem.itemID == inOrderItem.itemID) {
                duplicate = true;
                System.out.println("\nWARNING: Duplicate insertion rejected. [InsertOrderItem FAILED]");
            }
        }

        if (!duplicate) {
            for (Book book : books) {
                // Subtract books from stock
                if (book.ID() == inOrderItem.itemID) {
                    book.UpdateQty(inOrderItem.qty * -1);
                }
            }
            
            orderItems.add(inOrderItem);
        }
    }

    // SELECT (these are like getters on our arrs)
    public ArrayList<Order> SelectOrders() {
        return orders;
    }
    public Order SelectOrderByID(Integer ID) {
        Order identifiedOrder = null;

        while (identifiedOrder == null) {
            int i = 0;
            if (!orders.isEmpty() && orders.get(i).ID() == ID) {
                identifiedOrder = orders.get(i);
                System.out.println("\nOrder found.");
            }

            i++;
        }

        System.out.println("\nWARNING: Order not found! [SelectOrderByID FAILED]");

        return identifiedOrder;
    }

    public ArrayList<Customer> SelectCustomers() {
        return customers;
    }
    public Customer SelectCustomerByID(Integer ID) {
        Customer identifiedCustomer = null;

        while (identifiedCustomer == null) {
            int i = 0;
            if (!customers.isEmpty() && customers.get(i).ID() == ID) {
                identifiedCustomer = customers.get(i);
                System.out.println("\nCustomer found.");
            }

            i++;
        }

        System.out.println("\nWARNING: Customer not found! [SelectCustomerByID FAILED]");
        
        return identifiedCustomer;
    }
    public Customer SelectCustomerByName(String name) {
        Customer identifiedCustomer = null;

        while (identifiedCustomer == null) {
            int i = 0;
            if (!customers.isEmpty() && customers.get(i).Name() == name) {
                identifiedCustomer = customers.get(i);
                System.out.println("\nCustomer found.");
            }

            i++;
        }

        System.out.println("\nWARNING: Customer not found! [SelectCustomerByName FAILED]");
        
        return identifiedCustomer;
    }

    public ArrayList<Book> SelectBooks() {
        return books;
    }
    public Book SelectBookByID(Integer ID) {
        Book identifiedBook = null;

        while (identifiedBook == null) {
            int i = 0;
            if (!books.isEmpty() && books.get(i).ID() == ID) {
                identifiedBook = books.get(i);
                System.out.println("\nBook found.");
            }

            i++;
        }

        System.out.println("\nWARNING: Book not found! [SelectCustomerByID FAILED]");
        
        return identifiedBook;
    }
    public Book SelectBookByISBN(Long ISBN) {
        Book identifiedBook = null;

        while (identifiedBook == null) {
            int i = 0;
            if (!books.isEmpty() && books.get(i).ISBN() == ISBN) {
                identifiedBook = books.get(i);
                System.out.println("\nBook found.");
            }

            i++;
        }

        System.out.println("\nWARNING: Book not found! [SelectCustomerByID FAILED]");
        
        return identifiedBook;
    }

    private ArrayList<OrderItem> SelectOrderItems() {
        return orderItems;
    }
    public ArrayList<OrderItem> SelectOrderItemsByOrderID(Integer ID) {
        ArrayList<OrderItem> items = new ArrayList<OrderItem>();

        for (OrderItem item : orderItems) {
            if (item.orderID == ID) {
                items.add(item);
            }
        }

        return items;
    }
    public ArrayList<OrderItem> SelectOrderItemsByItemID(Integer ID) {
        ArrayList<OrderItem> items = new ArrayList<OrderItem>();

        for (OrderItem item : orderItems) {
            if (item.itemID == ID) {
                items.add(item);
            }
        }

        return items;
    }

    // UPDATE
    public void UpdateCustomerByID(Integer ID, Scanner scanner) {
        for (Customer customer : customers) {
            if(customer.ID() == ID) {
                customer.UpdateAddress(scanner);
                customer.UpdateEmail(scanner);
                customer.UpdatePhone(scanner);

                System.out.println("\nCustomer update successful.");

                break;
            }
        }

        System.out.println("\nWARNING: Customer not found! [UpdateCustomerByID FAILED]");
    }
    public void UpdateCustomerByName(String name, Scanner scanner) {
        for (Customer customer : customers) {
            if(customer.Name().strip().equalsIgnoreCase(name.strip())) {
                customer.UpdateAddress(scanner);
                customer.UpdateEmail(scanner);
                customer.UpdatePhone(scanner);

                System.out.println("\nCustomer update successful.");

                break;
            }
        }

        System.out.println("\nWARNING: Customer not found! [UpdateCustomerByName FAILED]");
    }
    public void UpdateBookByID(Integer ID, Integer amount) {
        for (Book book : books) {
            if(book.ID() == ID) {
                book.UpdateQty(amount);

                System.out.println("\nBook quantity update successful.");

                break;
            }
        }

        System.out.println("\nWARNING: Book not found! [UpdateCustomerByID FAILED]");
    }

    // DELETE
    public void RemoveOrder(Integer ID) {
        for (Order order : orders) {
            if(order.ID() == ID) {
                orders.remove(order);

                System.out.println("\nOrder removed successfully.");

                break;
            }
        }

        System.out.println("\nWARNING: Order not found! [RemoveOrder FAILED]");
    }
    public void RemoverOrderItemByOrderID(Integer ID) {
        ArrayList<OrderItem> orderItems = SelectOrderItems();

        for (OrderItem orderItem : orderItems) {
            if (orderItem.orderID == ID) {
                UpdateBookByID(orderItem.itemID, orderItem.qty);  // Reverse book qty subtraction
                orderItems.remove(orderItem);
            }
        }

        System.out.println("\nOrder items all removed successfully.");
    }
}