package net.sparkworks.mapper.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class Receiver implements MessageListener {
    @Override
    public void onMessage(Message message) {
        byte[] messageContent = message.getBody();
        System.err.println("Message receied is " + messageContent);

    }
}
