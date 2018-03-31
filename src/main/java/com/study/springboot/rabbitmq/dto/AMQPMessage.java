package com.study.springboot.rabbitmq.dto;

import java.io.Serializable;

public class AMQPMessage implements Serializable {

    private String id;
    private String content;

    public AMQPMessage() {

    }

    public AMQPMessage(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "AMQPMessage{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}