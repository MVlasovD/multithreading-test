package com.example.multithreading_matrix.broker;

import com.example.multithreading_matrix.broker.consumer.MessageConsumingTask;
import com.example.multithreading_matrix.broker.messagebroker.MessageBroker;
import com.example.multithreading_matrix.broker.producer.MessageFactory;
import com.example.multithreading_matrix.broker.producer.MessageProducingTask;

import java.util.Arrays;

public class BrokerRunner {

    public static void main(String[] args) {
        final int brokerMaxStoredMessages = 15;

        final MessageBroker messageBroker = new MessageBroker(brokerMaxStoredMessages);
        final MessageFactory messageFactory = new MessageFactory();

        final Thread firstProducingThread = new Thread(new MessageProducingTask(messageBroker, messageFactory,
                brokerMaxStoredMessages, "Producer_1"));
        final Thread secondProducingThread = new Thread(new MessageProducingTask(messageBroker, messageFactory,
                10, "Producer_2"));
        final Thread thirdProducingThread = new Thread(new MessageProducingTask(messageBroker, messageFactory,
                5, "Producer_3"));

        final Thread firstConsumingThread = new Thread(new MessageConsumingTask(messageBroker,
                0, "Consumer_1"));
        final Thread secondConsumingThread = new Thread(new MessageConsumingTask(messageBroker,
                6, "Consumer_2"));
        final Thread thirdConsumingThread = new Thread(new MessageConsumingTask(messageBroker,
                11, "Consumer_3"));

        startThreads(firstProducingThread, firstConsumingThread,
                secondProducingThread, secondConsumingThread,
                thirdProducingThread, thirdConsumingThread);
    }

    private static void startThreads(Thread... threads){
        Arrays.stream(threads).forEach(Thread::start);
    }
}
