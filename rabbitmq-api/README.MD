1.###################################交换机属性########################
name :交换机名称
Type :交换机类型 direct,topic,fanout,headers
Durability :是否需要持久化,true 为持久化
Auto  Delete :当最后一个绑定Exchange上的对列删除后,自动删除该Exchange
Internal :当前exchange是否用于RabbitMQ内部使用,默认为False
Arguments :扩展参数,用于扩展AMQP协议自制电话使用

direct Exchange:
 所有发送Direct Exchange 的消息被转发到routingKey中的Queue
注意:
    Direct模式可以使用RabbitMq自带的Exchange :default Exchange,所以不需要将Exchange进行任何绑定操作,消息传递时
    ,RoutingKey必须完全匹配才会被对列接受,否则该消息活被抛弃

Topic Exchange
 所有发送到Topic Exchange 的消息被转发到所有关心RoutingKey中指定Topic 的Queue上
 
  Exchange 将RouteKey和某Topic进行模糊匹配,此时对列需要绑定一个Topic
注意:可以使用通配符进行模糊匹配
  "#" 匹配一个或多个词
  "*"只能匹配一个单词
  
Fanout Exchange
  不处理路由键,只需要简单的将对列绑定到交换机上
  发送到交换机的消息都会被转发到与该交换机绑定的所有对列上
  Fanout交换机转发消息是最快的
  
2.########Binding-绑定##########
Exchange 和Exchange,Queue之间的连接关系

Binding中 可以包含RoutingKey或者参数

3.########Queue消息对列############
消息对列 ,实际存储消息数据
Durability :是否持久化,Durable:是,Transaient:否

Auto delete :选择yes,代表当最后一个监听被移除之后,该Queue会自动删除

4.########Message -消息#######

5.#########虚拟主机##########
虚拟地址,用于进行逻辑隔离,最上层的消息路由
一个Virtural Host里面可以有若干搞个Exchange 和Queue
同一个Virtual Host 里面不能有相同名称的Exchange 或者Queue
 
 
#######消息如何保障100%的投递成功############
什么是生产端的可靠性投递
   保障消息的成功发出
   保障MQ节点的成功接收
   发送端收到MQ节点(Broker)确认应答
   完善的消息进行补偿机制
BAT/TMD 互联网大厂的解决方案'
消息落库,对消息状态进行打标
消息的延迟投递,做二次确认,回调检查
