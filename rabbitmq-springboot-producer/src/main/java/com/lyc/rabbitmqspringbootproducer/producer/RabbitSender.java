package com.lyc.rabbitmqspringbootproducer.producer;

import com.lyc.rabbitmqspringbootproducer.entity.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Auther: Jhon Li
 * @Date: 2019/1/23 12:13
 * @Description:
 */
@Component
public class RabbitSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    //回调函数: confirm确认
    final ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            System.err.println("correlationData: " + correlationData);
            System.err.println("ack: " + ack);
            if(!ack){
                System.err.println("异常处理....");
            }
        }
    };

    //回调函数: return返回
    final RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(org.springframework.amqp.core.Message message, int replyCode, String replyText,
                                    String exchange, String routingKey) {
            System.err.println("return exchange: " + exchange + ", routingKey: "
                    + routingKey + ", replyCode: " + replyCode + ", replyText: " + replyText);
        }
    };

    public void send(Object message,Map<String,Object> properties){
        MessageHeaders messageHeaders=new MessageHeaders(properties);
        Message mess= MessageBuilder.createMessage(message,messageHeaders);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        CorrelationData correlationData=new CorrelationData();
        correlationData.setId("123456789");  //id要全局唯一
        rabbitTemplate.convertAndSend("exchange-1","springboot.hello",mess,correlationData);
    }

    public void sendOrder(Order order){
      /*  MessageHeaders messageHeaders=new MessageHeaders(properties);
        Message mess= MessageBuilder.createMessage(order,messageHeaders);*/
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        CorrelationData correlationData=new CorrelationData();
        correlationData.setId("12345678");  //id要全局唯一
        rabbitTemplate.convertAndSend("exchange-2","springboot.abc",order,correlationData);
    }
}
