package com.company.HomeWork01;
import java.util.Scanner;
public class TestAccount1 {
    public static void main(String[] args)
    {
        Account testAcct;
        Scanner scan = new Scanner(System.in);
        System.out.println("How many accounts would you like to create?");
        int num = scan.nextInt();
        for (int i=1; i<=num; i++)
        {
            testAcct = new Account(100, "Name" + i);
            System.out.println("\nCreated account " + testAcct);
            // Your code here: call method to print out how many accounts have been created so far
        }
        System.out.println("total Accounts ="+ Account.getTotalAccount());

    }
}
