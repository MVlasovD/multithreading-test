package com.example.multithreading_matrix.other;

import java.util.concurrent.atomic.AtomicReference;

public class TestAtomicReference {
    private static String message = "hello";
    private static AtomicReference<String> atomicReference;

    public static void main(final String[] arguments) throws InterruptedException {
        atomicReference = new AtomicReference<String>(message);

        new Thread("Thread 1") {

            public void run() {
                atomicReference.compareAndSet(message, "Thread 1");
                message = message.concat(" - Thread 1!".toUpperCase());
            };
        }.start();

        System.out.println("Message is: " + message);
        System.out.println("Atomic Reference of Message is: " + atomicReference.get());
    }
}