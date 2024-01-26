//  Author: Robert Macklem
//  Course: COSC1200-01
//  Date:   January 19, 2024
//  Title:  Lab 02
//
//  Descr:  Main class for lab 2, which is about exploring some OOP
//          concepts, primarily polymorphism.

import java.util.Scanner;

public class oops_concepts {
    //MAIN
    public static void main(String[] args) {
        //Construct scanner to gather input and prep instance vars
        Scanner scanner = new Scanner(System.in);
        Student student = null;
        
        Boolean lNameValid = false;
        while (!lNameValid) {
            try {
                //Gather user input for the student information
                System.out.println("Please enter the first name of the student:");
                String fName = scanner.nextLine();
                System.out.println("Please enter the last name of the student:");
                String lName = scanner.nextLine();

                //Fill our student instance using the scanner inputs above
                student = new Student(fName, lName);
                lNameValid = true;
            } 
            
            catch (Exception e) {
                //Handle exception on invalid input
                System.out.println("\nINVALID INPUT: Last name was NULL. \nPlease enter a last name.\n");
            }
        }

        //Access the information using getter methods
        System.out.println("Name: " + student.getName());
        System.out.println("Age: " + student.getAge());

        //Set the age based on user input, using while loop and boolean to test for
        //input validation.
        Boolean ageValid = false;
        while (!ageValid) {
            try {
                //Modify the student's information using setter methods
                System.out.println("Now please update the age:");
                student.setAge(scanner.nextInt());
                ageValid = true;
            } 
            
            catch (Exception e) {
                //Handle exception on invalid input
                System.out.println("\nINVALID INPUT: Age was out of range. \nPlease enter an age within the range 0-100.\n");        
            }
        }

        //Print the updated information
        System.out.println("Student Name: " + student.getName());
        System.out.println("Student Age: " + student.getAge());

        //Close the scanner (prevent memleak)
        scanner.close();
    }
}