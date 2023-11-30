package com.sunnydevs.alengame;

import com.sunnydevs.alengame.db.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Controller class for the sign-in view in the AlenGame application.
 */
public class SignInController {
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    Button signInBtn, signUpBtn;

    /**
     * Initializes the sign-in view.
     */
    @FXML
    void initialize() {
        signInBtn.setOnAction(signInHandler);
        signUpBtn.setOnAction(signUpHandler);
    }

    /**
     * Handles the sign-up button action.
     */
    EventHandler<ActionEvent> signUpHandler = e -> {
        try {
            ((Button) e.getSource()).getScene().setRoot((new FXMLLoader(getClass().getResource("SignUp.fxml")).load()));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    };

    /**
     * Handles the sign-in button action.
     */
    EventHandler<ActionEvent> signInHandler = event -> {
        String pwd = password.getText();
        String user = username.getText();
        try {
            System.out.println(User.getUser(user).password());
            System.out.println(PasswordManager.hashPassword(pwd));
            System.out.println(PasswordManager.checkPassword(pwd, User.getUser(user).password()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            if (!PasswordManager.checkPassword(pwd, User.getUser(user).password())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText("Incorrect Password");
                alert.setContentText("The entered password is incorrect. Please try again.");

                alert.showAndWait();
            } else {
                Stage thisStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home.fxml"));
                thisStage.getScene().setRoot(fxmlLoader.load());
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    };
}
