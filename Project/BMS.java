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

    static final String ExitMsg =       "\nQuitting application...\n\nGoodbye!\n";

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

    // Database
    static DBMS db;

    // --MAIN--
    public static void main(String args[]) {
        System.out.println(WelcomeMsg);     // Print welcome message
        db = new DBMS();                    // Start up our database
        MenuLoop(MainMenu, MainMenuMap);    // Run the menu loop
        System.out.println(ExitMsg);        // Printout an exit message before quitting
    }

    // Function runs the menu loop for the current menu
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
                System.out.println("\n***ERROR*** Please retry using a valid option!\n");
                scanner.next();
            }
        }
    }

    // --TRANSACTIONS--
    private static void AddBook(Long ISBN) {
        Integer newBookID = db.GenerateUniqueID(db.Books());
        Book book = new Book(newBookID, scanner, ISBN);

        db.Insert(db.Books(), book);
    }

    private static void AddBooks() {
        ArrayList<Book> books = db.SelectBooks();

        boolean finished = false;
        while (!finished) {
            System.out.println("\nPlease enter the 13 digit ISBN: ");
            Long ISBN = scanner.nextLong();
            scanner.nextLine();

            if (ISBN <= 999999999999l || ISBN > 9999999999999l) {
                System.out.println("\n***ERROR*** ISBN must be 13 digits!\nPlease try again.");
                continue;
            }


            // Check for duplicate book ISBN entries
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

                        db.UpdateBookByID(book.ID(), qty);
                    }
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
                validName = true;
                db.UpdateCustomerByName(name, scanner);
            }
    
            else {
                System.out.println("\n***ERROR*** Names cannot be NULL.\nPlease try again.\n");
            }
        }
    }

    private static void NewOrders() {
        boolean finishedOrders = false;
        while (!finishedOrders) {
            // Init the data containers for writing (later)
            ArrayList<OrderItem> items = new ArrayList<OrderItem>();
            Customer identifiedCustomer = null;
            Integer orderID = db.GenerateUniqueID(db.Orders());

            // Prompt user for customer
            System.out.println("\nPlease enter the customer name for your order: ");
            String name = scanner.nextLine();

            // Handles unidentified (new) customer
            identifiedCustomer = db.SelectCustomerByName(name);
            if (identifiedCustomer == null) {
                System.out.println("\nNo existing customer by that name.\nCreating new customer...");
                Integer newCustomerID = db.GenerateUniqueID(db.Customers());
                Customer newCustomer = new Customer(newCustomerID, scanner);
                db.Insert(db.Customers(), newCustomer);
            }

            // Loop to allow buying multiple items
            boolean finishedItems = false;
            while (!finishedItems) {
                // Get the ISBN (id) of the book and the amount user wants to order
                System.out.println("\nPlease enter the ISBN of the book to add to your order: \n");
                Long ISBN = scanner.nextLong();
                scanner.nextLine() ;
                System.out.println("\nPlease enter the quantity you'd like to order: \n");
                int qty = scanner.nextInt();
                scanner.nextLine();

                // Lookup the book if its in stock (or exists)
                boolean bookFound = false;
                ArrayList<Book> books = db.SelectBooks();
                for (Book book : books) {
                    if (book.ISBN() == ISBN) {
                        bookFound = true;

                        // Make sure there is enough stock
                        if (book.Qty() >= qty) {
                            OrderItem item = new OrderItem(orderID, book.ID(), qty);
                            items.add(item);
                        }

                        else if (book.Qty() == 0) {
                            System.out.println("\nWARNING: Sorry, we are out of stock for that book.");
                        }

                        else {
                            System.out.println("\nWARNING: Not enough books in stock. Buy remaining stock? (Enter 'Y' to accept): \n");
                            String decision = scanner.nextLine();

                            if (decision.equalsIgnoreCase("Y")) {
                                qty = book.Qty();
                                OrderItem item = new OrderItem(orderID, book.ID(), qty);
                                db.Insert(db.OrderItems(), item);
                                book.UpdateQty(qty * -1);

                                System.out.println("\nRemaining stock added.\n");
                            }
                        }

                        break;
                    }
                }

                if (!bookFound) {
                    System.out.println("\nWARNING: Sorry, we do not have any records for that book.\nYou might need to try another bookstore.");
                }

                // Add another book?
                System.out.println("\nAdd more books to your order? (Enter 'Y' to accept): \n");
                String decision = scanner.nextLine();

                if (decision.toUpperCase() != "Y") {
                    finishedItems = true;
                }
            }

            // Gen the order record and insert
            Order order = new Order(orderID, identifiedCustomer.ID());
            db.Insert(db.Orders(), order);

            // Gen the items records and insert
            for (OrderItem orderItem : items) {
                db.Insert(db.OrderItems(), orderItem);
                db.UpdateBookByID(orderItem.itemID, orderItem.qty * -1);
            }

            // Another order?
            System.out.println("\nOrder submitted. Would you like to add another order? (Enter 'Y' to accept): ");
            String decision = scanner.nextLine();
            if (decision.equalsIgnoreCase("Y")) {
                finishedOrders = true;
            }
        }
    }

    private static void ReverseOrder() {
        System.out.println("\nPlease enter the Order ID to be reversed: ");
        Integer ID = scanner.nextInt();
        scanner.nextLine();

        // This if/else block returns the item stock to the associated record
        ArrayList<OrderItem> items = db.SelectOrderItemsByOrderID(ID);
        if (items.isEmpty()) {
            System.out.println("\nWARNING: Order not found [ReverseOrder FAILED]");
        }

        else {
            for (OrderItem item : items) {
                db.UpdateBookByID(item.itemID, item.qty);
                db.RemoveOrder(ID);
                db.RemoverOrderItemByOrderID(ID);

                System.out.println("\nOrder reversal complete.");
            }
        }
    }
}