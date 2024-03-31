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
import java.util.HashMap;

public class BMS {
    //CONSTS
    //Messages & Strings
    static final String WELCOMEMSG = "\n*************************************************\n" +
                                  "Welcome to Bookstore Management System (BMS) 1.0!" +
                                  "\n*************************************************\n" +
                                  "\nHow can we help your bookstore operations today?\n";

    static final String MENUMSG = "1. Transact\n" +
                               "2. Report\n" +
                               "3. Quit\n";

    static final String ERRORMSG = "\n***ERROR*** Please retry using a valid input!\n";

    //Hashmaps
    static final HashMap<Integer, Runnable> MENUMAP = new HashMap<Integer, Runnable>() {{
        put(1, () -> TransactMenu()); //lambda lets us run submenus out of a dict
    }};

    //VARS
    static Scanner scanner = new Scanner(System.in);

    public static void main(String args[]) {
        System.out.println(WELCOMEMSG);

        boolean inMainMenu = true;
        while(inMainMenu) {
            System.out.println(MENUMSG);
            try { 
                int mode = scanner.nextInt();
                if (mode == 1 || mode == 2 || mode == 3) {
                    MENUMAP.get(mode).run(); //runs selected mode via hashmap
                    inMainMenu = false;
                }

                else {
                    System.out.println("\nOption not found.\n" +
                                       "Please enter 1, 2 or 3 to select an option.\n");
                }
            }

            catch (Exception exception) {
                System.out.println(ERRORMSG);
                scanner.next();
            }

        }

    }

    private static int TransactMenu() {
        //Declare return var
        int mode = 0;

        boolean inTransactMenu = true;
        while (inTransactMenu) {
            System.out.println("In transact menu!");
            try{
                mode = scanner.nextInt();
                inTransactMenu = false;
            }

            catch (Exception exception) {
                System.out.println(ERRORMSG);
                scanner.next();
            }
        }

        return mode;
    }
}
