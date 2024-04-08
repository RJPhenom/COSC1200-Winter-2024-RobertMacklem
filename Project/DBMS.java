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
                ArrayList<Customer> customers = SelectCustomers();
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
    public void Insert(File table, Object record) {
        int tableIndex = IdentifyTable(table);

        switch (tableIndex) {
            case 0:
                ArrayList<Order> orders = SelectOrders(); 
                try (ObjectOutputStream OOS = new ObjectOutputStream(new FileOutputStream(table))) {
                    Order order = (Order) record;
                    if (!IdentifyDuplicate(table, order.ID())) {
                        orders.add(order);

                        for (Order o : orders) {
                            OOS.writeObject(o);
                        }

                        System.out.println("\nOrder write successful.");
                    }

                    else {
                        System.out.println("\nOrder write failed: duplicate entry detected.");
                    }
                }

                catch (Exception exception) {
                    exception.printStackTrace();
                }

                break;

            case 1:
                ArrayList<Customer> customers = SelectCustomers(); 
                try (ObjectOutputStream OOS = new ObjectOutputStream(new FileOutputStream(table))) {
                    Customer customer = (Customer) record;
                    if (!IdentifyDuplicate(table, customer.ID())) {
                        customers.add(customer);

                        for (Customer c : customers) {
                            OOS.writeObject(c);
                        }
                        
                        System.out.println("\nCustomer write successful.");
                    }

                    else {
                        System.out.println("\nCustomer write failed: duplicate entry detected.");
                    }
                }

                catch (Exception exception) {
                    exception.printStackTrace();
                }

                break;

            case 2:
                ArrayList<Book> books = SelectBooks(); 
                try (ObjectOutputStream OOS = new ObjectOutputStream(new FileOutputStream(table))) {
                    Book book = (Book) record;
                    if (!IdentifyDuplicate(table, book.ID())) {
                        books.add(book);

                        for (Book b : books) {
                            OOS.writeObject(b);
                        }                        System.out.println("\nOrder write successful.");
                    }

                    else {
                        System.out.println("\nOrder write failed: duplicate entry detected.");
                    }
                }

                catch (Exception exception) {
                    exception.printStackTrace();
                }

                break;
        
            case 3:
                ArrayList<OrderItem> orderItems = SelectOrderItems(); 
                try (ObjectOutputStream OOS = new ObjectOutputStream(new FileOutputStream(table))) {
                    OrderItem orderItem = (OrderItem) record;
                    orderItems.add(orderItem);

                    for (OrderItem oi : orderItems) {
                        OOS.writeObject(oi);
                    }                        
                    
                    System.out.println("\nOrder write successful.");                
                }

                catch (Exception exception) {
                    exception.printStackTrace();
                }

                break;

            default:
                System.out.println("\nWARNING: Table not found! [Insert FAILED]");
            }
    }

    // SELECT
    public ArrayList<Order> SelectOrders() {
        ArrayList<Order> records = new ArrayList<Order>();

        try (ObjectInputStream OIS = new ObjectInputStream(new FileInputStream(ordersTable))) {
            while (true) {
                Order order = (Order) OIS.readObject();
                ((ArrayList<Order>) records).add(order);
            }
        }

        catch (Exception exception) {
            exception.printStackTrace();
        }

        return records;
    }
    public Order SelectOrderByID(Integer ID) {
        Order identifiedOrder = null;

        try (ObjectInputStream OIS = new ObjectInputStream(new FileInputStream(ordersTable))) {
            while (true) {
                Order order = (Order) OIS.readObject();
                if (order.ID() == ID) {
                    identifiedOrder = order;
                    throw new Exception("\nOrder found: closing File Stream.");
                }
            }
        }

        catch (Exception exception) {
            exception.printStackTrace();  // Debugging
        }

        System.out.println("\nWARNING: Order not found! [SelectOrderByID FAILED]");
        return identifiedOrder;
    }

    public ArrayList<Customer> SelectCustomers() {
        ArrayList<Customer> records = new ArrayList<Customer>();

        try (ObjectInputStream OIS = new ObjectInputStream(new FileInputStream(customersTable))) {
            while (true) {
                Customer customer = (Customer) OIS.readObject();
                ((ArrayList<Customer>) records).add(customer);
            }
        }

        catch (Exception exception) {
            exception.printStackTrace();
        }

        return records;
    }
    public Customer SelectCustomerByID(Integer ID) {
        Customer identifiedCustomer = null;

        try (ObjectInputStream OIS = new ObjectInputStream(new FileInputStream(ordersTable))) {
            while (true) {
                Customer customer = (Customer) OIS.readObject();
                if (customer.ID() == ID) {
                    identifiedCustomer = customer;
                    throw new Exception("\nCustomer found: closing File Stream.");
                }
            }
        }

        catch (Exception exception) {
            exception.printStackTrace();  // Debugging
        }

        System.out.println("\nWARNING: Customer not found! [SelectCustomerByID FAILED]");
        return identifiedCustomer;
    }
    public Customer SelectCustomerByName(String name) {
        Customer identifiedCustomer = null;

        try (ObjectInputStream OIS = new ObjectInputStream(new FileInputStream(ordersTable))) {
            while (true) {
                Customer customer = (Customer) OIS.readObject();
                if (customer.Name() == name) {
                    identifiedCustomer = customer;
                    throw new Exception("\nCustomer found: closing File Stream.");
                }
            }
        }

        catch (Exception exception) {
            exception.printStackTrace();  // Debugging
        }

        System.out.println("\nWARNING: Customer not found! [SelectCustomerByName FAILED]");
        return identifiedCustomer;
    }

    public ArrayList<Book> SelectBooks() {
        ArrayList<Book> records = new ArrayList<Book>();

        try (ObjectInputStream OIS = new ObjectInputStream(new FileInputStream(booksTable))) {
            while (true) {
                Book book = (Book) OIS.readObject();
                ((ArrayList<Book>) records).add(book);
            }
        }

        catch (Exception exception) {
            exception.printStackTrace();
        }

        return records;
    }
    public Book SelectBookByID(Integer ID) {
        Book identifiedBook = null;

        try (ObjectInputStream OIS = new ObjectInputStream(new FileInputStream(ordersTable))) {
            while (true) {
                Book book = (Book) OIS.readObject();
                if (book.ID() == ID) {
                    identifiedBook = book;
                    throw new Exception("\nBook found: closing File Stream.");
                }
            }
        }

        catch (Exception exception) {
            exception.printStackTrace();  // Debugging
        }

        System.out.println("\nWARNING: Book not found! [SelectBookByID FAILED]");
        return identifiedBook;
    }
    public Book SelectBookByISBN(Long ISBN) {
        Book identifiedBook = null;

        try (ObjectInputStream OIS = new ObjectInputStream(new FileInputStream(ordersTable))) {
            while (true) {
                Book book = (Book) OIS.readObject();
                if (book.ISBN() == ISBN) {
                    identifiedBook = book;
                    throw new Exception("\nBook found: closing File Stream.");
                }
            }
        }

        catch (Exception exception) {
            exception.printStackTrace();  // Debugging
        }

        System.out.println("\nWARNING: Book not found! [SelectBookByISBN FAILED]");
        return identifiedBook;
    }

    private ArrayList<OrderItem> SelectOrderItems() {
        ArrayList<OrderItem> records = new ArrayList<OrderItem>();

        try (ObjectInputStream OIS = new ObjectInputStream(new FileInputStream(orderItemsTable))) {
            while (true) {
                OrderItem oi = (OrderItem) OIS.readObject();
                ((ArrayList<OrderItem>) records).add(oi);
            }
        }

        catch (Exception exception) {
            exception.printStackTrace();
        }

        return records;
    }
    public ArrayList<OrderItem> SelectOrderItemsByOrderID(Integer ID) {
        ArrayList<OrderItem> records = new ArrayList<OrderItem>();

        try (ObjectInputStream OIS = new ObjectInputStream(new FileInputStream(orderItemsTable))) {
            while (true) {
                OrderItem item = (OrderItem) OIS.readObject();
                if (item.orderID == ID) {
                    ((ArrayList<OrderItem>) records).add(item);
                }
            }
        }

        catch (Exception exception) {
            exception.printStackTrace();
        }

        return records;
    }
    public ArrayList<OrderItem> SelectOrderItemsByItemID(Integer ID) {
        ArrayList<OrderItem> records = new ArrayList<OrderItem>();

        try (ObjectInputStream OIS = new ObjectInputStream(new FileInputStream(orderItemsTable))) {
            while (true) {
                OrderItem item = (OrderItem) OIS.readObject();
                if (item.itemID == ID) {
                    ((ArrayList<OrderItem>) records).add(item);
                }
            }
        }

        catch (Exception exception) {
            exception.printStackTrace();
        }

        return records;
    }

    // UPDATE
    public void UpdateCustomerByID(Integer ID, Scanner scanner) {
        ArrayList<Customer> customers = SelectCustomers();

        for (Customer customer : customers) {
            if(customer.ID() == ID) {
                customer.UpdateAddress(scanner);
                customer.UpdateEmail(scanner);
                customer.UpdatePhone(scanner);

                try (ObjectOutputStream OOS = new ObjectOutputStream(new FileOutputStream(customersTable))) {
                    for (Customer overwrite : customers) {
                        OOS.writeObject(overwrite);
                    }
                }

                catch (Exception exception) {
                    exception.printStackTrace();
                }

                System.out.println("\nCustomer update successful.");

                break;
            }
        }

        System.out.println("\nWARNING: Customer not found! [UpdateCustomerByID FAILED]");
    }
    public void UpdateCustomerByName(String name, Scanner scanner) {
        ArrayList<Customer> customers = SelectCustomers();

        for (Customer customer : customers) {
            if(customer.Name().toUpperCase() == name.toUpperCase()) {
                customer.UpdateAddress(scanner);
                customer.UpdateEmail(scanner);
                customer.UpdatePhone(scanner);

                try (ObjectOutputStream OOS = new ObjectOutputStream(new FileOutputStream(customersTable))) {
                    for (Customer overwrite : customers) {
                        OOS.writeObject(overwrite);
                    }
                }

                catch (Exception exception) {
                    exception.printStackTrace();
                }

                System.out.println("\nCustomer update successful.");

                break;
            }
        }

        System.out.println("\nWARNING: Customer not found! [UpdateCustomerByName FAILED]");
    }

    public void UpdateBookByID(Integer ID, Integer amount) {
        ArrayList<Book> books = SelectBooks();

        for (Book book : books) {
            if(book.ID() == ID) {
                book.UpdateQty(amount);
                try (ObjectOutputStream OOS = new ObjectOutputStream(new FileOutputStream(customersTable))) {
                    for (Book overwrite : books) {
                        OOS.writeObject(overwrite);
                    }
                }

                catch (Exception exception) {
                    exception.printStackTrace();
                }

                System.out.println("\nCustomer update successful.");

                break;
            }
        }

        System.out.println("\nWARNING: Customer not found! [UpdateCustomerByID FAILED]");
    }

    // DELETE
    public void RemoveOrder(Integer ID) {
        ArrayList<Order> orders = SelectOrders();

        for (Order order : orders) {
            if(order.ID() == ID) {
                orders.remove(order);
                try (ObjectOutputStream OOS = new ObjectOutputStream(new FileOutputStream(customersTable))) {
                    for (Order overwrite : orders) {
                        OOS.writeObject(overwrite);
                    }
                }

                catch (Exception exception) {
                    exception.printStackTrace();
                }

                System.out.println("\nCustomer update successful.");

                break;
            }
        }

        System.out.println("\nWARNING: Customer not found! [UpdateCustomerByID FAILED]");
    }

    public void RemoverOrderItemByOrderID(Integer ID) {
        ArrayList<OrderItem> orderItems = SelectOrderItems();

        for (OrderItem orderItem : orderItems) {
            orderItems.remove(orderItem);
        }

        try (ObjectOutputStream OOS = new ObjectOutputStream(new FileOutputStream(customersTable))) {
            for (OrderItem overwrite : orderItems) {
                OOS.writeObject(overwrite);
                System.out.println("\nOrder Item removal successful.");
            }
        }

        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}