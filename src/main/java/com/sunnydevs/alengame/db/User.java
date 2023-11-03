package com.sunnydevs.alengame.db;

import java.sql.Blob;

public class User {
    String name;
    int age;
    Blob avatar;

    User (String name, int age) {
        this.name = name;
        this.age = age;
    }

    User (String name, int age, Blob avatar) {
        this(name, age);
        this.avatar = avatar;
    }

    public int getAge() {
        return age;
    }

    public Blob getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }
}
