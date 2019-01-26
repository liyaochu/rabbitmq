package com.lyc.rabbitmqapi.consumer;

import com.rabbitmq.client.*;
import com.rabbitmq.client.Consumer;

import java.io.IOException;

/**
 * @Auther: Jhon Li
 * @Date: 2019/1/22 12:25
 * @Description:  自定义的消费者
 */
public class MyConsumer extends DefaultConsumer {

    public MyConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.err.println("-------------consume message ---------------");
        System.err.println("consumerTag: "+consumerTag);
        System.err.println("envelope :"+envelope);
        System.err.println("properties :"+properties);
        System.err.println("body: "+new String(body));
    }
}
