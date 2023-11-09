package com.sunnydevs.alengame.db;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public final class Person extends GetConnection {
    private final int id;
    private final String name;
    private final int age;
    private final Blob photo;
    private final String memo;
    private final int groupId;

    private Person(int id, String name, int age, Blob photo, String memo, int groupId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.photo = photo;
        this.memo = memo;
        this.groupId = groupId;
    }

    public static void _new(String name, int age, Blob photo, Blob voice, String memo, int groupId) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO person (name, age, photo, memo, group_id) VALUES (?, ?, ?, ?, ?)");
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, age);
        preparedStatement.setBlob(3, photo);
        preparedStatement.setString(4, memo);
        preparedStatement.setInt(5, groupId);
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

    static public ArrayList<Person> all() throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM person ORDER BY id");
        ResultSet res = preparedStatement.executeQuery();
        ArrayList<Person> allPeople = new ArrayList<>();

        while(res.next()) {
            allPeople.add(_getFromResultSet(res));
        }

        return allPeople;
    }

    public static ArrayList<String> allNames() throws SQLException {
        ArrayList<String> allNames = new ArrayList<>();
        for (Person p : Person.all())
            allNames.add(p.name);

        return allNames;
    }

    static Person _getFromResultSet(ResultSet res) throws SQLException {
        return new Person(
            res.getInt("id"),
            res.getString("name"),
            res.getInt("age"),
            res.getBlob("photo"),
            res.getString("memo"),
            res.getInt("group_id")
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

    public String memo() {
        return memo;
    }

    public int groupId() {
        return groupId;
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
                Objects.equals(this.memo, that.memo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, photo, memo);
    }

    @Override
    public String toString() {
        return "Person[" +
                "id=" + id + ", " +
                "name=" + name + ", " +
                "age=" + age + ", " +
                "photo=" + photo + ", " +
                "memo=" + memo + ']';
    }

}
