package com.micro.rabbitmq.topic;

import com.micro.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by mycge on 2018/6/12.
 */
public class Send {
    private static final String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        //exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        String msg = "商品...";
        channel.basicPublish(EXCHANGE_NAME, "goods.update", null, msg.getBytes());

        System.out.println("---send "+msg);

        channel.close();
        connection.close();
    }
}
