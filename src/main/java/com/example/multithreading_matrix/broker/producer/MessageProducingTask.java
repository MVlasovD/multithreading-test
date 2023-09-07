package com.example.multithreading_matrix.broker.producer;

import com.example.multithreading_matrix.broker.messagebroker.MessageBroker;
import com.example.multithreading_matrix.broker.model.Message;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.currentThread;

public class MessageProducingTask implements Runnable{
    private static final int SLEEP_DURATION_IN_SECONDS = 0;
    private final MessageBroker messageBroker;
    private final MessageFactory messageFactory;
    private final int maxAmountMessagesToProduce;
    private final String name;

    public MessageProducingTask(MessageBroker messageBroker, MessageFactory messageFactory,
                                int maxAmountMessagesToProduce, String name) {
        this.messageBroker = messageBroker;
        this.messageFactory = messageFactory;
        this.maxAmountMessagesToProduce = maxAmountMessagesToProduce;
        this.name = name;
    }

    public int getMaxAmountMessagesToProduce() {
        return maxAmountMessagesToProduce;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        while (!currentThread().isInterrupted()){
            final Message producedMessage = this.messageFactory.create();
            try {
                TimeUnit.SECONDS.sleep(SLEEP_DURATION_IN_SECONDS);
            } catch (InterruptedException e) {
                currentThread().interrupt();
            }
            this.messageBroker.produce(producedMessage, this);
        }
    }
}
