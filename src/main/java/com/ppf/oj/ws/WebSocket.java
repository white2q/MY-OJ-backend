package com.ppf.oj.ws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

//注册成组件
@Slf4j
@Component
@ServerEndpoint("/ws/submit-results/{questionSubmitId}")
public class WebSocket {

    private Session session;

    private String questionSubmitId;

    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("questionSubmitId") String questionSubmitId) {
        this.session = session;
        this.questionSubmitId = questionSubmitId;
        webSocketSet.add(this);
        log.info("【websocket消息】有新的连接, 总数:{}", webSocketSet.size());
    }

    //前端关闭时一个websocket时
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        log.info("【websocket消息】连接断开, 总数:{}", webSocketSet.size());
    }

    //前端向后端发送消息
    @OnMessage
    public void onMessage(String message) {
        log.info("【websocket消息】收到客户端发来的消息:{}", message);
    }

    public static void sendMessage(String message, String to) {
        for (WebSocket webSocket : webSocketSet) {
            if (to.equals(webSocket.questionSubmitId)) {
                try {
                    webSocket.session.getBasicRemote().sendText(message);
                    break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}