package com.micro.rabbitmq.confirm;

import com.micro.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * confirm模式  批量多条
 * 发完后确认，串行往下走
 *
 * Created by mycge on 2018/6/13.
 */
public class Send2 {
    private static final String QUEUE_NAME = "test_queue_confirm2";

    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //生产者调用confirmSelect 将channel设置为confirm模式 注意
        channel.confirmSelect();


        String msgString = "hello confirm message batch!";
        //批量发送
        for(int i=0; i<10; i++){
            channel.basicPublish("", QUEUE_NAME, null, msgString.getBytes());
        }
        //确认
        if(!channel.waitForConfirms()){
            System.out.println("message send failed");
        }else {
            System.out.println("message send ok");
        }

        channel.close();
        connection.close();
    }
}
