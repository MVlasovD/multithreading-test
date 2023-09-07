package com.example.multithreading_matrix.other;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static java.util.Arrays.stream;
import static java.util.stream.IntStream.range;

public class TestGenerator {

    public static void main(String[] args) {
        final EvenNumberGenerator generator = new EvenNumberGenerator();

        final int taskGenerationCounts = 10_000;
        final Runnable generatingTask =
                () -> range(0, taskGenerationCounts)
                        .forEach(i -> generator.generate());

        final int amountOfGeneratingThreads = 5;
        final Thread[] generatingThreads = createThreads(generatingTask, amountOfGeneratingThreads);

        startThreads(generatingThreads);
        waitUntilFinish(generatingThreads);

        final int expectingGeneratorValue = amountOfGeneratingThreads * taskGenerationCounts * 2;
        final int actualGeneratorValue = generator.getValue();
        if(expectingGeneratorValue != actualGeneratorValue){
            throw new RuntimeException("Expected is %d but was %d".formatted(expectingGeneratorValue, actualGeneratorValue));
        }
         out.println(actualGeneratorValue);
    }

    private static Thread[] createThreads(final Runnable task, final int amountsOfThreads) {
        return range(0, amountsOfThreads)
                .mapToObj(i -> new Thread(task))
                .toArray(Thread[]::new);
    }

    private static void startThreads(final Thread[] threads) {
        stream(threads).forEach(Thread::start);
    }

    private static void waitUntilFinish(final Thread thread) {
        try {
            thread.join();
        } catch (final InterruptedException interruptedException) {
            currentThread().interrupt();
        }
    }

    private static void waitUntilFinish(final Thread[] threads) {
        stream(threads).forEach(TestGenerator::waitUntilFinish);
    }
}
