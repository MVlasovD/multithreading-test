package com.example.multithreading_matrix.other;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Scanner;
import java.util.concurrent.ThreadFactory;
import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;


public class Test {

    public final static int MAX_NUMBER_OF_ITERATIONS = 20;

    public static void main(String[] args) throws InterruptedException {

        MyThread myThread = new MyThread("THREAD #1");
        System.out.println(myThread.getState());
        myThread.setPriority(Thread.MIN_PRIORITY);
        myThread.start();

        MyThread myThread2 = new MyThread("THREAD #2");
        System.out.println(myThread2.getState());
        myThread2.setDaemon(true);
        myThread2.start();
        Thread.yield();

        UncaughtExceptionHandler uncaughtExceptionHandler = (thread, exception) ->
                System.out.printf("Exception %s on thread %s\n", exception.getMessage(), thread.getName());

        ThreadFactory threadFactory = new MyThreadFactory(uncaughtExceptionHandler);

        Thread myThread3 =  threadFactory.newThread(new MyTask());
        myThread3.start();
        myThread3.join();

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        myThread.shutdownThread();
        System.out.println(myThread.getState());
        scanner.nextLine();
        myThread2.shutdownThread();
        System.out.println(myThread2.getState());

        System.out.println(myThread.getState());
        sleep(10);
        System.out.println(myThread2.getState());
    }
}

    class MyThreadFactory implements ThreadFactory{
        private final UncaughtExceptionHandler uncaughtExceptionHandler;

        public MyThreadFactory(UncaughtExceptionHandler uncaughtExceptionHandler) {
            this.uncaughtExceptionHandler = uncaughtExceptionHandler;
        }

        @Override
        public Thread newThread(final Runnable runnable) {
            final Thread thread = new Thread(runnable, "F_THREAD");
            thread.setUncaughtExceptionHandler(this.uncaughtExceptionHandler);
            thread.setDaemon(true);
            return thread;
        }
    }

    class MyTask implements Runnable{

        @Override
        public void run() {
            System.out.printf("This is MyTask with thread (state : %s)\n", currentThread().getState());
            throw new RuntimeException("<Ooooopsss, exception>");
        }
    }

    class MyThread extends Thread{

        public MyThread(String name) {
            super(name);
        }

        private volatile boolean running = true;

        @Override
        public void run() {
                int i = 0;
            while (i < Test.MAX_NUMBER_OF_ITERATIONS) {
                try {
                    sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Thread from MyThread - " + i + " " + currentThread().getState() + " : " + currentThread().getName());
                i++;
            }
        }

        public void shutdownThread (){
            this.running = false;
        }
    }

