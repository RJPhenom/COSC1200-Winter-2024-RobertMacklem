import java.util.Scanner;
import java.util.Arrays;

public class ArraySquared {
    public class arrayExerciseThree {
        public static void main(String args[]) {
            Scanner scanner = new Scanner(System.in);
            
            System.out.println("Please enter the size of your array: ");
            int N = scanner.nextInt();
            int[] intArr = new int[N];
    
            for (int i = 0; i < N; i++) {
                System.out.println("Enter an integer: ");
                intArr[i] = scanner.nextInt();
            }
    
            int[] intArrSqrd = arrSquared(intArr);
            Arrays.sort(intArrSqrd);
    
            System.out.println("Your sorted and squared array is: ");
            for (int x : intArrSqrd) {
                System.out.println(x);
            }

            scanner.close();
        }
    
        private static int[] arrSquared(int[] arr) {
            int[] retArr = new int[arr.length];
    
            for (int i = 0; i < arr.length; i++) {
                retArr[i] = arr[i]*arr[i];
            }
    
            return retArr;
        }
    }
    }