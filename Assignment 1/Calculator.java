//  Author: Robert Macklem
//  Course: COSC1200-01
//  Date:   January 27, 2024
//  Title:  Assignment 1: Part Three - "Calculator"
//  Descr: 
//
//          Text
//
import java.util.Scanner;

public class Calculator {
    //MAIN
    public static void main(String[] args) {
        //Instantiat ethe scanner for user input
        Scanner scanner = new Scanner(System.in);

        //Get the two numbers from user input
        //--First Num--
        System.out.println("Enter the first number: ");
        Double num1 = scanner.nextDouble();

        //--Second Num--
        System.out.println("Enter the second number: ");
        Double num2 = scanner.nextDouble();

        //Perform Math operations
        Double sum = num1 + num2;
        Double diff = num1 - num2;
        Double prod = num1 * num2;
        Double quot = num1 / num2;
        Double mod = num1 % num2;

        //Print results
        System.out.println(String.format("Sum: %f", sum));
        System.out.println(String.format("Difference: %f", diff));
        System.out.println(String.format("Product: %f", prod));
        System.out.println(String.format("Quotient: %f", quot));
        System.out.println(String.format("Remainder: %f", mod));

        //Close scanner to prevent leak
        scanner.close();
    }
}
