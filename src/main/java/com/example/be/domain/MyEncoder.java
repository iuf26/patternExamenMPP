package com.example.be.domain;

import org.springframework.boot.configurationprocessor.json.JSONObject;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.util.List;

public class MyEncoder implements Encoder.Text<String>{


    @Override
    public void init(EndpointConfig endpointConfig) {
        System.out.println("init globals encoder");
    }

    @Override
    public void destroy() {
        System.out.println("destroy globals encoder");
    }




    @Override
    public String encode(String object) throws EncodeException {
        return object.toString();
    }
}
