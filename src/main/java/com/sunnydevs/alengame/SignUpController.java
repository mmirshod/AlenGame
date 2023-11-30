package com.sunnydevs.alengame;

import com.sunnydevs.alengame.db.User;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Controller class for the sign-up view in the AlenGame application.
 */
public final class SignUpController {
    @FXML
    TextField username, age, firstName, lastName;
    @FXML
    PasswordField password;
    @FXML
    Button signInBtn, signUpBtn;

    /**
     * Initializes the sign-up view.
     */
    @FXML
    private void initialize() {
        bindUsernameProperty();
        signUpBtn.setOnAction(signUpHandler);
        signInBtn.setOnAction(signInHandler);
    }

    /**
     * Handles the sign-up button action.
     */
    private final EventHandler<ActionEvent> signUpHandler = e -> {
        String firstNameInput = firstName.getText();
        String lastNameInput = lastName.getText();
        int ageInput = Integer.parseInt(age.getText());
        String password = PasswordManager.hashPassword(this.password.getText());

        User.signUp(firstNameInput, lastNameInput, ageInput, password);
    };

    /**
     * Handles the sign-in button action.
     */
    private final EventHandler<ActionEvent> signInHandler = e -> {
        try {
            ((Button) e.getSource()).getScene().setRoot((new FXMLLoader(getClass().getResource("SignIn.fxml")).load()));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    };

    /**
     * Binds the username property based on the values of first name, last name, and age.
     */
    private void bindUsernameProperty() {
        StringBinding concatenatedValue = Bindings.createStringBinding(
                () -> String.format("%s%s%s",
                        firstName.getText().toLowerCase(),
                        lastName.getText().toLowerCase(),
                        age.getText()
                ),
                firstName.textProperty(), lastName.textProperty(), age.textProperty()
        );

        // Bind the textProperty of username to the concatenated value
        username.textProperty().bind(concatenatedValue);
    }
}
