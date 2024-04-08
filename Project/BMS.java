// ***************************************************************************************
//  Title:              Bookstore Management System
//  Author:             Robert Macklem
//  Date:               29 March 2024
//
//  Description:        The Bookstore Management System (BMS) is the main
//                      management software for the Library. It manages
//                      book, customer, and order records and facilitates
//                      transactions and Bookstore operations.
//
//                      This software was developed for COSC1200-01, Winter
//                      2024 at Durham College.
//                      
// ***************************************************************************************
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;

public class BMS {
    // --CONSTS--
    // Menus
    static final String MainMenu =      "1. \tTransact\n" +
                                        "2. \tReport\n" +
                                        "3. \tQuit\n";

    static final String TransactMenu =  "1. \tNew Order\n" +
                                        "2. \tReverse Order\n" +
                                        "3. \tNew Book(s)\n" +
                                        "4. \tEdit Customer\n" +
                                        "5. \tExit to previous menu.\n";

    static final String ReportMenu =    "1. \tGenerate Customer report by name.\n" +
                                        "2. \tGenerate Customer report by ID.\n" +
                                        "3. \tGenerate Customers report.\n" +
                                        "4. \tGenerate last Order\n" +
                                        "5. \tGenerate past Orders report by date\n" +
                                        "6. \tGenerate past Orders report (all)\n" +
                                        "7. \tQuery Book by ISBN\n" +
                                        "8. \tQuery Book by Title\n" +
                                        "9. \tQuery Book by Author\n" +
                                        "10. \tQuery Book by Publisher\n" +
                                        "11. \tExit to previous menu.\n";
    // System Messages
    static final String WelcomeMsg =   "\n*************************************************\n" +
                                        "Welcome to Bookstore Management System (BMS) 1.0!" +
                                        "\n*************************************************\n" +
                                        "\nHow can we help your bookstore operations today?\n";

    static final String InputErrorMsg = "\n***ERROR*** Please retry using a valid input!\n";

    static final String FileErrorMsg =  "\n***ERROR*** File operations could not be resolved\n";

    static final String ExitMsg =       "\nQuitting application...\n\nGoodbye!\n";

    // Menu Hashmaps
    static final HashMap<Integer, Runnable> MainMenuMap = new HashMap<Integer, Runnable>() {{
        put(1, () -> MenuLoop(TransactMenu, TransactMenuMap));  // lambdas let us run submenus out of a dict (noice)
        put(2, () -> MenuLoop(ReportMenu, MainMenuMap));
        put(3, () -> {});
    }};

    static final HashMap<Integer, Runnable> TransactMenuMap = new HashMap<Integer, Runnable>() {{
        put(1, () -> MenuLoop(TransactMenu, MainMenuMap));
        put(2, () -> MenuLoop(ReportMenu, MainMenuMap));
        put(3, () -> {});
    }};


    // --VARS--
    // Generic scanner (for menu navigation)
    static Scanner scanner = new Scanner(System.in);

    // Database variables
    static ArrayList<File> db = new ArrayList<File>();

    static File ordersTable = new File("Orders");
    static File customersTable = new File("Customers");
    static File booksTable = new File("Books");


    // --MAIN--
    public static void main(String args[]) {
        System.out.println(WelcomeMsg);     // Print welcome message
        InitFiles();                        // Initialize db
        MenuLoop(MainMenu, MainMenuMap);    // Run the menu loop
        System.out.println(ExitMsg);        // Printout an exit message before quitting
    }

    private static void MenuLoop(String MENU, HashMap<Integer, Runnable> MENUMAP) {
        boolean exit = false;
        while (!exit) {
            System.out.println(MENU);
            try {
                int mode = scanner.nextInt();
                Runnable modeExecutable = MENUMAP.get(mode);
                if (modeExecutable != null) {
                    modeExecutable.run();
                }

                else {
                    throw new Exception();
                }

                exit = true;
            }

            catch (Exception exception) {
                System.out.println(InputErrorMsg);
                scanner.next();
            }
        }
    }


    // --FILE HANDLING--
    // Init runs once, when program starts to ensure there are the files for the live session.
    private static void InitFiles() {
        db.add(ordersTable);
        db.add(customersTable);
        db.add(booksTable);

        for (File table : db) {
            if (table.exists()) {

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

    private static Integer GenerateID(File file) {
        if (file == ordersTable) {
            Integer highestID = 0;
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ordersTable))) {
                while (true) {
                    Order order = (Order) ois.readObject();
                    if (order.ID() > highestID) {
                        highestID = order.ID();
                    }
                }
            } 
            
            catch (Exception exception) {

            } 

            return highestID++;
        }

        else if (file == customersTable) {
            Integer highestID = 0;
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(customersTable))) {
                while (true) {
                    Customer customer = (Customer) ois.readObject();
                    if (customer.ID() > highestID) {
                        highestID = customer.ID();
                    }
                }
            } 
            
            catch (Exception exception) {

            } 

