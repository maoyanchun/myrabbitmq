package com.micro.rabbitmq.ps;

import com.micro.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Created by mycge on 2018/6/12.
 */
public class Recv2 {
    private static final String QUEUE_NAME = "test_queue_fanout_sms";
    private static final String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] args) throws IOException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        //队列声明
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.basicQos(1);//保证一次只分发一个

        //绑定队列到交换机转发器
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

        //定义一个消费者
        Consumer consumer = new DefaultConsumer(channel){
            //消息到达触发这个方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                String msg = new String(body, "utf-8");
                System.out.println("[2] Recv msg:"+msg);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("[2] done");
                    //手动回执
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }
}
