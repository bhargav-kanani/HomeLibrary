package com.example.myapplication.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderResponseModel {

    @SerializedName("orders")
    private List<OrderModel> orders;

    public List<OrderModel> getOrders() {
        return orders;
    }
}
