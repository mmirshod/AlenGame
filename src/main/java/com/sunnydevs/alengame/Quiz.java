package com.sunnydevs.alengame;

import com.sunnydevs.alengame.db.Person;

import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * Abstract class representing the base functionality for a quiz in the AlenGame application.
 */
public abstract class Quiz {

    static int NUM_OF_QUESTIONS = 10;

    /**
     * Sets the options for the quiz buttons based on the current question.
     *
     * @param btnsContainer The GridPane containing the quiz buttons.
     * @param questions     The list of questions with associated information.
     * @param counter       The current question counter.
     */
    static void setOptions(GridPane btnsContainer, ArrayList<Map<String, Object>> questions, int counter) {
        List<Node> btns = btnsContainer.getChildren();
        ArrayList<String> opts = (ArrayList<String>) questions.get(counter).get("options");
        for (int i = 0; i < btns.size(); i++) {
            Node btn = btns.get(i);
            if (btn instanceof Button) {
                ((Button) btn).setText(opts.get(i));
            }
        }
    }

    /**
     * Shows the quiz results in a new stage.
     *
     * @param stage    The primary stage of the application.
     * @param corrects The number of correct answers.
     */
    protected void showResults(Stage stage, int corrects, String typer) {
        try {
            stage.close();
            FXMLLoader resultsView = new FXMLLoader(getClass().getResource("Result.fxml"));
            Scene resultsScene = new Scene(resultsView.load());
            ResultsController controller = resultsView.getController();
            controller.setCorrectNum(corrects, typer);

            Stage resultsStage = new Stage();
            resultsStage.setScene(resultsScene);
            resultsStage.setMaximized(true);
            resultsStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the initial styles for quiz buttons.
     *
     * @param btn The button to set styles for.
     */
    protected void setButtonStyles(@NotNull Button btn) {
        btn.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000; -fx-border-color: #808080");
    }

    /**
     * Handles the mouse enter event for quiz buttons.
     *
     * @param event The MouseEvent triggering the event.
     */
    protected void handleMouseEnter(@NotNull MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: rgb(99,111,255); -fx-text-fill: #fcfafa;");
    }

    /**
     * Handles the mouse exit event for quiz buttons.
     *
     * @param event The MouseEvent triggering the event.
     */
    protected void handleMouseExit(@NotNull MouseEvent event) {
        setButtonStyles((Button) event.getSource());
    }

    /**
     * Sets up the AnchorPane constraints for centering it within an outer AnchorPane.
     *
     * @param innerAnchorPane The inner AnchorPane to be centered.
     * @param outerAnchorPane The outer AnchorPane.
     */
    protected void setUpAnchorPaneConstraints(AnchorPane innerAnchorPane, AnchorPane outerAnchorPane) {
        AnchorPane.setTopAnchor(innerAnchorPane, (outerAnchorPane.getHeight() - innerAnchorPane.getPrefHeight()) / 2);
        AnchorPane.setLeftAnchor(innerAnchorPane, (outerAnchorPane.getWidth() - innerAnchorPane.getPrefWidth()) / 2);

        outerAnchorPane.heightProperty().addListener((observable, oldValue, newValue) ->
                AnchorPane.setTopAnchor(innerAnchorPane, (newValue.doubleValue() - innerAnchorPane.getPrefHeight()) / 2));

        outerAnchorPane.widthProperty().addListener((observable, oldValue, newValue) ->
                AnchorPane.setLeftAnchor(innerAnchorPane, (newValue.doubleValue() - innerAnchorPane.getPrefWidth()) / 2));
    }

    /**
     * Generates questions for the quiz.
     *
     * @return A list of questions with associated information.
     */
    ArrayList<Map<String, Object>> generateQuestions() {
        Random rd = new Random();
        ArrayList<Map<String, Object>> questions = new ArrayList<>();

        try {
            ArrayList<Person> dataSet = Person.generateDataSetForQuiz(1);
            Collections.shuffle(dataSet);
            ArrayList<String> names = Person.allNames();

            for (Person p : dataSet) {
                Map<String, Object> question = new HashMap<>();
                ArrayList<String> opts = new ArrayList<>();
                opts.add(p.name());
                while (opts.size() != 4) {
                    String name = names.get(rd.nextInt(dataSet.size()));
                    if (!opts.contains(name))
                        opts.add(name);
                }
                Collections.shuffle(opts);
                question.put("options", opts);
                question.put("correct", p.name());
                questions.add(question);
            }
            return questions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
