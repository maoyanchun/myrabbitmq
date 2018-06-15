package com.micro.rabbitmq.simple;

import com.micro.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by mycge on 2018/6/11.
 */
public class Send {

    private static final String QUEUE_NAME = "test_simple_queue";

    public static void main(String[] args) {

        Connection connection = ConnectionUtil.getConnection();
        Channel channel = null;

        try {
            //从连接中创建一个通道
            channel = connection.createChannel();
            //创建队列声明
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            String msg = "hello simple !";
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

            System.out.println("--send msg: "+msg);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                channel.close();
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }
}
