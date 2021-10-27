package com.example.demo.service.impl;

import com.example.demo.DemoApplication;
import com.example.demo.Listener;
import com.example.demo.model.Event;
import com.example.demo.service.EventService;
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

    private final Listener listener;

    private final List<Event> events = Stream.of(
            new Event(ID_HOLDER.incrementAndGet(), "First", new Date(), false),
            new Event(ID_HOLDER.incrementAndGet(), "Second", new Date(), false)
    ).collect(Collectors.toList());

    public EventServiceImpl(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void send(Long id) {
        jmsTemplate = DemoApplication.context.getBean(JmsTemplate.class);
//здесь в аргументах метода, возможно, нужно задавать очередь, для различных получаетелей
        System.out.println("------------ SENDING EVENTS... ------------");
        jmsTemplate.convertAndSend(MESSAGE_QUEUE, getById(id));
    }

    @Override
    public Event getById(Long id) {
        return events.stream().filter(event -> event.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void receive(Long id) {
        Event eventToReceive = getById(id);
        if (eventToReceive != null) {
            listener.receiveMessage(eventToReceive);
            eventToReceive.setSent(true);
        }
    }
}
