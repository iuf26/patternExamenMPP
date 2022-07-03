package com.example.be.domain;


import org.springframework.stereotype.Component;

import javax.websocket.*;
import java.net.URI;
import java.util.List;

@ClientEndpoint

public class WebSocketClientEndpoint {
    Session userSession = null;
    private MessageHandler messageHandler;

    public WebSocketClientEndpoint(URI endpointURI) {
        try {
            System.out.println("Init client websocket");
            WebSocketContainer container = ContainerProvider
                    .getWebSocketContainer();
            container.connectToServer(this, endpointURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Callback hook for Connection open events.
     *
     * @param userSession
     *            the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession) {
        this.userSession = userSession;
    }

    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        this.userSession = null;
    }

    @OnMessage
    public void onMessage(String message) {
        if (this.messageHandler != null)
            this.messageHandler.handleMessage(message);
    }

    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }


    public void sendMessage(String message) {

        System.out.println("IN send meesssage webclient");
        userSession.getAsyncRemote().sendObject(message);
    }

    public static interface MessageHandler {
        public void handleMessage(String message);
    }
}