package com.lyc.rabbitmqapi.limit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

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

        String exchange="test_qos_exchange";
        String routingKey="qos.save";
      //  String routingKeyError="abc.save";

        String msg="Hello RabbitMQ  Qos Message";

        for (int i = 0; i <5 ; i++) {
            channel.basicPublish(exchange,routingKey,true,null,msg.getBytes());
        }


     //   channel.basicPublish(exchange,routingKeyError,true,null,msg.getBytes());
    }
}
