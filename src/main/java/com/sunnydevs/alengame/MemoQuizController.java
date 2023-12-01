package com.sunnydevs.alengame;

import com.sunnydevs.alengame.db.Person;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Controller class for the Memo Quiz screen.
 */
public class MemoQuizController extends Quiz {

    @FXML
    private Label questionNum;

    @FXML
    private GridPane btnsContainer;

    @FXML
    private Text memoHolder;

    @FXML
    private AnchorPane innerAnchorPane, outerAnchorPane;

    private int counter;
    private final ArrayList<Integer> correctQuestions = new ArrayList<>();
    private final ArrayList<Integer> incorrectQuestions = new ArrayList<>();
    private final ArrayList<Map<String, Object>> questions = generateQuestions();

    /**
     * Initializes the Memo Quiz controller.
     */
    @FXML
    private void initialize() {
        super.setUpAnchorPaneConstraints(innerAnchorPane, outerAnchorPane);

        for (Node btn : btnsContainer.getChildren()) {
            btn.setOnMouseEntered(super::handleMouseEnter);
            btn.setOnMouseExited(super::handleMouseExit);
        }
        nextQuestion(null);
    }

    /**
     * Handles the next question action.
     *
     * @param event The action event.
     */
    @FXML
    void nextQuestion(ActionEvent event) {
        if (event == null) {
            Quiz.setOptions(btnsContainer, questions, counter);
            questionNum.setText("#" + (counter + 1));
            memoHolder.setText((String) questions.get(counter).get("memo"));
            counter++;
            return;
        }

        if (((Button) event.getSource()).getText().equals(questions.get(counter - 1).get("correct"))) {
            correctQuestions.add(counter - 1);
        } else {
            incorrectQuestions.add(counter - 1);
        }

        if (counter == NUM_OF_QUESTIONS) {
            System.out.println(correctQuestions);
            System.out.println(incorrectQuestions);
            showResults(((Stage) ((Button) event.getSource()).getScene().getWindow()), correctQuestions.size(), "memo");
            return;
        }

        questionNum.setText("#" + (counter + 1));
        Quiz.setOptions(btnsContainer, questions, counter);

        memoHolder.setText((String) questions.get(counter).get("memo"));
        counter++;
    }

    /**
     * Generates questions for the Memo Quiz.
     *
     * @return A list of questions.
     */
    @Override
    ArrayList<Map<String, Object>> generateQuestions() {
        ArrayList<Map<String, Object>> rawQuestions = super.generateQuestions();
        for (Map<String, Object> question : rawQuestions) {
            try {
                Person person = Person.getByName((String) question.get("correct"));
                question.put("memo", person.memo());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return rawQuestions;
    }
}
