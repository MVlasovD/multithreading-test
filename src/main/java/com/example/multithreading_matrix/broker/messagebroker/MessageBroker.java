package com.example.multithreading_matrix.broker.messagebroker;

import com.example.multithreading_matrix.broker.consumer.MessageConsumingTask;
import com.example.multithreading_matrix.broker.model.Message;
import com.example.multithreading_matrix.broker.producer.MessageProducingTask;

import java.util.ArrayDeque;
import java.util.Queue;

import static java.lang.Thread.currentThread;

public final class MessageBroker {

    private static final String MESSAGE_IS_PRODUCED = "Message '%s' is produced by producer '%s'. " +
            "Amount of messages before producing: %d\n";
    private static final String TEMPLATE_MESSAGE_IS_CONSUMED = "Message '%s' is consumed by consumer '%s'. " +
            "Amount of messages before consuming: %d\n";

    private final Queue<Message> messages;
    private final int maxStoredMessages;

    public MessageBroker(int maxStoredMessages) {
        this.messages = new ArrayDeque<>(maxStoredMessages);
        this.maxStoredMessages = maxStoredMessages;
    }

    public synchronized void produce(final Message message, final MessageProducingTask producingTask) {
        while (!this.isShouldProduce(producingTask)) {
            try {
                wait();
            } catch (InterruptedException e) {
                currentThread().interrupt();
            }
        }
        this.messages.add(message);
        System.out.printf(MESSAGE_IS_PRODUCED, message, producingTask.getName(), this.messages.size() - 1);
        notifyAll();
    }

    public synchronized Message consume(final MessageConsumingTask consumingTask) {
        while (!this.isShouldConsume(consumingTask)) {
            try {
                wait();
            } catch (InterruptedException e) {
                currentThread().interrupt();
            }
        }
        final Message consumedMessage = this.messages.poll();
        System.out.printf(TEMPLATE_MESSAGE_IS_CONSUMED, consumedMessage, consumingTask.getName(), this.messages.size() + 1);
        notifyAll();
        return consumedMessage;
    }

    private boolean isShouldConsume(final MessageConsumingTask consumingTask){
        return !this.messages.isEmpty()
                && this.messages.size() >= consumingTask.getMinAmountMessageToConsume();
    }

    private boolean isShouldProduce(final MessageProducingTask producingTask){
        return this.messages.size() < this.maxStoredMessages
                && this.messages.size() <= producingTask.getMaxAmountMessagesToProduce();
    }
}
