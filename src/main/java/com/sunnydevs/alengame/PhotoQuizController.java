package com.sunnydevs.alengame;

import com.sunnydevs.alengame.db.Person;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class PhotoQuizController extends Quiz {
    @FXML
    private Label questionNum;
    @FXML
    private GridPane btnsContainer;
    @FXML
    private ImageView imgHolder;

    @FXML
    private void initialize() {
        nextQuestion(null);
    }

    int counter;
    ArrayList<Integer> correctQuestions = new ArrayList<>();
    ArrayList<Integer> incorrectQuestions = new ArrayList<>();
    ArrayList<Map<String, Object>> questions = generateQuestions();

    @FXML
    void nextQuestion(ActionEvent event) {
        if (event == null) {
            Quiz.setOptions(btnsContainer, questions, counter);
            questionNum.setText("#" + (counter + 1));
            imgHolder.setImage((Image) questions.get(counter).get("pic"));
            counter++;
            return;
        }

        if (((Button) event.getSource()).getText() == questions.get(counter - 1).get("correct")) {
            System.out.println(counter + "-> Correct");
            correctQuestions.add(counter - 1);
        } else {
            System.out.println(counter + "-> Incorrect");
            incorrectQuestions.add(counter - 1);
        }
        if (counter == NUM_OF_QUESTIONS) {
            System.out.println(correctQuestions);
            System.out.println(incorrectQuestions);
            showResults(((Stage) ((Button) event.getSource()).getScene().getWindow()));
            return;
        }

        questionNum.setText("#" + (counter + 1));
        Quiz.setOptions(btnsContainer, questions, counter);

        imgHolder.setImage((Image) questions.get(counter).get("pic"));
        counter++;
    }

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
