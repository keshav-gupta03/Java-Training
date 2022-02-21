package java_assessment;

import java.util.ArrayList;
import java.util.Arrays;

public class Question09 {
    public static void main(String[] args) {
        ArrayList<Integer> al= new ArrayList<>();
        al.add(1);
        al.add(2);
        al.add(-9);
        al.add(3);
        al.add(-3);
        System.out.println("Before removing all negative");
        System.out.println(al);

        ArrayList<Integer> ans=removeNegative(al);
        System.out.println("After removing all negative");
        System.out.println(ans);
    }
    static ArrayList<Integer> removeNegative(ArrayList<Integer> al){
        for(int i=0;i<al.size();i++){
            if(al.get(i)<0)
                al.remove(i);
        }
        return al;
    }
}
