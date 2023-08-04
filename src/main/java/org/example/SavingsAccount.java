package org.example;

public class SavingsAccount extends BankAccount {

    public SavingsAccount(String name, double balance) {
        super(name, balance, "Savings");
    }

    @Override
    public void withdraw(double money) {
        if(getBalance()-money<=100) {
            System.out.print("Not enough balance. 100 INR required");
        } else {
            super.withdraw(money);
        }
    }

}
