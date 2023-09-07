package com.example.multithreading_matrix.other;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Test3 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);
        Callable<String> task = () -> {
            System.out.println(Thread.currentThread().getName());
            return Thread.currentThread().getName();
        };
        var sf = scheduledExecutorService.schedule(task, 4, TimeUnit.SECONDS);
        scheduledExecutorService.shutdown();
    }

}
