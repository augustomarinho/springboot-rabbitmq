package com.study.springboot.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import com.study.springboot.rabbitmq.constants.MessagingApplication;
import com.study.springboot.rabbitmq.constants.RabbitConstants;
import com.study.springboot.rabbitmq.dto.AMQPMessage;
import com.study.springboot.rabbitmq.serialization.ToBytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DefaultQueueAMQPConsumer {

    private static final Logger logger = LoggerFactory.getLogger(DefaultQueueAMQPConsumer.class);

    @RabbitListener(queues = MessagingApplication.QUEUE_AMQP_MESSAGE)
    public void receiveMessage(final AMQPMessage customMessage) {
        logger.info("Received message as specific class: {}", customMessage.toString());
    }

    @RabbitListener(queues = MessagingApplication.QUEUE_AMQP_MESSAGE, containerFactory = RabbitConstants.BEAN_MANUAL_ACK)
    public void receiveMessage(final byte[] customMessage) throws IOException, ClassNotFoundException {
        logger.info("Received byte array message as specific class: {}", ToBytes.deserialize(customMessage));
    }

    @RabbitListener(queues = MessagingApplication.QUEUE_AMQP_MANUAL_ACK, containerFactory = RabbitConstants.BEAN_MANUAL_ACK)
    public void receiveMessageManualAck(final AMQPMessage customMessage,
                                        Channel channel,
                                        @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        logger.info("Received message as specific class: {} and Tag {}", customMessage.toString(), tag);

        if(tag > 2) {
            channel.basicAck(tag, false);
        }
        else {
            channel.basicReject(tag, true);
        }
    }
}