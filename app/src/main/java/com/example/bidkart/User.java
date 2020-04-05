package com.example.bidkart;

import java.util.ArrayList;

public class User {

    String userId;
    String name;
    String[] categories;

    public User(){

    }

    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String[] getCategories() {
        return categories;
    }
}
