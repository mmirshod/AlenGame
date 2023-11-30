package com.sunnydevs.alengame.db;

import javafx.scene.image.Image;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public final class Person extends GetConnection {
    private final int id;
    private final String name;
    private final int age;
    private final byte[] photo;
    private final String memo;
    private final int groupId;
    private final int userId;

    private Person(int id, int userId, String name, int age, byte[] photo, String memo, int groupId) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.age = age;
        this.photo = photo;
        this.memo = memo;
        this.groupId = groupId;
    }

    public static void _new(String name, int userId, int age, byte[] photo, String memo, int groupId) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO person (name, age, photo, memo, group_id, user_id) VALUES (?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, age);
        preparedStatement.setBytes(3, photo);
        preparedStatement.setString(4, memo);
        preparedStatement.setInt(5, groupId);
        preparedStatement.setInt(6, userId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public static void _delete(Integer id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM person WHERE id=?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public static Person get(Integer id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM person WHERE id=?");
        preparedStatement.setInt(1, id);
        ResultSet res = preparedStatement.executeQuery();
        preparedStatement.close();
        return _getFromResultSet(res);
    }

    public static Person getByName(String name) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM person WHERE name=?");
        statement.setString(1, name);
        ResultSet res = statement.executeQuery();
        if (res.next())
            return _getFromResultSet(res);
        throw new RuntimeException();
    }

    public static Person first() throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM person ORDER BY id LIMIT 1");
        ResultSet res = preparedStatement.executeQuery();
        preparedStatement.close();
        return _getFromResultSet(res);
    }

    static public ArrayList<Person> all() throws SQLException {
        PreparedStatement st = conn.prepareStatement("SELECT * from person where group_id = 1;");
        ResultSet res = st.executeQuery();

        ArrayList<Person> allPeople = new ArrayList<>();
        while(res.next())
            allPeople.add(_getFromResultSet(res));

        return allPeople;
    }

    static public ArrayList<Person> generateDataSetForQuiz(int groupId) throws SQLException {
        PreparedStatement st = conn.prepareStatement("SELECT * from person where group_id = ? ORDER BY random() LIMIT 10;");
        st.setInt(1, groupId);
        ResultSet res = st.executeQuery();

        ArrayList<Person> allPeople = new ArrayList<>();
        while(res.next())
            allPeople.add(_getFromResultSet(res));

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
                    res.getInt("user_id"),
                    res.getString("name"),
                    res.getInt("age"),
                    res.getBytes("photo"),
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

    public javafx.scene.image.Image photo() {
        return new Image(new java.io.ByteArrayInputStream(this.photo));
    }

    public String memo() {
        return memo;
    }

    public int groupId() {
        return groupId;
    }

    public int userId() {
        return userId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Person) obj;
        return this.id == that.id &&
                Objects.equals(this.name, that.name) &&
                this.age == that.age &&
                Arrays.equals(this.photo, that.photo) &&
                Objects.equals(this.memo, that.memo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, Arrays.hashCode(photo), memo);
    }

    @Override
    public String toString() {
        return "Person[" +
                "id=" + id + ", " +
                "name=" + name + ", " +
                "age=" + age + ", " +
                "memo=" + memo + ']';
    }

}
