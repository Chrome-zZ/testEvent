package com.example.demo.service;

import com.example.demo.dto.EventDTO;
import com.example.demo.model.Event;

public interface EventService {

    public EventDTO send(Long id);

    public Event getById( Long id);

}
