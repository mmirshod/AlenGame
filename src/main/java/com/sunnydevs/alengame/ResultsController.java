package com.sunnydevs.alengame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller class for the quiz results view in the AlenGame application.
 */
public class ResultsController {

    @FXML
    Label correctNum, incorrectNum;
    @FXML
    Text resText;
    @FXML
    ProgressIndicator incorrectProgress, correctProgress;

    /**
     * Initializes the results view based on the number of correct answers.
     */
    @FXML
    void initialize() {
        int correct = Integer.parseInt(correctNum.getText());
        if (correct < 2) {
            resText.setText("Oh no.. It seems that you need to improve your brain. Talk to your nears daily!");
        } else if (correct < 5) {
            resText.setText("Oops..! You have scored less marks. It seems like you need to talk more with your nears and dears ones.");
        } else if (correct <= 7) {
            resText.setText("Good. A bit more improvement might help you to get better results. Practice is the key to success. Check your results here.");
        } else if (correct == 8 || correct == 9) {
            resText.setText("Congratulations! You have good memory.");
        } else if (correct == 10) {
            resText.setText("Congratulations! You can boast that you have great memory.");
        }
    }

    /**
     * Sets the number of correct answers and updates the progress indicators.
     *
     * @param corrects The number of correct answers.
     */
    void setCorrectNum(int corrects) {
        correctNum.setText(String.valueOf(corrects));
        incorrectNum.setText(String.valueOf(Quiz.NUM_OF_QUESTIONS - corrects));
        incorrectProgress.setProgress((double) (Quiz.NUM_OF_QUESTIONS - corrects) / 10);
        correctProgress.setProgress((double) corrects / 10);
    }

    /**
     * Handles the action to go back to the home page.
     *
     * @param event The ActionEvent triggering the event.
     */
    @FXML
    private void goToHomePage(ActionEvent event) {
        Stage thisStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home.fxml"));
        try {
            thisStage.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
