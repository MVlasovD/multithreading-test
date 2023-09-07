package com.example.multithreading_matrix.broker.consumer;

import com.example.multithreading_matrix.broker.messagebroker.MessageBroker;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.currentThread;

public class MessageConsumingTask implements Runnable {

    private static final int SLEEP_DURATION_IN_SECONDS = 0;


    private final MessageBroker messageBroker;
    private final int minAmountMessageToConsume;
    private final String name;

    public MessageConsumingTask(MessageBroker messageBroker, int minAmountMessageToConsume, String name) {
        this.messageBroker = messageBroker;
        this.minAmountMessageToConsume = minAmountMessageToConsume;
        this.name = name;
    }

    public int getMinAmountMessageToConsume() {
        return minAmountMessageToConsume;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        while (!currentThread().isInterrupted()) {
            try {
                TimeUnit.SECONDS.sleep(SLEEP_DURATION_IN_SECONDS);
            } catch (InterruptedException e) {
                currentThread().interrupt();
            }
            this.messageBroker.consume(this);
        }
    }
}
