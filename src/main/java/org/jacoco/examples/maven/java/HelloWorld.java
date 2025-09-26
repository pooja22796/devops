package org.jacoco.examples.maven.java;

public class HelloWorld {

    public String getMessage(boolean bigger) {
        if (bigger) {
            return "Hello Universe!";
        } else {
            return "Hello World!";
        }
    }

    // 👇 Add this main method
    public static void main(String[] args) {
        HelloWorld hello = new HelloWorld();
        System.out.println(hello.getMessage(false));
        System.out.println(hello.getMessage(true));
    }
}

