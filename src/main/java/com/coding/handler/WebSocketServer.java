package com.coding.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@ServerEndpoint("/ws/{userId}")
@Component
public class WebSocketServer {
    /**
     * 用来保存websocket连接信息，供统计查询当前连接数使用
     */

    private static final ConcurrentMap<String, WebSocketServer> USER_CLIENT_MAP = new ConcurrentHashMap<>();
    private Session session;

    /**
     * 连接建立成功调用的方法
     *
     * @param session 链接session
     */
    @OnOpen
    public void onOpen(Session session) {
        USER_CLIENT_MAP.put(session.getId(), this);
        this.session = session;
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 消息内容
     * @param session session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.debug("websocket连接onMessage。{},{},{}", message, this.toString(), session.getId());
        String key = this.session.getId();
        if (!USER_CLIENT_MAP.containsKey(key)) {
            this.close();
            return;
        }
        push("token", "收到你的消息:" + message);
    }

    /**
     * 发生异常时的处理
     *
     * @param session   客户端session
     * @param throwable session
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        if (this.session != null && this.session.isOpen()) {
            log.error("websocket连接onError。inputSession：{}-localSession：{}", session.getId(), this.toString(), throwable);
            this.close();
        } else {
            log.debug("已经关闭的websocket连接发生异常！inputSession：{}-localSession：{}", session.getId(), this.toString(), throwable);
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        log.debug("websocket连接onClose。{}", this.toString());
        //然后关闭
        this.close();
    }

    public void push(String token, String message) {
        if (StringUtils.isEmpty(token)) {
            throw new RuntimeException("token must not be null. token=" + token + ", message=" + message);
        }

        if (StringUtils.isEmpty(message)) {
            throw new RuntimeException("message must not be null. token=" + token + ", message=" + message);
        }

        if (!session.isOpen()) {
            this.close();
            throw new RuntimeException("session is closed. token=" + token + ", message=" + message);
        }

        //发送消息
        this.sendMessage(message);

    }

    /**
     * 推送消息给客户端
     *
     * @param message 消息内容
     */
    private void sendMessage(String message) {
        try {
            //使用同步块和同步方法发送。看是否能防止产生IllegalStateException异常
            //modify by caoshuo at 200506
            synchronized (session) {
                session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            log.error("websocket连接发送客户端发送消息时异常！{}-{}", message, this.toString(), e);
        }
    }

    /**
     * 关闭session连接
     */
    private void close() {
        //从本机map中移除连接信息
        USER_CLIENT_MAP.remove(this.session.getId());
        if (session == null) {
            log.debug("websocket连接关闭完成。{}", this.toString());
            return;
        }

        try {
            if (session.isOpen()) {
                session.close();
            }
            log.info("连接已经关闭");
        } catch (IOException e) {
            log.error("websocket连接关闭时异常。{}", this.toString(), e);
        }
    }

    public void sendToAll(Map<String, Object> map) {
        USER_CLIENT_MAP.values().forEach(item -> {
            item.sendMessage(map.toString());
        });
    }
}
