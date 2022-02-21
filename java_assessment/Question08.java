package java_assessment;

import java.util.Arrays;

public class Question08 {
    public static void main(String[] args) {
        String s1="abcd";
        String s2="bcads";
        if(isAnagram(s1,s2))
            System.out.println("The given strings are anagram");
        else
            System.out.println("not Anagram");
    }
    static boolean isAnagram(String s1,String s2){
        if(s1.length()!=s2.length())
            return false;

        char[] c1=s1.toCharArray();
        char[] c2=s2.toCharArray();
        Arrays.sort(c1);
        Arrays.sort(c2);
        for(int i=0;i<c1.length;i++){
            if(c1[i]!=c2[i])
                return false;
        }
        return true;
    }
}
