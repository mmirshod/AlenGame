package com.sunnydevs.alengame;

import com.sunnydevs.alengame.db.Person;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

/**
 * Controller class for the "AddPeople.fxml" view.
 */
public class AddPeopleController {

    @FXML
    private ImageView imgView;

    @FXML
    private TextField nameField;

    @FXML
    private TextField ageField;

    @FXML
    private TextField quoteField;

    @FXML
    private Button selectImg;

    @FXML
    private Button add;

    private File selectedFile;

    /**
     * Initializes the controller, setting up event handlers for buttons.
     */
    @FXML
    private void initialize() {
        selectImg.setOnAction(e -> handleSelectImage());
        add.setOnAction(e -> handleAdd());
    }

    /**
     * Handles the event when the user clicks the "Select Image" button.
     * Opens a file chooser dialog to select an image file.
     */
    @FXML
    private void handleSelectImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg")
        );

        selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            // Set the selected image to the ImageView
            imgView.setImage(new Image(selectedFile.toURI().toString()));
        }
    }

    /**
     * Handles the event when the user clicks the "Add" button.
     * Adds a new person to the database based on the entered information.
     */
    @FXML
    private void handleAdd() {
        try {
            // You can access nameField.getText(), ageField.getText(), quoteField.getText(), and imgView.getImage() here
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String memo = quoteField.getText();
            byte[] pic = Files.readAllBytes(selectedFile.toPath());
            Person._new(name, 1, age, pic, memo, 1);

            // Redirect to the home screen after adding the person
            add.getScene().setRoot((new FXMLLoader(getClass().getResource("home.fxml"))).load());
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
