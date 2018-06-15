package com.micro.rabbitmq.confirm;

import com.micro.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Created by mycge on 2018/6/12.
 */
public class Recv {
    private static final String QUEUE_NAME = "test_queue_confirm3";

    public static void main(String[] args) throws IOException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.basicConsume(QUEUE_NAME, true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("recv[confirm] msg:"+new String(body, "utf-8"));
            }
        });

    }
}
