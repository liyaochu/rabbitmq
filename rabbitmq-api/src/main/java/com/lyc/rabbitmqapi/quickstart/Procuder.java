package com.lyc.rabbitmqapi.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: Jhon Li
 * @Date: 2019/1/19 15:42
 * @Description:生产者
 */
public class Procuder {
    public static void main(String[] args) throws IOException, TimeoutException {
        //1.创建一个 ConnectionFactory,并进行配置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.146.133");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2.通过连接工厂ConnectionFactory 创建连接
        Connection connection = connectionFactory.newConnection();

        //3.通过connection创造一个channel
        Channel channel = connection.createChannel();

        //4.通过channel发送数据
        for (int i = 0; i < 5; i++) {
            String msg="Hello RabbitMQ";
            //1.exchange  2.routingKey
            //当没有指定交换机,默认会走第一个default交换机,然后根据routingKey的名字(名字要和queue相同),去消费,不然消费不成功
            channel.basicPublish("","test001",null,msg.getBytes());
        }


        //5.记得关闭相关的连接
        channel.close();
        connection.close();
    }
}
