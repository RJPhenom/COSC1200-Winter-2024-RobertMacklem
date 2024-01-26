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
        //NULL check on last name
        if (lName != null && lName != "") {
            this.fName = fName;
            this.lName = lName;
        }

        //Throw exception is NULL check failed
        else {
            throw new IllegalArgumentException("lName cannot be NULL. A last name is needed.");
        }

        //Validated age is within acceptable age range
        if (0 <= age && age <= 100) {
            this.age = age;
        }

        //Throw exception if age is out of range
        else {
            throw new IllegalArgumentException("age must be within the range (0-100) inclusive");
        }
    }

    public Student(String fName, String lName) {
        //Overload for the main constructor where age is not passed and is instead assigned
        //a default value of 0, to be set later. Uses the same NULL check on last name.
        if (lName != null && lName != "") {
            this.fName = fName;
            this.lName = lName;
        }

        else {
            throw new IllegalArgumentException("lName cannot be NULL. A last name is needed.");
        }

        //Age defaults to 0 when not given
        //*************************************************************************************
        //LAB 2 QUESTION: this is know as method OVERLOADING, which is when two or more methods
        //have the same name but different signatures. This helps the readability of code, as
        //in our oops_concepts.java file we can see we are still constructing the class in this
        //method, but we are not calling the full constructor which makes our lives easier.
        //*************************************************************************************
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
        //Setter uses the same NULL check on last name
        if (lName != null && lName != "") {
            this.fName = fName;
            this.lName = lName;
        }

        else {
            throw new IllegalArgumentException("lName cannot be NULL. A last name is needed.");
        }
    }

    public void setAge(int age) {
        //Setter uses the same age range validation
        if (0 <= age && age <= 100) {
            this.age = age;
        }

        //Throw exception if age is out of range
        else {
            throw new IllegalArgumentException("age must be within the range (0-100) inclusive");
        }
    }
}

