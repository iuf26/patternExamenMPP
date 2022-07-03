package com.example.be.domain;

import com.example.be.repository.AbstractRepo;
import com.example.be.repository.RepoGame;
import com.example.be.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ServerEndpoint(value = "/scores-board",encoders = MyEncoder.class)
@Component
public class ScoresBoard {



    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());



    @OnMessage
    public void broadcastScoresBoard(String scores, Session session) throws EncodeException, IOException {
        System.out.println("Broadcast global scores");

            for(Session peer : peers){



                    peer.getBasicRemote().sendObject(scores);

            }

    }

    @OnOpen
    public void onOpen (Session peer){
        peers.add(peer);
        System.out.println("Opened websocket sessionn***********************88");
    }

    @OnClose
    public void onClose(Session peer){
        peers.remove(peer);
        System.out.println("Remove session from websocket ");
    }

    @OnError
    public void error(Session peer,Throwable e){
        System.out.println("Erroare ..." +e);
    }
}
