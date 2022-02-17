package com.company.HomeWork01;

public class Account {
    private double balance;
    private String name;
    private long acctNum;
    private static int totalAccount=0;

    //----------------------------------------------
    //Constructor -- initializes balance and owner; generates random
    //account number
    //----------------------------------------------
    public Account(double initBal, String owner)
    {
        balance = initBal;
        name = owner;
        acctNum = (int) (Math.random() * Integer.MAX_VALUE);
        totalAccount++;
    }


    //----------------------------------------------
    //Constructor -- initializes owner as given and balance to 0.
    //Generates random account number
    //----------------------------------------------
    public Account(String owner)
    {
        balance = 0;
        name = owner;
        acctNum = (int) (Math.random() * Integer.MAX_VALUE);
        totalAccount++;
    }

    //----------------------------------------------
    // Checks to see if balance is sufficient for withdrawal.
    // If so, decrements balance by amount; if not, prints message.
    //----------------------------------------------
    public void withdraw(double amount)
    {
        if (balance >= amount)
            balance -= amount;
        else
            System.out.println("Insufficient funds");

    }

    //----------------------------------------------
    // Checks to see if balance is sufficient for withdrawal.
    // If so, decrements balance by amount; if not, prints message.
    // Also deducts fee from account.
    //----------------------------------------------
    public void withdraw(double amount, double fee)
    {
        if (balance >= amount)
        {
            balance -= amount;
            balance -= fee;
        }
        else
            System.out.println("Insufficient funds");

    }

    //----------------------------------------------
    // Adds deposit amount to balance.
    //----------------------------------------------
    public void deposit(double amount)
    {
        balance += amount;
    }

    //----------------------------------------------
    // Returns balance.
    //----------------------------------------------
    public double getBalance()
    {
        return balance;
    }

    //----------------------------------------------
    // Returns account number
    //----------------------------------------------
    public double getAcctNumber()
    {
        return acctNum;
    }

//    Return name
    public String getName(){return name;}

//    Return total Accounts
    public static int getTotalAccount(){
        return totalAccount;
    }

//    Close Account

    public static void CloseAccount(Account obj){
        obj.name+=obj.getName()+"CLOSED";
        obj.balance=0;
        totalAccount--;
    }


    public static Account consolidate(Account acc1,Account acc2){
        if(acc1.name.equalsIgnoreCase(acc2.name) && acc1.getAcctNumber()!=acc2.getAcctNumber()){
            double balance=acc1.balance+acc2.balance;
            return new Account(balance,acc1.name);
        }
        else{
            System.out.println("We can not create a new Account");
            return null;
        }


    }
    //----------------------------------------------
    // Returns a string containing the name, acct number, and balance.
    //----------------------------------------------
    public String toString()
    {
        return "Name: " + name +
                "\nAcct #: " + acctNum +
                "\nBalance: " + balance;
    }
}
