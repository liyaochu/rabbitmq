package com.lyc.rabbitmqapi.returnlistener;

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

        String exchange="test_return_exchange";
        String routingKey="return.save";
        String routingKeyError="abc.save";

        String msg="Hello RabbitMQ  Return Message";

        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties basicProperties, byte[] body) throws IOException {
                System.out.println("-------handle return --------");
                System.out.println("replyCode: "+replyCode);
                System.out.println("replyText: "+replyCode);
                System.out.println("exchange: "+exchange);
                System.out.println("routingKey: "+routingKey);
                System.out.println("basicProperties: "+basicProperties);
                System.out.println("body: "+new String(body));
            }
        });

        channel.basicPublish(exchange,routingKey,true,null,msg.getBytes());
     //   channel.basicPublish(exchange,routingKeyError,true,null,msg.getBytes());
    }
}
