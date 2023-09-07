package com.example.multithreading_matrix.other;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test8 {
    public static void main(String[] args) {
        // Создаем пул потоков с тремя потоками
        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 10; i++) {
            final int taskNumber = i;
            executor.execute(() -> {
                System.out.println("Task " + taskNumber + " is running on thread " + Thread.currentThread().getName());
            });
        }

        // Завершение работы пула потоков
        executor.shutdown();
    }
}