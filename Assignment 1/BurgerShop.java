//  Author: Robert Macklem
//  Course: COSC1200-01
//  Date:   January 27, 2024
//  Title:  Assignment 1: Part Two - "Burger Shop"
//  Descr: 
//
//          Text
//
import java.util.Scanner;

public class BurgerShop {
    //MAIN
    public static void main(String[] args) {
        //Instantiate scanner for user input
        Scanner scanner = new Scanner(System.in);

        //Establish consts
        final Float brgrPrice = 5.99f;

        //Prompt user for input using scanner
        //--Customer Name--
        String custName = "";
        while (custName == "") {
            System.out.println("Enter customer name: ");
            custName = scanner.nextLine();
            if (custName == "") {
                System.out.println("\nCustomer name cannot be blank.\nPlease try again.\n");
            }
        }

        //--Burger Quantity--
        //Initialize
        Integer brgrQty = 0;
        //Boolean coupled with while loop to do input validation on data type (needs to be int) for brgrQty
        Boolean isValidBQty = false;
        while (!isValidBQty) {
            try {
                System.out.println("Enter quantity of burgers: ");
                brgrQty = scanner.nextInt();
                isValidBQty = true; //Breaks out of while loop
            } 
            
            catch (Exception e) {
                System.out.println("\n***INVALID INPUT**\nPlease use whole numbers represented in numeric characters only.\n");
                scanner.next(); //Consumes the next line to advance scanner for while loop
            }
        }

        //Process the financial values to be outputte don receipt
        Float totalCost = (brgrQty * brgrPrice);
        Float hstCost = totalCost * 0.13f;
        Float grandTotal = hstCost + totalCost;

        //Output results
        System.out.println("----------Receipt----------");
        System.out.println(String.format("Customer Name: %s", custName));
        System.out.println(String.format("Quantity of Burgers: %d", brgrQty));
        System.out.println(String.format("Price per Burger: %f", brgrPrice));
        System.out.println(String.format("Total Cost: %f", totalCost));
        System.out.println(String.format("HST: %f", hstCost));
        System.out.println(String.format("Grand Total: %f", grandTotal));
        System.out.println("---------------------------");

        //Close scanner to prevent leak
        scanner.close();
    }
}
