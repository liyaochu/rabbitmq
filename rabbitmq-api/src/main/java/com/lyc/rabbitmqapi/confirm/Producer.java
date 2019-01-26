package com.lyc.rabbitmqapi.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: Jhon Li
 * @Date: 2019/1/21 17:32
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

        //指定我们的消息投递模式;消息确认模式
        channel.confirmSelect();

        String exchangeName="test_confirm_exchange";

        String routingKey="confirm.save";

        //发送一条消息
        String msg="Hello RabbitMq Send confirm message";
        channel.basicPublish(exchangeName,routingKey,null,msg.getBytes());

        //添加一个确认监听
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long l, boolean b) throws IOException {
                System.out.println("-------- ack ------------");
            }

            @Override
            public void handleNack(long l, boolean b) throws IOException {
                System.out.println("--------no ack ------------");
            }
        });


        channel.close();
        connection.close();
    }
}
