package com.example.server_flowers.controller;

import com.example.server_flowers.model.Flower;

public class PlantMessage {
    private String type; // CREATE, UPDATE, DELETE
    private Flower data; // The plant data (for CREATE and UPDATE)


    public PlantMessage(String type, Flower data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Flower getData() {
        return data;
    }

    public void setData(Flower data) {
        this.data = data;
    }
}
