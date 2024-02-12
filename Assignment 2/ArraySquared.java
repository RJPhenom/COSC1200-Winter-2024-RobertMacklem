import java.util.Scanner;
import java.util.Arrays;

public class ArraySquared {
    public class arrayExerciseThree {
        public static void main(String args[]) {
            //Scanner for input
            Scanner scanner = new Scanner(System.in);
            
            //Grab size
            System.out.println("Please enter the size of your array: ");
            int N = scanner.nextInt();
            int[] intArr = new int[N];
    
            //Grab vals
            for (int i = 0; i < N; i++) {
                System.out.println("Enter an integer: ");
                intArr[i] = scanner.nextInt();
            }
    
            //Sqr w/ func
            int[] intArrSqrd = arrSquared(intArr);
            Arrays.sort(intArrSqrd);
    
            //Print results
            System.out.println("Your sorted and squared array is: ");
            for (int x : intArrSqrd) {
                System.out.println(x);
            }

            //Close scanner
            scanner.close();
        }
    
        //Func for sqr arr vals (returns new arr)
        private static int[] arrSquared(int[] arr) {
            int[] retArr = new int[arr.length];
    
            for (int i = 0; i < arr.length; i++) {
                retArr[i] = arr[i]*arr[i];
            }
    
            return retArr;
        }
    }
    }