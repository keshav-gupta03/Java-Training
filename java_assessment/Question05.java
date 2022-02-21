package java_assessment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question05 {
    public static void main(String[] args) {
        ArrayList<Integer> al=new ArrayList<>();
        al.add(1);
        al.add(2);
        al.add(3);
        al.add(4);
        al.add(5);
        al.add(6);
        List<Integer> ans=getTopKMax(al,3);
        System.out.println(ans);
    }

    static List<Integer> getTopKMax(List<Integer> list, int k){
        Collections.sort(list);
        ArrayList<Integer> ans=new ArrayList<>();
        for(int i= list.size()-k;i<list.size();i++ ){
            ans.add(list.get(i));
        }
        return ans;
    }
}
