package com.sunnydevs.alengame.db;

import javafx.beans.property.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents a user in the AlenGame application, stored in the database.
 */
public class User extends GetConnection {

    private static User user;

    private final IntegerProperty id;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty age;
    private final ObjectProperty<int[]> contacts;
    private final ObjectProperty<byte[]> userPhoto;
    private final String username;
    private final String password;

    private User(int userId, String firstName, String lastName, int age, int[] contacts, byte[] userPhoto, String password, String username) {
        this.id = new SimpleIntegerProperty(userId);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.age = new SimpleStringProperty(String.valueOf(age));
        this.contacts = new SimpleObjectProperty<>(contacts);
        this.userPhoto = new SimpleObjectProperty<>(userPhoto);
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the user from the database based on the provided username.
     *
     * @param username The username of the user to be retrieved.
     * @return The retrieved User object.
     * @throws SQLException If a database access error occurs.
     */
    public static synchronized User getUser(String username) throws SQLException {
        if (user == null) {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            ResultSet res = preparedStatement.executeQuery();

            if (res.next()) {
                int[] contacts = res.getArray("contacts") != null ? (int[]) res.getArray("contacts").getArray() : null;
                byte[] photo = res.getBytes("user_photo") != null ? res.getBytes("user_photo") : null;
                user = new User(
                        res.getInt("id"),
                        res.getString("first_name"),
                        res.getString("last_name"),
                        res.getInt("age"),
                        contacts,
                        photo,
                        res.getString("password"),
                        res.getString("username")
                );
                return user;
            }
            preparedStatement.close();
        } else return user;
        throw new RuntimeException();
    }

    /**
     * Signs up a new user in the database.
     *
     * @param firstName The first name of the new user.
     * @param lastName  The last name of the new user.
     * @param age       The age of the new user.
     * @param password  The password of the new user.
     */
    public static synchronized void signUp(String firstName, String lastName, int age, String password) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO users (first_name, last_name, username, age, password) VALUES (?, ?, ?, ?, ?)"
            );
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            String username = firstName.toLowerCase().concat(lastName.toLowerCase()).concat(String.valueOf(age));
            preparedStatement.setString(3, username);
            preparedStatement.setInt(4, age);
            preparedStatement.setString(5, password);

            preparedStatement.executeUpdate();
            user = null;
            User.getUser(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the first name of the user in the database.
     *
     * @param firstName The new first name of the user.
     */
    public final void setFirstName(String firstName) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE users SET first_name = ? WHERE id = ?");
            preparedStatement.setString(1, firstName);
            preparedStatement.setInt(2, user.id.get());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the last name of the user in the database.
     *
     * @param lastName The new last name of the user.
     */
    public final void setLastName(String lastName) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE users SET last_name = ? WHERE id = ?");
            preparedStatement.setString(1, lastName);
            preparedStatement.setInt(2, user.id.get());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the age of the user in the database.
     *
     * @param age The new age of the user.
     */
    public final void setAge(int age) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE users SET age = ? WHERE id = ?");
            preparedStatement.setInt(1, age);
            preparedStatement.setInt(2, user.id.get());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the first name property of the User.
     *
     * @return The first name property of the User.
     */
    public final StringProperty firstNameProperty() {
        return user.firstName;
    }

    /**
     * Gets the last name property of the User.
     *
     * @return The last name property of the User.
     */
    public final StringProperty lastNameProperty() {
        return user.lastName;
    }

    /**
     * Gets the user photo property of the User.
     *
     * @return The user photo property of the User.
     */
    public final ObjectProperty<byte[]> userPhotoProperty() {
        return user.userPhoto;
    }

    /**
     * Gets the age property of the User.
     *
     * @return The age property of the User.
     */
    public final StringProperty ageProperty() {
        return user.age;
    }

    /**
     * Gets the username of the User.
     *
     * @return The username of the User.
     */
    public final String username() {
        return username;
    }

    /**
     * Gets the password of the User.
     *
     * @return The password of the User.
     */
    public String password() {
        return password;
    }
}
