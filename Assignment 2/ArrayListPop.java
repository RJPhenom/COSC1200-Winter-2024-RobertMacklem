import java.util.ArrayList;

public class ArrayListPop {
    public static void main(String args[]) {
        //Init arrlist
        ArrayList<Integer> arrList = new ArrayList<Integer>();

        //Build arr s.t.:
        // {20, 30, 25, 35, -16, 60, -100}
        arrList.add(20);
        arrList.add(30);
        arrList.add(25);
        arrList.add(35);
        arrList.add(-16);
        arrList.add(60);
        arrList.add(-100);

        //Add 80
        arrList.add(80);

        //Remove -16 by val
        arrList.remove(Integer.valueOf(-16));

        for (int x : arrList) {
            System.out.println(x);
        }
    }
}
