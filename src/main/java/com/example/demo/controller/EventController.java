package com.example.demo.controller;

import com.example.demo.service.EventService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/{id}")
    public void send(@PathVariable Long id) {
        eventService.send(id);
    }

    @GetMapping("/{id}")
    public void receive(@PathVariable Long id) {
        eventService.receive(id);
    }

}
