package com.example.multithreading_matrix.broker.producer;

import com.example.multithreading_matrix.broker.model.Message;

public final class MessageFactory {

    private static final int INITIAL_INDEX = 1;
    private static final String TEMPLATE_MESSAGE_DATA = "Message#%d";

    private int nextMessageIndex;

    public MessageFactory() {
    }

    public MessageFactory(int nextMessageIndex) {
        this.nextMessageIndex = INITIAL_INDEX;
    }

    public Message create() {
        return new Message(String.format(TEMPLATE_MESSAGE_DATA, this.findAndIncrementMessageIndex()));
    }

    private synchronized int findAndIncrementMessageIndex(){
        return this.nextMessageIndex++;
    }
}
