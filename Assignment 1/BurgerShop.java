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
        System.out.println("Enter customer name: ");
        String custName = scanner.nextLine();

        //--Burger Quantity--
        System.out.println("Enter quantity of burgers: ");
        Integer brgrQty = scanner.nextInt();

        //Cast to float
        Float brgrQtyFloat = brgrQty.floatValue();

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