            return highestID++;
        }

        else if (file == booksTable) {
            Integer highestID = 0;
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(booksTable))) {
                while (true) {
                    Book book = (Book) ois.readObject();
                    if (book.ID() > highestID) {
                        highestID = book.ID();
                    }
                }
            } 
            
            catch (Exception exception) {

            } 

            return highestID++;
        }

        else {
            System.out.println("\n***ERROR*** Could not generate unique ID: file type mismatch!\n");
            return 0;
        }
    }
    
    private static ArrayList<Customer> ReadCustomers() {
        ArrayList<Customer> customers = new ArrayList<Customer>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(customersTable))) {
            while (true) {
                Customer customer = (Customer) ois.readObject();
                customers.add(customer);
            }
        } 
        
        catch (Exception exception) {

        } 

        return customers;
    }

    private static ArrayList<Order> ReadOrders() {
        ArrayList<Order> orders = new ArrayList<Order>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(customersTable))) {
            while (true) {
                Order order = (Order) ois.readObject();
                orders.add(order);
            }
        } 
        
        catch (Exception exception) {

        } 

        return orders;
    }

    private static ArrayList<Book> ReadBooks() {
        ArrayList<Book> books = new ArrayList<Book>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(customersTable))) {
            while (true) {
                Book book = (Book) ois.readObject();
                books.add(book);
            }
        } 
        
        catch (Exception exception) {

        } 

        return books;
    }

    private static void WriteCustomers(ArrayList<Customer> customers) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(customersTable))) {
            for (Customer customer : customers) {
                oos.writeObject(customer);
            }
        } 
        
        catch (Exception exception) {

        }
    }

    private static void WriteOrders(ArrayList<Order> orders) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ordersTable))) {
            for (Order order : orders) {
                oos.writeObject(order);
            }
        } 
        
        catch (Exception exception) {

        }
    }

    private static void WriteBooks(ArrayList<Book> books) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(booksTable))) {
            for (Book book : books) {
                oos.writeObject(book);
            }
        } 
        
        catch (Exception exception) {

        }
    }

    private static Integer findCustomerIndex(Integer ID, ArrayList<Customer> customers) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).ID() == ID) {
                return i;
            }
        }

        System.out.println("\nWARNING: ID not found!\n");
        return -1;
    }

    private static Integer findOrderIndex(Integer ID, ArrayList<Order> orders) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).ID() == ID) {
                return i;
            }
        }

        System.out.println("\nWARNING: ID not found!\n");
        return -1;
    }

    private static Integer findBookIndex(Integer ID, ArrayList<Book> books) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).ID() == ID) {
                return i;
            }
        }

        System.out.println("\nWARNING: ID not found!\n");
        return -1;
    }

    private static Integer findCustomerIDByName(String name, ArrayList<Customer> customers) {
        for (Customer customer : customers) {
            if (customer.name().toUpperCase() == name.toUpperCase()) {
                return customer.ID();
            }
        }

        System.out.println("\nWARNING: Customer not found!\n");
        return -1;
    }

    // --TRANSACTIONS--
    private static void AddBook() {
        Integer newBookID = GenerateID(booksTable);
        Book book = new Book(newBookID, scanner);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(booksTable, true))) {
            oos.writeObject(book);
        } 
        
        catch (Exception exception) {

        }
    }

    private static void AddBooks() {
        boolean finished = false;
        while (!finished) {
            AddBook();
            System.out.println("\nAdd another book?\n(Enter 'Y' to accept): ");
            String decision = scanner.nextLine();
            if (decision.toUpperCase() != "Y") {
                finished = true;
            }
        }
    }

    private static void EditCustomer(String name) {
        ArrayList<Customer> customers = ReadCustomers();
        Integer ID = findCustomerIDByName(name, customers);
        Integer index = findCustomerIndex(ID, customers);

        if (index != -1) {
            customers.get(index).updateAddress(scanner);
            customers.get(index).updateEmail(scanner);
            customers.get(index).updatePhone(scanner);
        }   
        
        WriteCustomers(customers);
    }


    // -- REPORTS--


    private static void ReportCustomerByName(int ID) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(customersTable));

            boolean found = false;
            while (!found) {
                try {
                    Customer customer = (Customer) ois.readObject();
                    if (customer.ID() == ID) {
                        System.out.println(customer);
                    }
                }

                catch (Exception exception) {
                    System.out.println("Customer not found.");
                    found = true;
                }
            }

            ois.close();
        }

        catch (Exception exception) {
            System.out.println(FileErrorMsg);
        }
    }

    private static void ReportCustomerByID(String name) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(customersTable));

            boolean found = false;
            while (!found) {
                try {
                    Customer customer = (Customer) ois.readObject();
                    if (customer.name().toUpperCase() == name.toUpperCase()) {
                        System.out.println(customer);
                    }
                }

                catch (Exception exception) {
                    System.out.println("Customer not found.");
                    found = true;
                }
            }

            ois.close();
        }

        catch (Exception exception) {
            System.out.println(FileErrorMsg);
        }
    }

    private static void ReportCustomers() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(customersTable));

            boolean finished = false;
            while (!finished) {
                try {
                    Customer customer = (Customer) ois.readObject();
                    System.out.println(customer);
                }

                catch (Exception exception) {
                    finished = true;
                }

            }
            
            ois.close();
        }

        catch (Exception exception) {
            System.out.println(FileErrorMsg);
        }
    }
}