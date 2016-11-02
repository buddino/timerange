package net.sparkworks.mapper.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ReceiverService {
    @Value("${rabbitmq.queue.send}")
    String rabbitQueueSend;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Async
    public void receive() {
        Object message = rabbitTemplate.receiveAndConvert(rabbitQueueSend);
        if (message instanceof String) {
            String strMessage = (String) message;
            System.err.println("String message received is '" + strMessage + "'");
        } else {
            System.err.println("Unknown message received '" + message + "'");
        }
    }
}
