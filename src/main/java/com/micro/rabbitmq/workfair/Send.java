package com.micro.rabbitmq.workfair;

import com.micro.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *                  |---c1
 * p------Queue-----|
 *                  |--c2
 *
 * Created by mycge on 2018/6/12.
 */
public class Send {
    private static final String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException {
        //获取连接
        Connection connection = ConnectionUtil.getConnection();
        //获取channel
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        /**
         * 每个消费者发送确认消息之前，消费队列不会发送下一个消息到消费者，一次只处理一个消息-------
         */
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);

        for(int i=0; i<50; i++){
            String msg = "hello "+i;
            System.out.println("[WQ]send "+msg);

            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            Thread.sleep(i*20);
        }

        channel.close();
        connection.close();
    }
}
