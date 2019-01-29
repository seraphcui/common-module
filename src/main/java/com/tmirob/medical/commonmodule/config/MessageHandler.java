package com.tmirob.medical.commonmodule.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Seraph(yao.cui@tmirob.com)
 * @date 2019/1/25
 */
@Component
@SuppressWarnings("unchecked")
public class MessageHandler extends TextWebSocketHandler {
    private List<WebSocketSession> clients = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        clients.add(session);
        System.out.println("uri :" + session.getUri());
        System.out.println("连接建立: " + session.getId());
        System.out.println("current session: " + clients.size());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        clients.remove(session);
        System.out.println("断开连接: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message){
        String payload = message.getPayload();
        ObjectMapper om = new ObjectMapper();
        try {
            Map<String, String> map = om.readValue(payload, new TypeReference<HashMap<String, String>>() {
            });
            System.out.println("接受到的数据" + map);
           for(WebSocketSession wss: clients){
                System.out.println("发送消息给: " + session.getId());
               wss.sendMessage(new TextMessage("服务器返回收到的信息," + payload));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
