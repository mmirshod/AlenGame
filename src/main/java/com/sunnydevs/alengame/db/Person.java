package com.sunnydevs.alengame.db;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public final class Person extends GetConnection {
    private final int id;
    private final String name;
    private final int age;
    private final Blob photo;
    private final Blob voice;
    private final String memo;

    private Person(int id, String name, int age, Blob photo, Blob voice, String memo) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.photo = photo;
        this.voice = voice;
        this.memo = memo;
    }

    public static void _new(Person personSchema) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO person (name, age, photo, voice, memo) VALUES (?, ?, ?, ?, ?)");
        preparedStatement.setString(1, personSchema.name());
        preparedStatement.setInt(2, personSchema.age());
        preparedStatement.setBlob(3, personSchema.photo());
        preparedStatement.setBlob(4, personSchema.voice());
        preparedStatement.setString(5, personSchema.memo());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public static void _delete(Integer id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM person WHERE id=%d".formatted(id));
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public static Person get(Integer id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM person WHERE id=%d".formatted(id));
        ResultSet res = preparedStatement.executeQuery();
        preparedStatement.close();
        return _getFromResultSet(res);
    }

    public static Person first() throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM person ORDER BY id LIMIT 1");
        ResultSet res = preparedStatement.executeQuery();
        preparedStatement.close();
        return _getFromResultSet(res);
    }

    static Person _getFromResultSet(ResultSet res) throws SQLException {
        return new Person(
            res.getInt("id"),
            res.getString("name"),
            res.getInt("age"),
            res.getBlob("photo"),
            res.getBlob("voice"),
            res.getString("memo")
        );
    }

    public int id() {
        return id;
    }

    public String name() {
        return name;
    }

    public int age() {
        return age;
    }

    public Blob photo() {
        return photo;
    }

    public Blob voice() {
        return voice;
    }

    public String memo() {
        return memo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Person) obj;
        return this.id == that.id &&
                Objects.equals(this.name, that.name) &&
                this.age == that.age &&
                Objects.equals(this.photo, that.photo) &&
                Objects.equals(this.voice, that.voice) &&
                Objects.equals(this.memo, that.memo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, photo, voice, memo);
    }

    @Override
    public String toString() {
        return "Person[" +
                "id=" + id + ", " +
                "name=" + name + ", " +
                "age=" + age + ", " +
                "photo=" + photo + ", " +
                "voice=" + voice + ", " +
                "memo=" + memo + ']';
    }

}
