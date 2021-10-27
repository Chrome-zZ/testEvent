package com.example.demo.service.impl;

import com.example.demo.DemoApplication;
import com.example.demo.Listener;
import com.example.demo.model.Event;
import com.example.demo.service.EventService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.demo.DemoApplication.jmsTemplate;

@Service
public class EventServiceImpl implements EventService {

    private static final String MESSAGE_QUEUE = "message_queue";
    private static final AtomicInteger ID_HOLDER = new AtomicInteger();

    List<Event> events = Stream.of(
            new Event(ID_HOLDER.incrementAndGet(), "First", new Date(), false),
            new Event(ID_HOLDER.incrementAndGet(), "Second", new Date(), true)
    ).collect(Collectors.toList());

    @Override
    public void send(Event event) {
        //здесь в аргументах метода, возможно, нужно задавать очередь, для различных получаетелей
        //также нужно задать периодичность отправки сообщений в случае неудачи на стороне приёма (retrying)
        jmsTemplate = DemoApplication.context.getBean(JmsTemplate.class);
        System.out.println("------------ SENDING EVENTS... ------------");
        jmsTemplate.convertAndSend(MESSAGE_QUEUE, event);
//        event.setSent(true);
    }

    @Override
    public Event getById(Long id) {
        return events.stream().filter(event -> event.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    @JmsListener(destination = MESSAGE_QUEUE, containerFactory = "myFactory")
    public void receive(Event event) {
        Listener listener = new Listener();
        listener.receiveMessage(event);
        event.setSent(true);
    }
}
