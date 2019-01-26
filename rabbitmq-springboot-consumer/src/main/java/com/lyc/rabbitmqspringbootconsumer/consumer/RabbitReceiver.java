package com.lyc.rabbitmqspringbootconsumer.consumer;

import com.lyc.rabbitmqspringbootconsumer.entity.Order;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @Auther: Jhon Li
 * @Date: 2019/1/23 20:43
 * @Description:
 */
@Component
public class RabbitReceiver {
/*

    @Value("${spring.rabbitmq.listener.order.queue.name}")
    private String queueName;
    @Value("${spring.rabbitmq.listener.order.queue.durable}")
    private String queueDurable;
    @Value("${spring.rabbitmq.listener.order.exchange.name}")
    private String exchangeName;
    @Value("${spring.rabbitmq.listener.order.exchange.durable}")
    private String exchangeDurable;
    @Value("${spring.rabbitmq.listener.order.exchange.type}")
    private String exchangeType;
    @Value("${spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions}")
    private String ignoreDeclarationExceptions;
    @Value("${spring.rabbitmq.listener.order.key}")
    private String key;
*/



    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(
                    value = "queue-1",durable = "true"),
            exchange = @Exchange(value = "exchange-1",durable = "true",type="topic",ignoreDeclarationExceptions = "true"),
             key = "springboot.*"
    ))
    public void onMessage(Message message , Channel channel) throws Exception {
        System.err.println("消费端 :"+ message.getPayload());
        System.err.println("消费端 Payload :"+ message.getPayload());
        Long deliveryTag =(Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        //手工ack
        channel.basicAck(deliveryTag,false);
    }

    /**
     * spring.rabbitmq.listener.order.queue.name=queue-2
     spring.rabbitmq.listener.order.queue.durable=true
     spring.rabbitmq.listener.order.exchange.name=exchange-1
     spring.rabbitmq.listener.order.exchange.durable=true
     spring.rabbitmq.listener.order.exchange.type=topic
     spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions=true
     spring.rabbitmq.listener.order.key=springboot.*
     * @param order
     * @param channel
     * @param heders
     * @throws Exception
     */

    //监听对象
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(
                    value = "${spring.rabbitmq.listener.order.queue.name}",durable = "${spring.rabbitmq.listener.order.queue.durable}"),
            exchange = @Exchange(value = "${spring.rabbitmq.listener.order.exchange.name}",durable = "${spring.rabbitmq.listener.order.exchange.durable}",type="${spring.rabbitmq.listener.order.exchange.type}",ignoreDeclarationExceptions = "${spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions}"),
            key = "${spring.rabbitmq.listener.order.key}"
    ))
    @RabbitHandler            //需要手工签收 channel是必需有的
    public void onOrderMessage(@Payload com.lyc.rabbitmqspringbootconsumer.entity.Order order, Channel channel, @Headers Map<String,Object> heders) throws Exception {
        System.err.println("消费端 order:"+ order.getId());
        Long deliveryTag =(Long) heders.get(AmqpHeaders.DELIVERY_TAG);
        //手工ack
        channel.basicAck(deliveryTag,false);
    }
}
