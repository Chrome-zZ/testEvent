package com.example.demo.service.impl;

import com.example.demo.dto.EventDTO;
import com.example.demo.model.Event;
import com.example.demo.service.EventService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class EventServiceImpl implements EventService {


    private static final AtomicInteger ID_HOLDER = new AtomicInteger();

    private final List<Event> events = Stream.of(
            new Event(ID_HOLDER.incrementAndGet(), "First", LocalDateTime.now(), false),
            new Event(ID_HOLDER.incrementAndGet(), "Second", LocalDateTime.now(), false)
    ).collect(Collectors.toList());

    @Override
    public EventDTO send(Long id) {
        EventDTO eventDTO = new EventDTO();
        if (!getById(id).isSent()) {
            send(id);
        } else getById(id).setSent(true);
        return eventDTO;
    }

    @Override
    public Event getById(Long id) {
        return events.stream().filter(event -> event.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
