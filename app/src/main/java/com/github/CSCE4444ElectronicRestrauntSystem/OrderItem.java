package com.github.CSCE4444ElectronicRestrauntSystem;

public class OrderItem {
    public String name;
    public String request;
    public float price;

    OrderItem(String name, String request, float price) {
        this.name = name;
        this.request = request;
        this.price = price;
    }
}
