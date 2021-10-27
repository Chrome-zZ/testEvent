package com.example.demo.service;

import com.example.demo.model.Event;

public interface EventService {

    void send(Event event);

    Event getById( Long id);

    void receive(Event event);

}
