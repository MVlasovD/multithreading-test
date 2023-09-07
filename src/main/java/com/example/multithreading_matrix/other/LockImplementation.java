package com.example.multithreading_matrix.other;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

import static java.lang.Thread.currentThread;

public class LockImplementation {

    public static void main(String[] args) {
        final EvenNumberGenerator evenNumberGenerator = new EvenNumberGenerator();

        Runnable task = () ->
                IntStream.range(0, 100)
                        .forEach(i -> {
                            try {
                                System.out.println(evenNumberGenerator.generate());
                            } catch (InterruptedException e) {
                                currentThread().interrupt();
                            }
                        });

        final Thread firstThread = new Thread(task);
        final Thread secondThread = new Thread(task);
        final Thread thirdThread = new Thread(task);
        startThreads(firstThread, secondThread, thirdThread);
    }

    private static final class EvenNumberGenerator {

        private final Lock lock;
        private int previousGenerated;

        public EvenNumberGenerator() {
            this.previousGenerated = -2;
            this.lock = new ReentrantLock();
        }

        public int generate() throws InterruptedException {
            return this.lock.tryLock(1, TimeUnit.NANOSECONDS) ? this.onSuccesAcquireLock() : this.onFailAcquireLock();
        }

        private int onSuccesAcquireLock(){
            try {
                return this.previousGenerated += 2;
            } finally {
                this.lock.unlock();
            }
        }

        private int onFailAcquireLock(){
            System.out.printf("Thread '%s' didn't acquire lock.\n", currentThread().getName());
            throw new RuntimeException("");
        }
    }

    private static void startThreads(final Thread... threads){
        Arrays.stream(threads).forEach(Thread::start);
    }
}
