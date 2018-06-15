package com.micro.rabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 工具类提供静态方法
 *
 * Created by mycge on 2018/6/11.
 */
public class ConnectionUtil {

    /**
     * 获取MQ的连接
     * @return
     */
    public static Connection getConnection(){
        //定义一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置服务地址
        factory.setHost("127.0.0.1");
        //AMQP 5672
        factory.setPort(5672);
        //vhost
        factory.setVirtualHost("/vhost_myc");
        //用户名
        factory.setUsername("user_myc");
        //密码
        factory.setPassword("user_myc");

        try {
            return factory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }
}
