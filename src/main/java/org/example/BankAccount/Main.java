package org.example.BankAccount;

import com.mongodb.client.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.mongodb.client.model.Filters.eq;

public class Main {
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> collection;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        final String URI = "mongodb://localhost:27017";
        String name, option, option2;
        double balance;
        System.out.println("Choose an option:  (a)Add Account (l)Display Accounts (s)Save to database  (d) Deposit funds (w)Withdraw funds (q)Quit ");
        option = sc.next();
        //List<BankAccount> ba = new ArrayList<BankAccount>();
        //List<SavingsAccount> sa = new ArrayList<SavingsAccount>();
        connectMongo(URI);

        List<BankAccount> ba = retrieveAccounts();
        List<SavingsAccount> sa = retrieveSavingAccounts();

        int c1 = 0, c2 = 0;
        // Flag can be given...
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
                            BankAccount x = new BankAccount(name, balance, "Bank");
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
                    // Display all accounts from object declared
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
                    //System.out.print("\nNot ready!\n");
                    for(BankAccount x : ba) {
                        addToCollection(x);
                    }
                    for(SavingsAccount y : sa) {
                        addSavingToCollection(y);
                    }
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
        for(BankAccount x : ba) {
            addToCollection(x);
        }
        for(SavingsAccount y : sa) {
            addSavingToCollection(y);
        }

        // Close scanner
        sc.close();
    }

    private static void connectMongo(String URI) {
        mongoClient = MongoClients.create(URI);
        database =  mongoClient.getDatabase("bank");
        collection =  database.getCollection("account");
    }
    private static void addToCollection(BankAccount t) {
        Document doc = new Document();
        doc.put("name",t.getName());
        doc.put("balance",t.getBalance());
        doc.put("type",t.getType());
        collection.insertOne(doc);
    }

    private static void addSavingToCollection(SavingsAccount t) {
        Document doc = new Document();
        doc.put("name",t.getName());
        doc.put("balance",t.getBalance());
        doc.put("type",t.getType());
        collection.insertOne(doc);
    }

    public static void displayAllDocuments() {
        //we want to retrieve all documents
        FindIterable<Document> allDocs =  collection.find();
        for(Document d: allDocs){
            System.out.println("Name:"+d.get("name"));
            System.out.println("Balance:"+d.get("balance"));
            System.out.println("Type:"+d.get("type"));
        }
    }

    private static List<BankAccount> retrieveAccounts() {
        FindIterable<Document> allAccounts = collection.find();
        List<BankAccount> x = new ArrayList<BankAccount>();
        int i = 0;
        for(Document d: allAccounts) {
            String type = d.get("type").toString();
            if(type.equals("Bank")) {
                String name = d.get("name").toString();
                double balance = Double.parseDouble(d.get("balance").toString());
                BankAccount temp = new BankAccount(name,balance,type);
                x.add(i,temp);
                i++;
            }
        }
        return x;
    }

    private static List<SavingsAccount> retrieveSavingAccounts() {
        FindIterable<Document> allAccounts = collection.find();
        List<SavingsAccount> x = new ArrayList<SavingsAccount>();
        int i = 0;
        for(Document d: allAccounts) {
            String type = d.get("type").toString();
            if(type.equals("Savings")) {
                String name = d.get("name").toString();
                double balance = Double.parseDouble(d.get("balance").toString());
                SavingsAccount temp = new SavingsAccount(name,balance);
                x.add(i,temp);
                i++;
            }
        }
        return x;
    }
}

