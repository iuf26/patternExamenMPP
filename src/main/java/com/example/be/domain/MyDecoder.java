package com.example.be.domain;

import org.springframework.boot.configurationprocessor.json.JSONObject;

import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;

public class MyDecoder implements Decoder.Text<Object>{
    @Override
    public Object decode(String s) throws DecodeException {
        JsonObject jsonObject = Json.createReader(new StringReader(s)).readObject();
        return null;
    }

    @Override
    public boolean willDecode(String s) {
        try {
            Json.createReader(new StringReader(s)).readObject();
            return true;
        }
        catch (JsonException ex){
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        System.out.println("init decoder for globals scores");
    }

    @Override
    public void destroy() {
            System.out.println("destroy decoder for global scores");
    }
}
