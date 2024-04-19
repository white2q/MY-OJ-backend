package com.ppf.oj.rabbitmq;

import cn.hutool.json.JSONUtil;
import com.ppf.oj.judge.JudgeService;
import com.ppf.oj.model.enums.QuestionSubmitStatusEnum;
import com.ppf.oj.model.vo.QuestionSubmitAddResponse;
import com.ppf.oj.ws.WebSocket;
import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 消费者
 */
@Slf4j
@Component
public class MessageConsumer {

    @Resource
    private JudgeService judgeService;

    @Resource
    private WebSocket webSocket;
    // 指定程序监听的消息队列和确认机制
    @SneakyThrows
    @RabbitListener(queues = {"code_queue"}, ackMode = "MANUAL")
    public void receiveMessage(String questionSubmitId, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        try {
            // 消费成功 ， 返回结果
            QuestionSubmitAddResponse questionSubmitAddResponse = judgeService.doJudge(Long.parseLong(questionSubmitId));
            webSocket.sendMessage(JSONUtil.toJsonStr(questionSubmitAddResponse), questionSubmitId);
            log.info("消息消费成功，返回结果：{}", questionSubmitAddResponse);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            // 消费失败 ， 返回结果
            webSocket.sendMessage(QuestionSubmitStatusEnum.SYSTEM_ERROR.getText(), questionSubmitId);
            log.info("消息消费失败，返回结果：{}", e.getMessage());
            channel.basicNack(deliveryTag, false, false);
        }
    }
}
