//  Author: Robert Macklem
//  Course: COSC1200-01
//  Date:   January 27, 2024
//  Title:  Assignment 1: Part One - "Person"
//  Descr: 
//
//          Text
//
import java.util.Arrays;
import java.util.Scanner;

public class Person {
    //MAIN:
    //In MAIN we want to get user input to pass as params for an instance of the Person class
    public static void main(String[] args) {
        //Instantiate input scanner
        Scanner scanner = new Scanner(System.in);

        //***INPUT SECTION***

        //--Name--
        //Initialize
        String myName = ""; //Initialize

        //While loop for validation that name is not blank
        while (myName == "") {
            System.out.println("Enter your name: ");
            myName = scanner.nextLine(); //Get input
            if (myName == "") { //if blank (prints below and loops):
                System.out.println("\nName cannot be blank.\nPlease try again.\n");
            }
        }

        //--Age--
        //Initialize
        Integer myAge = 0;
        
        //While loop to ensure correct data type inputted
        while (myAge == 0) {
            System.out.println("Enter your age: ");
            try {
                myAge = scanner.nextInt();
            } 
            
            catch (Exception e) { //if invalid input, prints below and loops
                System.out.println("\n***INPUT ERROR***\nPlease use whole numbers represented in numeric characters only.\n");
                scanner.next(); //Consumes the \n and progresses the scanner (Source: https://stackoverflow.com/questions/23450524/java-scanner-doesnt-wait-for-user-input)
            }
        }


        //--Gender
        //Setup constants
        final String[] genders = {"M", "F", "X"};
        String errMsg = "\n***INPUT ERROR***\nPlease use a single M, F, or X character to represent your gender (X stands for other).\n";

        //Initialize
        String myGenderStr = "";

        //While loop to get appropriate char to represent gender (male, female, other/nb)
        while (myGenderStr == "") {
            System.out.println("Enter your gender (M, F, or X): ");
            myGenderStr = scanner.nextLine().toUpperCase();

            //conditional check that does the actual validation
            if (myGenderStr.length() != 1) { //if they used multiple chars, they fail
                System.out.println(errMsg);
            }
            
            else if (!Arrays.asList(genders).contains(myGenderStr)) { 
                System.out.println(errMsg);
            }


        }

        //Set gender from above to Char
        Character myGender = myGenderStr.charAt(0);

        //***OUTPUT SECTION***

        //--Instantiate a Person
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