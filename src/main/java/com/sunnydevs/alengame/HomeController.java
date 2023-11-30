package com.sunnydevs.alengame;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller class for the home screen.
 */
public class HomeController {

    @FXML
    private Button playBtn, playBtn1, settingsBtn;

    /**
     * Initializes the controller.
     */
    @FXML
    private void initialize() {
        playBtn.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Stage thisStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("memo-quiz.fxml"));
                    thisStage.getScene().setRoot(fxmlLoader.load());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        playBtn1.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Stage thisStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("photo-quiz.fxml"));
                    thisStage.getScene().setRoot(fxmlLoader.load());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        settingsBtn.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Stage thisStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UserSettings.fxml"));
                    thisStage.getScene().setRoot(fxmlLoader.load());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
