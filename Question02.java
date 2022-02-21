package java_assessment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Question02 {
    public static void main(String[] args) {
        ArrayList<Integer> al= new ArrayList<>();
        al.add(1);
        al.add(2);
        al.add(3);
        al.add(4);
        al.add(5);
        al.add(6);
        al.add(7);
        int sum=12;
        if(check(al,sum))
            System.out.println("true");
        else
            System.out.println("false");
    }
    static boolean check(List<Integer> al , int sum){
        for(int i=0;i<al.size();i++){
            int a=al.get(i);
            for(int j=i+1;j<al.size();j++){
                int b=al.get(j);
                if(a+b==sum)
                    return true;
            }
        }
        return false;
    }
}
