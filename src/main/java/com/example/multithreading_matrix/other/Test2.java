package com.example.multithreading_matrix.other;

import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import static java.lang.Thread.currentThread;

public class Test2 {

//    public static AtomicInteger atomicCounter = new AtomicInteger(0);
    private static int counter = 0;

    static Semaphore semaphore = new Semaphore(1);

    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread first = createThread(500);
        Thread second = createThread(500);

        Counter counterC = new Counter();
        Counter counterCo = new Counter();
        Thread third = createThread(450, i -> counterC.increment());
        Thread fourth = createThread(500, i -> counterCo.increment());

        startThreads(first, second, third, fourth);
        joinThreads(first,second,third,fourth);

        System.out.println(counter);
        System.out.println(counterC.counter);
        System.out.println(counterCo.counter);
    }

    public static Thread createThread(final int increment) {
        return new Thread(() -> IntStream.range(0, increment)
//                                         .forEach(i -> atomicCounter.getAndIncrement()));
                .forEach(i -> {
                    try {
                        incrementCounter();
                    } catch (InterruptedException e) {
                        currentThread().interrupt();
                    }
                }));
    }

    public static Thread createThread(final int increment, final IntConsumer incrementingOperation) {
        return new Thread(() -> IntStream.range(0, increment)
                .forEach(incrementingOperation));
    }

    private static void incrementCounter() throws InterruptedException {
//        semaphore.acquire();
        synchronized (LOCK) {
            counter++;
        }
//        semaphore.release();
    }

    private static void startThreads(final Thread... threads){
        Arrays.stream(threads).forEach(Thread::start);
    }

    private static void joinThreads(final Thread... thread){
        Arrays.stream(thread).forEach(t -> {
            try {
               t.join();
            }catch (InterruptedException e){
                currentThread().interrupt();
            }
        });
    }

   static final class Counter {
        private int counter;

        private void increment(){
            synchronized (this) {
                this.counter++;
            }
        }
    }
}
