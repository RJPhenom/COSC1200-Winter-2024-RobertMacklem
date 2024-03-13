public class JavaStrings {
    public static void main(String args[]) {
        String exercise = "Java is awesome!";

        // 1.Question: What is the character at index 5 in the string "Java is awesome!"? (Use the charAt() method)
        System.out.println(exercise.charAt(5));

        // 2.Question: How many characters are there in the string "Java is awesome!"? (Use the length() method)
        System.out.println(exercise.length());

        // 3.Question: What is the substring starting from index 5 in the string "Java is awesome!"? (Use the substring() method:
        System.out.println(exercise.substring(5));

        // 4.Question: At which index does the substring "is" first occur in the string "Java is awesome!"?( Use the indexOf() method:
        System.out.println(exercise.indexOf("is"));

        // 5.Question: Convert the string "Java is awesome!" to uppercase. (Use the toUpperCase() method)
        System.out.println(exercise.toUpperCase());

        // 6.Question: Convert the string "Java is awesome!" to lowercase. (Use the toLowerCase() method)
        System.out.println(exercise.toLowerCase());

        // 7.Question: Replace the word "awesome" with "amazing" in the string "Java is awesome!". (Use the replace() method)
        System.out.println(exercise.replace("awesome", "amazing"));

    }
}
