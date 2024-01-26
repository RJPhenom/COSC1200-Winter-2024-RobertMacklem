//  Author: Robert Macklem
//  Course: COSC1200-01
//  Date:   January 19, 2024
//  Title:  Lab 02
//
//  Descr:  Main class for lab 2, which is about exploring some OOP
//          concepts, primarily polymorphism.
import java.util.Scanner;

public class oops_concepts {
    public static void main(String[] args) {
        // Create an instance of Student
        Student student = new Student("John", "Smith", 20);

        // Access the methods
        System.out.println("Name: " + student.getName());
        System.out.println("Age: " + student.getAge());

        // Modify the student's information
        Student student2 = new Student("Melissa", "Jones");

        // Print the updated information
        System.out.println("Student 2 Name: " + student2.getName());
        System.out.println("Student 2 Age: " + student2.getAge());
    }
}