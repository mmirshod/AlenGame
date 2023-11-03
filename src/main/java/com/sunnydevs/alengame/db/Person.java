package com.sunnydevs.alengame.db;

import java.sql.Blob;

public class Person {
    int id, age;
    String name, memo;
    Blob photo, voice;

    Person(int id, String name, int age, Blob photo, Blob voice) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.photo = photo;
        this.voice = voice;
    }

    Person(int id, String name, int age, Blob photo, Blob voice, String memo) {
        this(id, name, age, photo, voice);
        this.memo = memo;
    }

    public Blob getPhoto() {
        return photo;
    }

    public Blob getVoice() {
        return voice;
    }

    public int getAge() {
        return age;
    }

    public int getId() {
        return id;
    }
}
