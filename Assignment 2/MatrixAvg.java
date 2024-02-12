public class MatrixAvg {
    //Globals
    private static int highestMaxAvg = 0;
    private static int rowWithHighestMaxAvg = 0;

    public static void main(String args[]) {
        //Init arr
        int[][] arr = {{45,20, 40}, {30,40,60}, {34,67,78}};
        //Avg it
        avgArr(arr);

        System.out.println("The highest average is " + highestMaxAvg); //print highest avg
        System.out.println("It was in row " + (rowWithHighestMaxAvg + 1)); //+1 for arr index counting
    }

    private static void avgArr(int[][] arr) {
        //Init arr that tracks avgs
        int[] retArr = new int[arr.length];

        //Loop through rows
        for (int i = 0; i < arr.length; i++) {
            //Loop through cols
            for (int x : arr[i]) {
                //Sum
                retArr[i] += x;
            }

            //Avg
            retArr[i] = retArr[i] / arr[i].length;

            //Set global vars
            if (retArr[i] > highestMaxAvg) {
                highestMaxAvg = retArr[i];
                rowWithHighestMaxAvg = i;
            }
        }
    }
}
