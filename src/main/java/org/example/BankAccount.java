package org.example;

public class BankAccount {
    private String name;
    private double balance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void deposit(double money){
        this.balance += money;
    };
    public void withdraw(double money){
        if(this.balance>money) {
            this.balance -= money;
        } else {
            System.out.print("Not enough money\n");
        }
    };

    public BankAccount(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }
}

