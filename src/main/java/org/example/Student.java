package org.example;

interface T1 {
    public void printTest();
}
abstract class Test implements T1 {
    @Override
    public void printTest() {
        System.out.println("Called!");
    }
}
public class Student extends Test {
    @Override
    public void printTest() {
        super.printTest();
        System.out.println("T2");
    }
}

