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
        //Construct scanner to gather input
        Scanner scanner = new Scanner(System.in);
        
        //Gather user input for the student information
        System.out.println("Please enter the first name of the student:");
        String fName = scanner.nextLine();
        System.out.println("Please enter the last name of the student:");
        String lName = scanner.nextLine();

        // Create an instance of Student using the scanner inputs above
        Student student = new Student(fName, lName);

        // Access the information using getter methods
        System.out.println("Name: " + student.getName());
        System.out.println("Age: " + student.getAge());

        // Modify the student's information using setter methods
        System.out.println("Now please update the age:");
        student.setAge(scanner.nextInt());

        // Print the updated information
        System.out.println("Student Name: " + student.getName());
        System.out.println("Student Age: " + student.getAge());

        //Close the scanner (prevent memleak)
        scanner.close();
    }
}