//  Author: Robert Macklem
//  Course: COSC1200-01
//  Date:   January 19, 2024
//  Title:  Lab 01
import java.util.Scanner;

//Declare class for program
class Lab01 {

    //Main function
    //Prompts user for name and age and prints them a welcome 
    //message using their input.
    public static void main(String[] args) {

        //Construct scanner to gather input
        Scanner scanner = new Scanner(System.in);

        //Prompt and store name input as String
        System.out.println("Enter your name: ");
        String name = scanner.nextLine();

        //Prompt and store age input as Int
        System.out.println("Enter your age: ");
        Integer age = scanner.nextInt();

        //Switch output based on age range
        if (age < 25) {
            System.out.println("Hello " + name + ", youth are the future!");
        }

        else if (age >= 25 && age < 45) {
            System.out.println("Hello " + name + ", you are like me! I'm also older than these kids!");
        }

        else if (age >= 45 && age < 60) {
            System.out.println("Hello " + name + ", it's never too late to learn!");
        }

        else {
            System.out.println("Hello " + name + ", technology should always respect the elderly!");
        }

        //Store age and retirement age for bonus calculation
        final Integer retirementAge = 65;
        Integer yearsToRetirement = retirementAge - age;

        //Bonus calculation
        //Output retirement waiting period, iff they're under the retirement age
        if (yearsToRetirement > 0) {
            System.out.println("You have " + Integer.toString(yearsToRetirement) + " year to retirement");
        }

        //Otherwise, output meme text
        else {
            System.out.println("Ok boomer.");
        }

        scanner.close();
    }
}