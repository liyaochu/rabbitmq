package com.lyc.rabbitmqapi.ack;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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

        String exchange="test_ack_exchange";
        String routingKey="ack.save";
      //  String routingKeyError="abc.save";



        for (int i = 0; i <5 ; i++) {
        Map<String,Object> headers=new HashMap<String,Object>();
            headers.put("num",i);
            AMQP.BasicProperties properties =new AMQP.BasicProperties()
                    .builder()
                    .deliveryMode(2)
                    .contentEncoding("UTF-8")
                    .headers(headers)
                    .build();
            String msg="Hello RabbitMQ  ACK Message"+i;
            channel.basicPublish(exchange,routingKey,true,properties,msg.getBytes());
        }


     //   channel.basicPublish(exchange,routingKeyError,true,null,msg.getBytes());
    }
}
