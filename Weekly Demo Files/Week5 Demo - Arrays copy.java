class ArrayBasics {
    public static void main(String[] args) {
        int[] A1 = new int[5];
        String[] A2 = new String[5];

        int[] A1_defined = new int[] {1,2,3,4,5};
        String[] A2_defined = new String[] {"a", "b", "c", "d", "e"};

        System.out.println(A1); //prints memory addres
        System.out.println(A2); //prints memory addres

        System.out.println(A1_defined); //still prints memory address
        System.out.println(A2_defined); //still prints memory address

        //For loop
        for (int i = 0; i < A1.length; i++) {
            System.out.println(A1[i]);
        }

        for (int i = 0; i < A2.length; i++) {
            System.out.println(A2[i]);
        }

        for (int i = 0; i < A1_defined.length; i++) {
            System.out.println(A1_defined[i]);
        }

        for (int i = 0; i < A2_defined.length; i++) {
            System.out.println(A2_defined[i]);
        }

        //Foreach loop
        for (int i: A1) {
            System.out.println(i);
        }

        for (int i: A1_defined) {
            System.out.println(i);
        }
    }
}
