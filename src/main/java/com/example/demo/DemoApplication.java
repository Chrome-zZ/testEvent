package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

@SpringBootApplication
@EnableJms
public class DemoApplication {

    public static JmsTemplate jmsTemplate;
    public static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(DemoApplication.class, args);
    }
}
