package com.lyc.rabbitmqapi.limit;

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

        String exchange="test_qos_exchange";
        String routingKey="qos.#";
        String queueName="test_qos_queue";

      //  String routingKeyError="abc.save";
        
        channel.exchangeDeclare(exchange,"topic",true,false,null);
        channel.queueDeclare(queueName,true,true,false,null);
        channel.queueBind(queueName,exchange,routingKey);
       // QueueingConsumer consumer=new QueueingConsumer(channel);

        //1.限流方式,第一件事就是关闭autoAck设置为false
        //先推过来1条,然后再通过ack确认
        channel.basicQos(0,1,false);

        channel.basicConsume(queueName,false,new MyConsumer(channel));



    }
}
