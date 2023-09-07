package com.example.multithreading_matrix.other;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

import static java.lang.Thread.sleep;

public class Test6 {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        Supplier newsSupplier = () -> Test6.getMessage();

        CompletableFuture<String> reader = CompletableFuture.supplyAsync(newsSupplier);
        CompletableFuture.completedFuture("!!")
                .thenCombine(reader, (a, b) -> b + a)
                .thenAccept(System.out::println)
                .get();

        CompletableFuture.completedFuture(2L)
                .thenApply((a) -> {
                    throw new IllegalStateException("error".toUpperCase());
                }).thenApply((a) -> 3L)
                .exceptionally(ex -> 0L)
                .thenAccept(val -> System.out.println(val))
                .get(100, TimeUnit.MILLISECONDS);
    }

        public static String getMessage() {
            try {
                sleep(2000);
                return "Message";
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }
    }
