package com.example.demo.controller;

import com.example.demo.dto.EventDTO;
import com.example.demo.model.Event;
import com.example.demo.service.EventService;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{id}")
    public Event getById(@PathVariable Long id) {
        return eventService.getById(id);
    }

    @PostMapping("/{id}")
    public EventDTO send(@PathVariable Long id) {
        return eventService.send(id);
    }

}
