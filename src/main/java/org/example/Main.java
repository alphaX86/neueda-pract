package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String name, option, option2;
        double balance;
        System.out.println("Choose an option:  (a)Add Account (l)Display Accounts (s)Save to database  (d) Deposit funds (w)Withdraw funds (q)Quit ");
        option = sc.next();
        List<BankAccount> ba = new ArrayList<BankAccount>();
        List<SavingsAccount> sa = new ArrayList<SavingsAccount>();
        int c1 = 0, c2 = 0;
        while(!option.equals("q")){
            switch(option){
                case "a":
                    System.out.print("Enter name:");
                    name = sc.next();
                    System.out.print("Type of account? (a)Bank Account (b)Savings\n");
                    option2 = sc.next();
                    System.out.print("Enter amount to deposit initially:");
                    balance = sc.nextDouble();
                    switch(option2) {
                        case "a":
                            BankAccount x = new BankAccount(name, balance);
                            ba.add(c1, x);
                            System.out.println("Account added!");
                            c1++;
                            break;
                        case "b":
                            SavingsAccount y = new SavingsAccount(name, balance);
                            sa.add(c2,y);
                            System.out.println("Savings account added");
                            c2++;
                            break;
                        default:
                            System.out.println("INVALID option");
                            break;
                    }
                    break;
                case "l":
                    // TBD
                    System.out.print("Type of account? (a)Bank Account (b)Savings\n");
                    option2 = sc.next();
                    switch (option2) {
                        case "a":
                            for(BankAccount x : ba) {
                                System.out.print("Name:"+x.getName()+"\nBalance:"+x.getBalance()+"\n");
                            }
                            break;
                        case "b":
                            for(SavingsAccount y : sa) {
                                System.out.print("Name:"+y.getName()+"\nBalance:"+y.getBalance()+"\n");
                            }
                            break;
                        default:
                            System.out.println("INVALID option");
                            break;
                    }
                    break;
                case "s":
                    // Save to DB
                    System.out.print("\nNot ready!\n");
                    break;
                case "d":
                    // Deposit
                    System.out.print("Type of account? (a)Bank Account (b)Savings\n");
                    option2 = sc.next();
                    switch (option2) {
                        case "a":
                            System.out.print("Enter name:");
                            String n = sc.next();
                            for(BankAccount x : ba) {
                                if(n.equals(x.getName())) {
                                    System.out.print("Enter amount to deposit");
                                    double depAmt = sc.nextDouble();
                                    x.deposit(depAmt);
                                    System.out.print("\nAmount deposited!\n");
                                } else {
                                    System.out.print("\nName not found!\n");
                                }
                            }
                            break;
                        case "b":
                            System.out.print("Enter name:");
                            String n1 = sc.next();
                            for(SavingsAccount y : sa) {
                                if(n1.equals(y.getName())) {
                                    System.out.print("Enter amount to deposit");
                                    double depAmt = sc.nextDouble();
                                    y.deposit(depAmt);
                                    System.out.print("\nAmount deposited!\n");
                                } else {
                                    System.out.print("\nName not found!\n");
                                }
                            }
                            break;
                        default:
                            System.out.println("INVALID option");
                            break;
                    }
                    break;
                case "w":
                    // Withdraw
                    System.out.print("Type of account? (a)Bank Account (b)Savings\n");
                    option2 = sc.next();
                    switch (option2) {
                        case "a":
                            System.out.print("Enter name:");
                            String n = sc.next();
                            for(BankAccount x : ba) {
                                if(n.equals(x.getName())) {
                                    System.out.print("Enter amount to withdraw\n");
                                    double depAmt = sc.nextDouble();
                                    x.withdraw(depAmt);
                                    System.out.print("\nAmount withdrawn!\n");
                                } else {
                                    System.out.print("\nName not found!\n");
                                }
                            }
                            break;
                        case "b":
                            System.out.print("Enter name:");
                            String n1 = sc.next();
                            for(SavingsAccount y : sa) {
                                if(n1.equals(y.getName())) {
                                    System.out.print("Enter amount to withdraw\n");
                                    double depAmt = sc.nextDouble();
                                    y.withdraw(depAmt);
                                    System.out.print("\nAmount withdrawn!\n");
                                } else {
                                    System.out.print("\nName not found!\n");
                                }
                            }
                            break;
                        default:
                            System.out.println("INVALID option");
                            break;
                    }
                    break;
                default:
                    System.out.println("INVALID option. Retry");
                    break;
            }
            System.out.println("Choose an option:  (a)Add Account (l)Display Accounts (s)Save to database (d) Deposit funds (w)Withdraw funds (q)Quit ");
            option = sc.next();
        }

        // Save to DB during exit


        // Close scanner
        sc.close();
    }
    private static void addToDatabase(String student, int mark){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/exams","root","c0nygre");
            PreparedStatement stmt=conn.prepareStatement(
                    "INSERT INTO exammarks(name, mark) VALUES(?,?)");
            stmt.setString(1,student);
            stmt.setInt(2,mark);
            stmt.executeUpdate();
            conn.close();
        }catch(SQLException e){
            System.out.println("Database Exception");
            System.out.println(e.getMessage());
        }
        catch(ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void getResults() {
        try {
            System.out.println("***** Results from the Database *****");
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/exams", "root", "c0nygre");
            Statement stmt = conn.createStatement();
            ResultSet examResults = stmt.executeQuery("SELECT * FROM exammarks");
            while (examResults.next()) {
                System.out.printf("%s got %d\n",
                        examResults.getString("name"),
                        examResults.getInt("mark"));
            }
            examResults.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Database Exception");
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

}

