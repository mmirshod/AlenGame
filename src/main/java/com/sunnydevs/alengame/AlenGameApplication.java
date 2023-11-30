package com.sunnydevs.alengame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main application class for the AlenGame.
 */
public class AlenGameApplication extends Application {

    /**
     * The main entry point for the AlenGame application.
     *
     * @param stage The primary stage for the application.
     * @throws IOException If an error occurs during loading of the FXML file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AlenGameApplication.class.getResource("SignUp.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("AlenGame");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    /**
     * The main method to launch the JavaFX application.
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        launch(args);
    }
}
