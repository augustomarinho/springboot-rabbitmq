package com.study.springboot.rabbitmq.consumer;

import com.study.springboot.rabbitmq.constants.MessagingApplication;
import com.study.springboot.rabbitmq.dto.AMQPMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DefaultQueueAMQPConsumer {

    private static final Logger logger = LoggerFactory.getLogger(DefaultQueueAMQPConsumer.class);

    @RabbitListener(queues = MessagingApplication.QUEUE_AMQP_MESSAGE)
    public void receiveMessage(final AMQPMessage customMessage) {
        logger.info("Received message as specific class: {}", customMessage.toString());
    }
}
