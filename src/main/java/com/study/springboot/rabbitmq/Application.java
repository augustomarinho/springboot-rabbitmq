package com.study.springboot.rabbitmq;

import com.rabbitmq.client.MessageProperties;
import com.study.springboot.rabbitmq.constants.MessagingApplication;
import com.study.springboot.rabbitmq.dto.AMQPMessage;
import com.study.springboot.rabbitmq.serialization.ToBytes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public String send(@RequestParam("id") String id,
                       @RequestParam("content") String content) {
        try {
            final AMQPMessage message = new AMQPMessage(id, content);
            rabbitTemplate.convertAndSend(MessagingApplication.QUEUE_AMQP_MESSAGE, message);
            return "OK";
        } catch (Exception e) {
            return "NOK";
        }
    }

    @RequestMapping(value = "/send/custom", method = RequestMethod.POST)
    public String sendQueue(@RequestParam("id") String id,
                            @RequestParam("content") String content) {
        try {
            final AMQPMessage message = new AMQPMessage(id, content);
            Message amqpMessage = MessageBuilder
                    .withBody(ToBytes.serialize(message))
                    .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                    .build();

            rabbitTemplate.convertAndSend(MessagingApplication.QUEUE_AMQP_MESSAGE, amqpMessage);
            return "OK";
        } catch (Exception e) {
            return "NOK";
        }
    }

    @RequestMapping(value = "/send/manual", method = RequestMethod.POST)
    public String sendQueueManual(@RequestParam("id") String id,
                                  @RequestParam("content") String content) {
        try {
            final AMQPMessage message = new AMQPMessage(id, content);
            rabbitTemplate.convertAndSend(MessagingApplication.QUEUE_AMQP_MANUAL_ACK, message);
            return "OK";
        } catch (Exception e) {
            return "NOK";
        }
    }
}