package com.example.multithreading_matrix;

import com.example.multithreading_matrix.broker.BrokerRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class MultithreadingMatrixApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultithreadingMatrixApplication.class, args);
    }

}
