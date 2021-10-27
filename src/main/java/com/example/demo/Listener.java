package com.example.demo;

import com.example.demo.model.Event;
import org.springframework.stereotype.Component;

@Component
public class Listener {

    //    @JmsListener(destination = MESSAGE_QUEUE, containerFactory = "myFactory")
    public void receiveMessage(Event event) {
        System.out.println();
        System.out.println("========================================");
        System.out.println("Received message is: " + event);
        System.out.println("========================================");
    }
}
