package com.lyc.rabbitmqapi.ack;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: Jhon Li
 * @Date: 2019/1/21 18:27
 * @Description: 消费端限流
 */
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.146.133");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //获取Connection
        Connection connection = connectionFactory.newConnection();

        //通过connection创建一个新的Channel
        Channel channel = connection.createChannel();

        String exchange="test_ack_exchange";
        String routingKey="ack.#";
        String queueName="test_ack_queue";

      //  String routingKeyError="abc.save";
        
        channel.exchangeDeclare(exchange,"topic",true,false,null);
        channel.queueDeclare(queueName,true,true,false,null);
        channel.queueBind(queueName,exchange,routingKey);
       // QueueingConsumer consumer=new QueueingConsumer(channel);


       //手工签收一定要关闭自动签收的Ack
        channel.basicConsume(queueName,false,new MyConsumer(channel));



    }
}
