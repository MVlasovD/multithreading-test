package com.example.multithreading_matrix.other;

import java.util.concurrent.atomic.AtomicInteger;

public class EvenNumberGenerator {

    private final static int DELTA = 2;
    private final AtomicInteger value = new AtomicInteger();


    public int generate(){
        return this.value.addAndGet(DELTA);
    }

    public int getValue(){
        return this.value.intValue();
    }

}
