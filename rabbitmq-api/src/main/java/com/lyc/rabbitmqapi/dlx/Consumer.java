package com.lyc.rabbitmqapi.dlx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: Jhon Li
 * @Date: 2019/1/21 18:27
 * @Description:
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

        //这就是一个普通的交换机和对列以及路由
        String exchange="test_dlx_exchange";
        String routingKey="dlx.#";
        String queueName="test_dlx_queue";
      //  String routingKeyError="abc.save";


        channel.exchangeDeclare(exchange,"topic",true,false,null);
        Map<String,Object> agruments=new HashMap<>();
        agruments.put("x-dead-letter-exchange","dlx.exchange");


        channel.queueDeclare(queueName,true,true,false,agruments);
        channel.queueBind(queueName,exchange,routingKey);

        //要进行死信对列的申明
        channel.exchangeDeclare("dlx.exchange","topic",true,false,null);
        channel.queueDeclare("dlx.queue",true,false,false,null);
        channel.queueBind("dlx.queue","dlx.exchange","#");


        channel.basicConsume(queueName,true,new MyConsumer(channel));



    }
}
