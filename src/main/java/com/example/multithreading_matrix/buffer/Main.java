package com.example.multithreading_matrix.buffer;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static java.lang.Thread.currentThread;

public class Main {

    public static void main(String[] args) {
        final BoundedBuffer<Integer> boundedBuffer = new BoundedBuffer<>(5);

        final Runnable producingTask = () -> {
            Stream.iterate(0, i -> i + 1)
                    .forEach(i -> {
                        try {
                            boundedBuffer.put(i);
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException interruptedException) {
                            currentThread().interrupt();
                        }
                    });
        };

        final Thread producingThread = new Thread(producingTask);

        final Runnable consumingTask = () -> {
            try {
                while (!currentThread().isInterrupted()){
                    boundedBuffer.take();
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException interruptedException){
                currentThread().interrupt();
            }
        };

        final Thread consumingThread = new Thread(consumingTask);

        producingThread.start();
        consumingThread.start();
    }
}
