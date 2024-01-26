public class Student {
    private String fName;
    private String lName;
    private int age;

    public Student(String fName, String lName, int age) {
        if (lName != null) {
            this.fName = fName;
            this.lName = lName;
        }

        else {
            throw new IllegalArgumentException("lName cannot be NULL.");
        }

        this.age = age;
    }

    public String getName() {
        return fName + " " + lName;
    }

    public void setName(String fName, String lName) {
        this.fName = fName;
        this.lName = lName;   
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

