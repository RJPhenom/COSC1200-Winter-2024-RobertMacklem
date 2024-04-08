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
    static final String MainMenu =      "\n" + 
                                        "1. \tTransact\n" +
                                        "2. \tReport\n" +
                                        "3. \tQuit\n";

    static final String TransactMenu =  "\n" + 
                                        "1. \tNew Order\n" +
                                        "2. \tReverse Order\n" +
                                        "3. \tNew Book(s)\n" +
                                        "4. \tEdit Customer\n" +
                                        "5. \tExit to previous menu.\n";

    static final String ReportMenu =    "\n" + 
                                        "1. \tGenerate Customer report by name.\n" +
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


    // Streams
    static ObjectInputStream bookOIS;
    static ObjectInputStream orderOIS;
    static ObjectInputStream customerOIS;

    static ObjectOutputStream bookOOS;
    static ObjectOutputStream orderOOS;
    static ObjectOutputStream customerOOS;

    // Live data
    static ArrayList<Book> books = new ArrayList<Book>();
    static ArrayList<Order> orders = new ArrayList<Order>();
    static ArrayList<Customer> customers = new ArrayList<Customer>();

    // Exit method
    static Runnable exitMethod = () -> {};

    // Menu Hashmaps
    static final HashMap<Integer, Runnable> MainMenuMap = new HashMap<Integer, Runnable>() {{
        put(1, () -> MenuLoop(TransactMenu, TransactMenuMap));  // lambdas let us run submenus out of a dict (noice)
        put(2, () -> MenuLoop(ReportMenu, MainMenuMap));
        put(3, exitMethod);
    }};

    static final HashMap<Integer, Runnable> TransactMenuMap = new HashMap<Integer, Runnable>() {{
        put(1, () -> NewOrders());
        put(2, () -> ReverseOrder());
        put(3, () -> AddBooks());
        put(4, () -> EditCustomer());
        put(5, exitMethod);

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
        InitIOStreams();                    // Initialize our IO streams to our files
        InitLiveData();                     // Initialize our active arrays for the current session
        MenuLoop(MainMenu, MainMenuMap);    // Run the menu loop
        System.out.println(ExitMsg);        // Printout an exit message before quitting
    }

    private static void MenuLoop(String MENU, HashMap<Integer, Runnable> MENUMAP) {
        boolean exit = false;
        while (!exit) {
            System.out.println(MENU);
            try {
                int mode = scanner.nextInt();
                scanner.nextLine();
                Runnable modeExecutable = MENUMAP.get(mode);
                if (modeExecutable != null) {
                    if (modeExecutable != exitMethod) {
                        modeExecutable.run();
                    }

                    else {
                        exit = true;
                    }
                }

                else {
                    throw new Exception();
                }
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
    
    private static void InitIOStreams() {
        try{
            bookOIS = new ObjectInputStream(new FileInputStream(booksTable));
            orderOIS = new ObjectInputStream(new FileInputStream(ordersTable));
            customerOIS  = new ObjectInputStream(new FileInputStream(customersTable));

            bookOOS = new ObjectOutputStream(new FileOutputStream(booksTable, true));
            orderOOS = new ObjectOutputStream(new FileOutputStream(booksTable, true));
            customerOOS = new ObjectOutputStream(new FileOutputStream(booksTable, true));
        }

        catch (Exception exception) {

        }
    }

    private static void InitLiveData() {
        // Populate books array
        ReadBooks();
        ReadCustomers();
        ReadOrders();

    }
    // Gens a new integer ID that is the highest ID integer in the file + 1
    private static Integer GenerateID(File file) {
        if (file == ordersTable) {
            Integer highestID = 0;
            for (Order order : orders) {
                highestID = (order.ID() > highestID) ? order.ID() : highestID;
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
    
    // Reader functions
    private static void ReadCustomers() {
        try {
            while (true) {
                try {
                    Customer customer = (Customer) customerOIS.readObject();
                    customers.add(customer);
                }

                catch (EOFException end) {
                    end.printStackTrace();
                    break;
                }
            }
        }

        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void ReadOrders() {
        try {
            while (true) {
                try {
                    Order order = (Order) orderOIS.readObject();
                    orders.add(order);
                }

                catch (EOFException end) {
                    end.printStackTrace();
                    break;
                }
            }
        }

        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void ReadBooks() {
        try {
            while (true) {
                try {
                    System.out.println("here");
                    Book book = (Book) bookOIS.readObject();
                    System.out.println("and here");
                    books.add(book);
                }

                catch (EOFException end) {
                    end.printStackTrace();
                    break;
                }
            }
        }

        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    // Writer functions
    private static void WriteCustomers(ArrayList<Customer> customers) {
        for (Customer customer : customers) {
            try {
                customerOOS.writeObject(customer);
            }

            catch (Exception exception) {
                
            }
        }
    }

    private static void WriteOrders(ArrayList<Order> orders) {
        for (Order order : orders) {
            try {
                orderOOS.writeObject(order);
            }

            catch (Exception exception) {
                
            }
        }
    }

    private static void WriteBooks(ArrayList<Book> books) {
        for (Book book : books) {
            try {
                bookOOS.writeObject(book);
            }

            catch (Exception exception) {

            }
        }
    }

    // Finder functions
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
            if (customer.Name().toUpperCase() == name.toUpperCase()) {
                return customer.ID();
            }
        }

        System.out.println("\nWARNING: Customer not found!\n");
        return -1;
    }

    
    // --TRANSACTIONS--
    private static void AddBook(Long ISBN) {
        Integer newBookID = GenerateID(booksTable);
        Book book = new Book(newBookID, scanner, ISBN);

        try {
            bookOOS.writeObject(book);
        } 
        
        catch (Exception exception) {
            System.out.println("\n***ERROR*** Unexpected error writing new book to file.\n");
        }
    }

    private static void AddBooks() {
        ArrayList<Book> books = ReadBooks();
        for (Book book : books) {
            System.out.println(book.ISBN().toString());
        }

        boolean finished = false;
        while (!finished) {
            System.out.println("\nPlease enter the 13 digit ISBN: ");
            Long ISBN = scanner.nextLong();
            scanner.nextLine();

            if (ISBN <= 999999999999l || ISBN > 9999999999999l) {
                System.out.println("\n***ERROR*** ISBN must be 13 digits!\nPlease try again.");
                continue;
            }


            // Check for duplicate book ISBN entries!
            boolean duplicate = false;
            for (Book book : books) {
                if (book.ISBN() == ISBN) {
                    duplicate = true;

                    System.out.println("\nA record already exists for this ISBN. Increase quantity? (Enter 'Y' to accept): ");
                    String decision = scanner.nextLine();

                    if (decision.equalsIgnoreCase("Y")) {
                        System.out.println("\nInput the quantity you would like to add: ");
                        int qty = scanner.nextInt();
                        scanner.nextLine();

                        book.UpdateQty(qty);
                    }

                    break;
                }
            }

            if (!duplicate) {
                AddBook(ISBN);
            }
            
            System.out.println("\nAdd another book?\n(Enter 'Y' to accept): ");
            String decision = scanner.nextLine();
            if (!decision.equalsIgnoreCase("Y")) {
                finished = true;
            }
        }
    }

    private static void EditCustomer() {
        System.out.println("\nPlease enter the name of the customer to edit: ");
        String name = scanner.nextLine();

        boolean validName = false;
        while (!validName) {
            if (name != "") {
                ArrayList<Customer> customers = ReadCustomers();
                Integer ID = findCustomerIDByName(name, customers);
                Integer index = findCustomerIndex(ID, customers);
        
                if (index != -1) {
                    customers.get(index).UpdateAddress(scanner);
                    customers.get(index).UpdateEmail(scanner);
                    customers.get(index).UpdatePhone(scanner);
                }   
                
                WriteCustomers(customers);
            }
    
            else {
                System.out.println("\n***ERROR*** Names cannot be NULL.\nPlease try again.\n");
            }
        }
    }

    private static void NewOrders() {
        ArrayList<Customer> customers = ReadCustomers();
        ArrayList<Book> books = ReadBooks();
        ArrayList<Order> orders = ReadOrders();

        boolean finishedOrders = false;
        while (!finishedOrders) {
            // Customer handling for the order
            int customerIndex = -1;
            boolean validName = false;
            while (!validName) {
                System.out.println("\nPlease enter the customer name for your order: ");
                String name = scanner.nextLine();

                // If the user inputs null str
                if (name != "") {
                    for (Customer customer : customers) {
                        if (customer.Name().toUpperCase() == name.toUpperCase()) {
                            Integer ID = findCustomerIDByName(name, customers);
                            customerIndex = findCustomerIndex(ID, customers);
                            validName = true;
                            break;
                        }
                    }

                    // If the customer couldn't be found!
                    if (customerIndex == -1) {
                        System.out.println("\nNo existing customer by that name.\nCreating new customer...");

                        Integer ID = GenerateID(customersTable);
                        Customer customer = new Customer(ID, scanner);
                        customers.add(customer);
                        WriteCustomers(customers);

                        customerIndex = customers.size() - 1;
                        validName = true;
                    }
                }

                else {
                    System.out.println("\n***ERROR*** Name cannot be NULL.\nPlease try again.\n");
                }
            }

            // Item handling for the order
            ArrayList<Integer> booksForOrder = new ArrayList<Integer>();
            ArrayList<Integer> qtysForOrder = new ArrayList<Integer>();

            // Loop to allow buying multiple items
            boolean finishedItems = false;
            while (!finishedItems) {
                // Get the ISBN (id) of the book and the amount user wants to order
                System.out.println("\nPlease enter the ISBN of the book to add to your order: \n");
                Long ISBN = scanner.nextLong();
                System.out.println("\nPlease enter the quantity you'd like to order: \n");
                int qty = scanner.nextInt();

                // Lookup the book if its in stock (or exists)
                for (Book book : books) {
                    if (book.ISBN() == ISBN) {

                        // Make sure there is enough stock
                        if (book.Qty() >= qty) {
                            booksForOrder.add(book.ID());
                            qtysForOrder.add(qty);
                            book.UpdateQty(qty * -1);
                        }

                        else {
                            System.out.println("\nWARNING: Not enough books in stock. Buy remaining stock? (Enter 'Y' to accept): \n");
                            String decision = scanner.nextLine();

                            if (decision.equalsIgnoreCase("Y")) {
                                qty = book.Qty();
                                booksForOrder.add(book.ID());
                                qtysForOrder.add(qty);
                                book.UpdateQty(qty * -1);

                                System.out.println("\nRemaining stock added.\n");
                            }
                        }

                        break;
                    }
                }

                // Add another book?
                System.out.println("\nAdd more books to your order? (Enter 'Y' to accept): \n");
                String decision = scanner.nextLine();

                if (decision.equalsIgnoreCase("Y")) {
                    finishedItems = true;
                }
            }

            // Gen the order record
            Integer ID = GenerateID(ordersTable);
            Order order = new Order(ID, customers.get(customerIndex).ID(), booksForOrder, qtysForOrder);

            // Add it to the arr
            orders.add(order);

            // Loop?
            System.out.println("\nOrder submitted. Would you like to add another order? (Enter 'Y' to accept): ");
            String decision = scanner.nextLine();
            if (decision.equalsIgnoreCase("Y")) {
                finishedOrders = true;
            }
        }

        // Write and exit
        WriteOrders(orders);
    }

    private static void ReverseOrder() {
        System.out.println("\nPlease enter the Order ID to be reversed: ");
        Integer ID = scanner.nextInt();
        ArrayList<Order> orders = ReadOrders();
        int orderIndex = findOrderIndex(ID, orders);

        if (orderIndex != -1) {
        // Book reader call is here because we dont need it outside validation scope and could save us some
        // speed at runtime if user puts in bad values
        ArrayList<Book> books = ReadBooks();

        // Loops over all items in the order, looks up those books in the books arr and adds back the
        // book qty from the order
        for (int i = 0; i < orders.get(orderIndex).Items().size(); i++) {
            Integer bookID = orders.get(orderIndex).Items().get(i);
            int bookIndex = findBookIndex(bookID, books);
            books.get(bookIndex).UpdateQty(orders.get(orderIndex).Quantities().get(i));
        }

        // Cull the order
        orders.remove(orders.get(orderIndex));

        // Write back
        WriteOrders(orders);
        WriteBooks(books);
        }
    }
}