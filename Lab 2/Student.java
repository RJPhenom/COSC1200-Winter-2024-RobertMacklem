//  Author: Robert Macklem
//  Course: COSC1200-01
//  Date:   January 19, 2024
//  Title:  Lab 02
//
//  Descr:  Student class file that accompanies oops_concepts.java
//          as the main file for the project. This file contains the
//          constructor and methods of the Student class obj.

//Declare class
public class Student {
    //Attributes
    private String fName;
    private String lName;
    private int age;

    //Constructor
    public Student(String fName, String lName, int age) {
        if (lName != null) {
            this.fName = fName;
            this.lName = lName;
        }

        else {
            throw new IllegalArgumentException("lName cannot be NULL. A last name is needed.");
        }

        if (0 <= age && age <= 100) {
            this.age = age;
        }

        else {
            throw new IllegalArgumentException("age must be within the range (0-100) inclusive");
        }
    }

    public Student(String fName, String lName) {
        if (lName != null) {
            this.fName = fName;
            this.lName = lName;
        }

        else {
            throw new IllegalArgumentException("lName cannot be NULL. A last name is needed.");
        }

        this.age = 0;
    }

    //Getters
    public String getName() {
        return fName + " " + lName;
    }

    public int getAge() {
        return age;
    }

    //Setters
    public void setName(String fName, String lName) {
        this.fName = fName;
        this.lName = lName;   
    }

    public void setAge(int age) {
        this.age = age;
    }
}

