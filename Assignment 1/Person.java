//  Author: Robert Macklem
//  Course: COSC1200-01
//  Date:   January 27, 2024
//  Title:  Assignment 1: Part One - "Person"
//  Descr: 
//
//          Text
//
import java.util.Scanner;

public class Person {
    //MAIN
    public static void main(String[] args) {
        //Instantiate input scanner
        Scanner scanner = new Scanner(System.in);

        //Print request for user input and store it as vars to
        //build a Person instance later.
        //--Name--
        System.out.println("Enter your name: ");
        String myName = scanner.nextLine();

        //--Age--
        System.out.println("Enter your age: ");
        Integer myAge = scanner.nextInt();

        //Consumes the \n left after nextInt(), which caused the next nextLine() to break 
        //(Source: https://stackoverflow.com/questions/23450524/java-scanner-doesnt-wait-for-user-input)
        scanner.nextLine(); 

        //--Gender
        System.out.println("Enter your gender (M, F, or X): ");
        String myGenderStr = scanner.nextLine();
        Character myGender = myGenderStr.charAt(0);

        //Instantiate a Person using the inputs
        Person myPerson = new Person(myName, myAge, myGender);

        //Output results using introduce method
        System.out.println(myPerson.introduce());

        //Close scanner
        scanner.close();
    }

    //Attributes
    private String name;
    private Integer age;
    private Character gender;

    //Constructor
    public Person(String name, Integer age, Character gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    //Getters
    public String getName() {
        return this.name;
    }

    public Integer getAge() {
        return this.age;
    }
    
    public Character getGender() {
        return this.gender;
    }

    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    //The introduce method works to return a formatted output
    //of the information for the person instance.
    public String introduce() {
        //Prep return string var
        String returnMessage = "";

        //Adds each line of ouput to the return string using the format method 
        //to return the value of the field in the given instance.
        returnMessage += String.format("Name: %s\n", this.name);
        returnMessage += String.format("Age: %d\n", this.age);
        returnMessage += String.format("Gender: %c\n", gender);

        //Returns the completed message
        return returnMessage;
    }
}