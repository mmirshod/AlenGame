package com.sunnydevs.alengame.db;

import javafx.beans.property.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User extends GetConnection {
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

    public static synchronized User signUp(String firstName, String lastName, int age, String password) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO users (first_name, last_name, username, age, password) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            String username = firstName.toLowerCase().concat(lastName.toLowerCase()).concat(String.valueOf(age));
            preparedStatement.setString(3, username);
            preparedStatement.setInt(4, age);
            preparedStatement.setString(5, password);

            preparedStatement.executeUpdate();
            user = null;
            return User.getUser(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
// #2E3348
    static User user;

    private final IntegerProperty id;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty age;
    private final ObjectProperty<int[]> contacts;
    private final ObjectProperty<byte[]> userPhoto;
    private final String username;
    private final String password;

    public final StringProperty firstNameProperty() {
        return user.firstName;
    }

    public final StringProperty lastNameProperty() {
        return user.lastName;
    }

    public final ObjectProperty<byte[]> userPhotoProperty() {
        return user.userPhoto;
    }

    public final StringProperty ageProperty() {
        return user.age;
    }
    public final String username() {
        return username;
    }

    public String password() {
        return password;
    }
}
