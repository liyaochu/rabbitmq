package com.lyc.rabbitmqapi.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: Jhon Li
 * @Date: 2019/1/21 18:16
 * @Description:
 */
public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.146.133");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //获取Connection
        Connection connection = connectionFactory.newConnection();

        //通过connection创建一个新的Channel
        Channel channel = connection.createChannel();

        String exchange="test_consumer_exchange";
        String routingKey="consumer.save";
      //  String routingKeyError="abc.save";

        String msg="Hello RabbitMQ  Consumer Message";

        for (int i = 0; i <5 ; i++) {
            channel.basicPublish(exchange,routingKey,true,null,msg.getBytes());
        }


     //   channel.basicPublish(exchange,routingKeyError,true,null,msg.getBytes());
    }
}
