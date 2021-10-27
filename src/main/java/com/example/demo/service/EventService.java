package com.example.demo.service;

import com.example.demo.model.Event;

import javax.jms.JMSException;

public interface EventService {

    void send(Event event) throws JMSException;

    Event getById( Long id);

    void receive(Event event) throws JMSException;

}
