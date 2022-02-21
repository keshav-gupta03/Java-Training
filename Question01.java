package java_assessment;

import java.util.Stack;

public class Question01 {
    public static void main(String[] args) {
        String s="(){}[]]";
        if(validParentheses(s))
            System.out.println("true");
        else
            System.out.println("false");
    }

    static boolean  validParentheses(String str)
    {
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < str.length(); i++)
        {
            char x = str.charAt(i);

            if (x == '(' || x == '[' || x == '{')
            {
                stack.push(x);
                continue;
            }
            if (stack.isEmpty())
                return false;
            else if(x==')'){
                char cc=stack.pop();
                if (cc == '{' || cc == '[')
                    return false;
            }
            else if(x=='}'){
                char cc=stack.pop();
                if (cc == '(' || cc == '[')
                    return false;
            }
            else if(x==']'){
                char cc=stack.pop();
                if (cc == '{' || cc == '(')
                    return false;
            }
        }
        return (stack.isEmpty());
    }

}
