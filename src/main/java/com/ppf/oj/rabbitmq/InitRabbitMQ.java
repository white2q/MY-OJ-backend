package com.ppf.oj.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * rabbitmq 启动类
 */

@Slf4j
@Component
public class InitRabbitMQ {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${codeSandbox.type}")
    private String key;

    private final String QUEUE_NAME = "code_queue";
    private final String EXCHANGE_NAME = "code_exchange";

    @PostConstruct
    public void doInit(){
        ConnectionFactory connectionFactory = new ConnectionFactory();
        try {
            connectionFactory.setHost(host);
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            // direct 发给指定route-key（还有广播，主题两种）
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            // 绑定queue到交换机上
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, key);
            log.info("【O】消息队列启动成功");
        } catch (IOException | TimeoutException e) {
            log.error("【X】消息队列启动失败", e);
        }
    }
}
