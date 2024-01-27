//  Author: Robert Macklem
//  Course: COSC1200-01
//  Date:   January 27, 2024
//  Title:  Assignment 1: Part One - "Person"
//  Descr: 
//
//          Text
//

public class Person {
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

        //TODO: functionality

        //Returns the completed message
        return returnMessage;
    }
}