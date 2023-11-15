package com.sunnydevs.alengame;

import com.sunnydevs.alengame.db.Person;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class MemoQuizController extends Quiz{
    @FXML
    private Label questionNum;
    @FXML
    private GridPane btnsContainer;
    @FXML
    private Label memoHolder;

    private int counter;
    ArrayList<Integer> correctQuestions = new ArrayList<>();
    ArrayList<Integer> incorrectQuestions = new ArrayList<>();
    ArrayList<Map<String, Object>> questions = generateQuestions();

    @FXML
    private void initialize() {
        questions = generateQuestions();
        Quiz.setOptions(btnsContainer, questions, counter);
        questionNum.setText("#1");
        memoHolder.setText((String) questions.get(counter).get("memo"));
        counter++;
    }

    @FXML
    void nextQuestion(ActionEvent event) {
        if (counter != NUM_OF_QUESTIONS) {
            questionNum.setText("#" + (counter + 1));
            ArrayList<String> options = (ArrayList<String>) questions.get(counter).get("options");

            if (options.indexOf(((Button) event.getSource()).getText()) == 0)
                correctQuestions.add(counter);
            else
                incorrectQuestions.add(counter);
            Quiz.setOptions(btnsContainer, questions, counter);

            memoHolder.setText((String) questions.get(counter).get("memo"));
            counter++;
        } else {
            showResults(((Stage) ((Button) event.getSource()).getScene().getWindow()));
        }
    }

    @Override
    ArrayList<Map<String, Object>> generateQuestions() {
        ArrayList<Map<String, Object>> rawQuestions = super.generateQuestions();
        for (Map<String, Object> question : rawQuestions) {
            try {
                Person person = Person.getByName(((ArrayList<String>) question.get("options")).get(0));
                question.put("memo", person.memo());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return rawQuestions;
    }
}
