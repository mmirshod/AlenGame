package com.sunnydevs.alengame.db;

import javafx.scene.image.Image;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Model representation of a person in the AlenGame application, stored in the database.
 */
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

    /**
     * Creates a new Person entry in the database.
     *
     * @param name    The name of the person.
     * @param userId  The user ID associated with the person.
     * @param age     The age of the person.
     * @param photo   The photo of the person in byte array format.
     * @param memo    Additional information or memo about the person.
     * @param groupId The group ID associated with the person.
     * @throws SQLException If a database access error occurs.
     */
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

    /**
     * Deletes a person entry from the database based on the provided ID.
     *
     * @param id The ID of the person to be deleted.
     * @throws SQLException If a database access error occurs.
     */
    public static void _delete(Integer id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM person WHERE id=?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    /**
     * Retrieves a Person from the database based on the provided ID.
     *
     * @param id The ID of the person to be retrieved.
     * @return The retrieved Person object.
     * @throws SQLException If a database access error occurs.
     */
    public static Person get(Integer id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM person WHERE id=?");
        preparedStatement.setInt(1, id);
        ResultSet res = preparedStatement.executeQuery();
        preparedStatement.close();
        return _getFromResultSet(res);
    }

    /**
     * Retrieves a Person from the database based on the provided name.
     *
     * @param name The name of the person to be retrieved.
     * @return The retrieved Person object.
     * @throws SQLException If a database access error occurs.
     */
    public static Person getByName(String name) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM person WHERE name=?");
        statement.setString(1, name);
        ResultSet res = statement.executeQuery();
        if (res.next())
            return _getFromResultSet(res);
        throw new RuntimeException();
    }

    /**
     * Retrieves the first Person entry from the database.
     *
     * @return The first Person entry.
     * @throws SQLException If a database access error occurs.
     */
    public static Person first() throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM person ORDER BY id LIMIT 1");
        ResultSet res = preparedStatement.executeQuery();
        preparedStatement.close();
        return _getFromResultSet(res);
    }

    /**
     * Retrieves all Person entries from the database.
     *
     * @return An ArrayList of all Person objects.
     * @throws SQLException If a database access error occurs.
     */
    static public ArrayList<Person> all() throws SQLException {
        PreparedStatement st = conn.prepareStatement("SELECT * from person where group_id = 1;");
        ResultSet res = st.executeQuery();

        ArrayList<Person> allPeople = new ArrayList<>();
        while(res.next())
            allPeople.add(_getFromResultSet(res));

        return allPeople;
    }

    /**
     * Generates a data set of Person objects for a quiz based on the provided group ID.
     *
     * @param groupId The group ID for the quiz data set.
     * @return An ArrayList of Person objects for the quiz.
     * @throws SQLException If a database access error occurs.
     */
    static public ArrayList<Person> generateDataSetForQuiz(int groupId) throws SQLException {
        PreparedStatement st = conn.prepareStatement("SELECT * from person where group_id = ? ORDER BY random() LIMIT 10;");
        st.setInt(1, groupId);
        ResultSet res = st.executeQuery();

        ArrayList<Person> allPeople = new ArrayList<>();
        while(res.next())
            allPeople.add(_getFromResultSet(res));

        return allPeople;
    }

    /**
     * Retrieves all names of Person entries from the database.
     *
     * @return An ArrayList of names of all Person entries.
     * @throws SQLException If a database access error occurs.
     */
    public static ArrayList<String> allNames() throws SQLException {
        ArrayList<String> allNames = new ArrayList<>();
        for (Person p : Person.all())
            allNames.add(p.name);

        return allNames;
    }

    /**
     * Creates a Person object from a ResultSet.
     *
     * @param res The ResultSet containing the Person data.
     * @return The created Person object.
     * @throws SQLException If a database access error occurs.
     */
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

    /**
     * Gets the ID of the Person.
     *
     * @return int The ID of the Person.
     */
    public int id() {
        return id;
    }

    /**
     * Gets the name of the Person.
     *
     * @return String The name of the Person.
     */
    public String name() {
        return name;
    }

    /**
     * Gets the photo of the Person as a JavaFX Image.
     *
     * @return The photo of the Person.
     */
    public javafx.scene.image.Image photo() {
        return new Image(new java.io.ByteArrayInputStream(this.photo));
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
