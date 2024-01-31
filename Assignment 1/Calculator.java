//  Author: Robert Macklem
//  Course: COSC1200-01
//  Date:   January 27, 2024
//  Title:  Assignment 1: Part Three - "Calculator"
//  Descr: 
//
//          Uses a function called getNum() to get two numbers from user input
//          and returns the mathematical sum, difference, product, quotient, and
//          remainder of those two numbers.
//
import java.util.Scanner;

public class Calculator {
    //MAIN
    //In MAIN we handle the step by step processing, outsourcing only the input intake and validation to getNum()
    public static void main(String[] args) {
        //Instantiat ethe scanner for user input
        Scanner scanner = new Scanner(System.in);

        //Get the two numbers from user input

        //--First Num--
        System.out.println("\nEnter the first number: ");
        Double num1 = getNum(scanner); //See func below

        //--Second Num--
        System.out.println("\nEnter the second number: ");
        Double num2 = getNum(scanner); //See func below

        //Perform Math operations
        Double sum = num1 + num2;
        Double diff = num1 - num2;
        Double prod = num1 * num2;

        //Handling division by zero errors
        String quotStr;
        String modStr;
        if(num2 != 0) {
            Double quot = num1 / num2;
            Double mod = num1 % num2;

            quotStr = quot.toString();
            modStr = mod.toString();
        }

        else {
            quotStr = "Undefined";
            modStr = "Undefined";
        }


        //Print results
        System.out.println(String.format("Sum: %f", sum));
        System.out.println(String.format("Difference: %f", diff));
        System.out.println(String.format("Product: %f", prod));
        System.out.println(String.format("Quotient: %s", quotStr));
        System.out.println(String.format("Remainder: %s", modStr));

        //Close scanner to prevent leak
        scanner.close();
    }

    private static Double getNum(Scanner scanner) {
        //Initialize return value
        Double retNum = 0.0;

        //Boolean coupled with while loop to ensure validation of the data type entered is double
        Boolean isValid = false;
        while (!isValid) {
            try {
                retNum = scanner.nextDouble();
                isValid = true; //Breaks while loop once input is correct
            }

            //Catch if input is invalid data type
            catch (Exception e) {
                System.out.println("\nInvalid entry, please input numeric characters only.\nRe-enter your number: ");
                scanner.next(); //Need to progress the scanner since the nextDouble() failed
            }
        }

        //Return the number
        return retNum;
    }
}
