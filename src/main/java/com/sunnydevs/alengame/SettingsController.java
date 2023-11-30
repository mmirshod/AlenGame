package com.sunnydevs.alengame;

import com.sunnydevs.alengame.db.User;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.sql.SQLException;

/**
 * Controller class for the user settings view in the AlenGame application.
 */
public class SettingsController {
    @FXML
    TextField firstName, lastName, age;
    @FXML
    Button edit, addPeople, resetDB;
    @FXML
    ImageView avatar;

    User current_user;

    /**
     * Initializes the user settings view.
     */
    @FXML
    private void initialize() {
        try {
            current_user = User.getUser(null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        firstName.textProperty().bindBidirectional(current_user.firstNameProperty());
        lastName.textProperty().bindBidirectional(current_user.lastNameProperty());
        age.textProperty().bindBidirectional(current_user.ageProperty());
        if (current_user.userPhotoProperty().get() != null)
            avatar.imageProperty().bind(Bindings.createObjectBinding(() ->
                            (current_user.userPhotoProperty().get() != null) ? new Image(new ByteArrayInputStream(current_user.userPhotoProperty().get())) : null,
                    current_user.userPhotoProperty()));

        setListeners();
    }

    /**
     * Sets listeners for the buttons.
     */
    private void setListeners() {
        edit.setOnAction(event -> {

            // If user in view mode:
            if (!firstName.isEditable() && !lastName.isEditable() && !age.isEditable()) {
                edit.setText("Finish");

                // set TextFields to edit mode
                firstName.setEditable(true);
                lastName.setEditable(true);
                age.setEditable(true);
            } else {
                // If user in edit mode:
                edit.setText("Edit");
                // Commit changes to DB
                current_user.setFirstName(firstName.getText());
                current_user.setLastName(lastName.getText());
                current_user.setAge(Integer.parseInt(age.getText()));

                // set TextFields to read-only mode
                firstName.setEditable(false);
                lastName.setEditable(false);
                age.setEditable(false);
            }
        });

        addPeople.setOnAction(event -> {
            try {
                Stage thisStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddPeople.fxml"));
                thisStage.getScene().setRoot(fxmlLoader.load());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        resetDB.setDisable(true);
    }
}
