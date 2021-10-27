package com.example.demo.service;

import com.example.demo.model.Event;

public interface EventService {

    void send(Long id);

    Event getById( Long id);

    void receive(Long id);

}
