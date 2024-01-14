package com.example.myapplication.models;

public class StateModel {
    private String name;

    public StateModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return name;
    }
}
