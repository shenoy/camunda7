package com.example.workflow;


import java.io.Serializable;

public class Order implements Serializable {
    public String name = "Order";

    public String getName() {
        return name;
    }
}
