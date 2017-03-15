package com.shawn.rxretrodemo.model;

import java.util.List;

/**
 * author: Shawn
 * time  : 2016/11/24 19:14
 */

public class User {
    private String name;
    private int age;
    private List<Wife> wifes;

    public User(String name, int age, List<Wife> wifes) {
        this.name = name;
        this.age = age;
        this.wifes = wifes;
    }

}
