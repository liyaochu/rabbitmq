package com.lyc.rabbitmqspringbootconsumer.entity;

import java.io.Serializable;

/**
 * @Auther: Jhon Li
 * @Date: 2019/1/24 11:10
 * @Description:
 */
public class Order implements Serializable{
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Order() {
    }
}