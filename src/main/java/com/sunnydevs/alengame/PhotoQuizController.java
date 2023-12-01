package com.sunnydevs.alengame;

import com.sunnydevs.alengame.db.Person;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Controller class for the PhotoQuiz.fxml file.
 */
public class PhotoQuizController extends Quiz {

    @FXML
    private Label questionNum;

    @FXML
    private GridPane btnsContainer;

    @FXML
    private ImageView imgHolder;

    private int counter;
    private final ArrayList<Integer> correctQuestions = new ArrayList<>();
    private final ArrayList<Integer> incorrectQuestions = new ArrayList<>();
    private final ArrayList<Map<String, Object>> questions = generateQuestions();

    /**
     * Initializes the controller.
     */
    @FXML
    void initialize() {
        for (Node btn : btnsContainer.getChildren()) {
            btn.setOnMouseEntered(super::handleMouseEnter);
            btn.setOnMouseExited(super::handleMouseExit);
        }
        nextQuestion(null);
    }

    /**
     * Handles the next question action.
     *
     * @param event The ActionEvent triggering the action.
     */
    @FXML
    void nextQuestion(ActionEvent event) {
        if (event == null) {
            Quiz.setOptions(btnsContainer, questions, counter);
            questionNum.setText("#" + (counter + 1));
            imgHolder.setImage((Image) questions.get(counter).get("pic"));
            counter++;
            return;
        }

        if (((Button) event.getSource()).getText().equals(questions.get(counter - 1).get("correct"))) {
            ((Button) event.getSource()).setStyle("-fx-background-color: #dffbef; -fx-border-color: #2ca177");
            correctQuestions.add(counter - 1);
        } else {
            ((Button) event.getSource()).setStyle("-fx-background-color: #ffa6a6; -fx-border-color: #800000");
            incorrectQuestions.add(counter - 1);
        }

        if (counter == NUM_OF_QUESTIONS) {
            System.out.println(correctQuestions);
            System.out.println(incorrectQuestions);
            showResults(((Stage) ((Button) event.getSource()).getScene().getWindow()), correctQuestions.size(), "photo");
            return;
        }

        questionNum.setText("#" + (counter + 1));
        Quiz.setOptions(btnsContainer, questions, counter);

        imgHolder.setImage((Image) questions.get(counter).get("pic"));
        counter++;
    }

    /**
     * Generates questions for the quiz, including images.
     *
     * @return A list of questions with associated information.
     */
    @Override
    ArrayList<Map<String, Object>> generateQuestions() {
        ArrayList<Map<String, Object>> rawQuestions = super.generateQuestions();
        for (Map<String, Object> question : rawQuestions) {
            try {
                Person person = Person.getByName((String) question.get("correct"));
                question.put("pic", person.photo());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return rawQuestions;
    }
}
