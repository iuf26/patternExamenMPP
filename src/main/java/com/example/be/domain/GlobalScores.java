package com.example.be.domain;

import org.springframework.boot.configurationprocessor.json.JSONObject;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;

public class GlobalScores  {
private JsonObject json;

public GlobalScores(JsonObject json){
    this.json = json;
}

    public JsonObject getJson() {
        return json;
    }

    public void setJson(JsonObject json) {
        this.json = json;
    }

    @Override
    public String toString(){
        StringWriter writer = new StringWriter();
        Json.createWriter(writer).write(json);
        return writer.toString();
    }
}
