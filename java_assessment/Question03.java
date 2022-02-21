package java_assessment;

import java.util.Scanner;

public class Question03 {
    static int n1=0,n2=1,n3=0;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the size of fibonaaci series");
        int n=sc.nextInt();
        int a=0,b=1,c;
        System.out.print(b);
        for(int i=2;i<=n;i++)
        {
            c=a+b;
            System.out.print(" "+c);
            a=b;
            b=c;
        }
        System.out.println();
        System.out.println("-----------------------");
        System.out.print("1");
        printFibonacci(n);
    }
    static void printFibonacci(int count){
        if(count>0){
            n3 = n1 + n2;
            n1 = n2;
            n2 = n3;
            System.out.print(" "+n3);
            printFibonacci(count-1);
        }
    }
}
