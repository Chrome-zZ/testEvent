package com.example.demo.service.impl;

import com.example.demo.DemoApplication;
import com.example.demo.Listener;
import com.example.demo.model.Event;
import com.example.demo.service.EventService;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.demo.DemoApplication.jmsTemplate;

@Service
public class EventServiceImpl implements EventService {

    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final String MESSAGE_QUEUE = "message_queue";
    private static final AtomicInteger ID_HOLDER = new AtomicInteger();

    List<Event> events = Stream.of(
            new Event(ID_HOLDER.incrementAndGet(), "First", new Date(), false),
            new Event(ID_HOLDER.incrementAndGet(), "Second", new Date(), true)
    ).collect(Collectors.toList());

    static ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

    @Override
    public void send(Event event) throws JMSException {
        //здесь в аргументах метода, возможно, нужно задавать очередь, для различных получаетелей
        //также нужно задать периодичность отправки сообщений в случае неудачи на стороне приёма (retrying)
        jmsTemplate = DemoApplication.context.getBean(JmsTemplate.class);
        System.out.println("------------ SENDING EVENTS... ------------");
        System.out.println("URL is - " + url);

        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createQueue(MESSAGE_QUEUE);
        MessageProducer producer = session.createProducer(destination);
        TextMessage message = session.createTextMessage(String.valueOf(event));
        producer.send(message);
//        event.setSent(true);
        connection.close();
        //        jmsTemplate.convertAndSend(MESSAGE_QUEUE, event);
    }

    @Override
    public Event getById(Long id) {
        return events.stream().filter(event -> event.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
//    @JmsListener(destination = MESSAGE_QUEUE, containerFactory = "myFactory")
    public void receive(Event event) throws JMSException {
        System.out.println("URL is - " + url);

        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createQueue(MESSAGE_QUEUE);

        MessageConsumer consumer = session.createConsumer(destination);
        if (event.isSent()) {
            consumer.receive();
        } else send(event);
        connection.close();
    }
}
