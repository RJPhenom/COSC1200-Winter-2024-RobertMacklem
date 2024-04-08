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
import java.time.LocalDate;
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
        db.Out();                           // Write to files
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
            }
        }
    }

    // --TRANSACTIONS--
    private static void AddBook(Long ISBN) {
        Integer newBookID = db.GenerateUniqueID(db.Books());
        Book book = newBook(newBookID, ISBN);
        db.InsertBook(book);
    }

    private static void AddBooks() {
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
            for (Book book : db.SelectBooks()) {
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
            ArrayList<OrderItem> itemsForOrder = new ArrayList<OrderItem>();
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
                Customer newCustomer = newCustomer(newCustomerID);

                try {
                    db.InsertCustomer(newCustomer);
                }

                catch (Exception exception) {
                    exception.printStackTrace();
                }
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
                            itemsForOrder.add(item);
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
                                itemsForOrder.add(item);
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

                if (decision.strip().equalsIgnoreCase("Y")) {
                    System.out.println("\nAdding another book...");
                }

                else{
                    finishedItems = true;
                }
            }

            try {
                // Gen the order record and insert
                Order order = new Order(orderID, identifiedCustomer.ID(), LocalDate.now());
                db.InsertOrder(order);

                // Gen the items records and insert
                for (OrderItem orderItem : itemsForOrder) {
                    db.InsertOrderItem(orderItem);
                }

                System.out.println("\nOrder submission successful.");
            }

            catch (Exception exception) {
                System.out.println("\nWARNING: Order submission failed! [NewOrder FAILED]");
                exception.printStackTrace();
            }

            // Another order?
            System.out.println("\nWould you like to add another order? (Enter 'Y' to accept): ");
            String decision = scanner.nextLine();
            if (!decision.strip().equalsIgnoreCase("Y")) {
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

    // Input Construcotrs (cannot use scanners for serialized objs)
    private static Customer newCustomer(Integer ID){
        Integer customerID = ID;
        String customerName = "";
        String customerAddr = "";
        String customerEmail = "";
        Integer customerPhone = 0;

        // Set name via input
        boolean validName = false;
        while (!validName) {
            System.out.println("\nPlease enter customer name: ");
            String input = scanner.nextLine();

            if (input != "") {
                customerName = input;
                validName = true;
            }

            else {
                System.out.println("\n***ERROR*** Name field cannot be NULL.\nPlease try again.");
            }
        }

        // Set addr via input
        boolean validAddr = false;
        while (!validAddr) {
            System.out.println("\nPlease enter customer address (NULL accepted): ");
            String input = scanner.nextLine();
            customerAddr = input;
            validAddr = true;  // While loop exists for future validation implementations, right now no validation runs on addr
        }

        // Set email via input
        boolean validEmail = false;
        while (!validEmail) {
            System.out.println("\nPlease enter customer email (NULL accepted): ");
            String input = scanner.nextLine();

            if (input == "" || input.contains("@")) {
                customerEmail = input;
                validEmail = true;
            }

            else {
                System.out.println("\n***ERROR*** Emails must contain a '@'.\nIf you do not have an email, please enter NULL.");
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
                    customerPhone = input;
                    validPhone = true;
                }
                
                
                else {
                    System.out.println("\n***ERROR*** Phone # must be at least 10 digits.\nPlease try again.");
                }
            }

            catch (Exception exception) {
                System.out.println("\n***ERROR*** Phone # must be entered using DIGITS only. (EXAMPLE: 18005551234)\nPlease try again.");
            }
        }

        Customer customer = new Customer(customerID, customerName, customerAddr, customerEmail, customerPhone);

        // For debugging
        System.out.println("\n\n************CREATED NEW CUSTOMER**************");
        customer.SelfReport();
        System.out.println(     "*********************************************");

        return customer;
    }
    private static Book newBook(Integer ID, Long ISBN) {
        Integer bookID = ID;
        Long bookISBN = ISBN;
        String bookTitle = "";
        String bookAuthor = "";
        String bookPublisher = "";
        Float bookPrice = 0.0f;
        Integer bookQty = 0;

        // Set Title via input
        boolean validTitle = false;
        while (!validTitle) {
            System.out.println("\nPlease enter book title: ");
            String input = scanner.nextLine();

            if (input != "") {
                bookTitle = input;
                validTitle = true;
            }

            else {
                System.out.println("\n***ERROR*** Title cannot be NULL.\nPlease try again.");
            }
        }

        // Set Author via input
        boolean validAuthor = false;
        while (!validAuthor) {
            System.out.println("\nPlease enter book author: ");
            String input = scanner.nextLine();

            if (input != "") {
                bookAuthor = input;
                validAuthor = true;
            }

            else {
                System.out.println("\n***ERROR*** Author cannot be NULL.\nPlease try again.");
            }
        }

        // Set Publisher via input
        boolean validPublisher = false;
        while (!validPublisher) {
            System.out.println("\nPlease enter book publisher (NULL accepted): ");
            String input = scanner.nextLine();
            bookPublisher = input;
            validPublisher = true;
        }

        // Set book price
        boolean validPrice = false;
        while (!validPrice) {
            System.out.println("\nPlease enter book price (Enter $0.00 if not applicable): ");
            String input = scanner.nextLine();

            try {
                Float floatInput = Float.parseFloat(input);
                bookPrice = floatInput;
                validPrice = true;
            }

            catch (Exception exception) {
                System.out.println("\n***ERROR*** Input was not a valid price amount. Please use DIGITS only, " +
                "specifying cents with a decimal. (EXAMPLE: 0.50)\nPlease try again");
            }
        }

        // Set book qty
        boolean validQty = false;
        while (!validQty) {
            System.out.println("\nPlease enter the quantity of these books to add to stock: ");
            try {
                Integer input = scanner.nextInt();
                scanner.nextLine();

                if (input > 0) {
                    bookQty = input;
                    validQty = true;
                }

                else {
                    System.out.println("\n***ERROR*** Book quantity must be greater than zero.\nPlease try again.");
                }
            }

            catch (Exception exception) {
                System.out.println("\n***ERROR*** Please use a valid numeric input for book quantity.");
            }
        }

        Book book = new Book(bookID, bookISBN, bookTitle, bookAuthor, bookPublisher, bookPrice, bookQty);

        // For debugging
        System.out.println("\n\n************CREATED NEW BOOK**************");
        book.SelfReport();
        System.out.println(    "******************************************");

        return book;
    }
}