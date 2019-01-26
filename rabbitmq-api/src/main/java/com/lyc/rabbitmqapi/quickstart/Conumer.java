package com.lyc.rabbitmqapi.quickstart;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: Jhon Li
 * @Date: 2019/1/19 15:35
 * @Description:消费者
 */
public class Conumer {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //1.创建一个 ConnectionFactory,并进行配置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.146.133");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        
        //2.通过连接工厂ConnectionFactory 创建连接
        Connection connection = connectionFactory.newConnection();
      
        //3.通过connection创造一个channel
        Channel channel = connection.createChannel();

        //4.声明一个队列
        /**
         * true表示队列持久化
         */
        String queueName="test001";
        channel.queueDeclare(queueName,true,false,false,null);

        //5.创建消费者
        QueueingConsumer consumer=new QueueingConsumer(channel);

        //6.设置channel
        /**
         * true 表示自动签收(ACk)
         */
        channel.basicConsume(queueName,true,consumer);
        //循环获得消费
        while(true){
            //7.获取消息
            QueueingConsumer.Delivery delivery =  consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.err.println("消费端: "+msg);
        //    Envelope envelope = delivery.getEnvelope();

        }

    }
}
