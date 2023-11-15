package com.sunnydevs.alengame;

import com.sunnydevs.alengame.db.Person;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public abstract class Quiz {
    static int NUM_OF_QUESTIONS = 10;

    static void setOptions(GridPane btnsContainer, ArrayList<Map<String, Object>> questions, int counter) {
        List<Node> btns = btnsContainer.getChildren();
        for (int i = 0; i < btns.size(); i++) {
            Node btn = btns.get(i);
            if (btn instanceof Button) {
                ((Button) btn).setText(((ArrayList<String>) questions.get(counter).get("options")).get(i));
            }
        }
    }

    protected void showResults(Stage stage){
        try {
            stage.close();
            FXMLLoader resultsView = new FXMLLoader(getClass().getResource("results.fxml"));
            Scene resultsScene = new Scene(resultsView.load());
            resultsScene.setFill(Color.TRANSPARENT);
            Stage resultsStage = new Stage(StageStyle.TRANSPARENT);
            resultsStage.setScene(resultsScene);
            resultsStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    ArrayList<Map<String, Object>> generateQuestions() {
        Random rd = new Random();
        ArrayList<Map<String, Object>> questions = new ArrayList<>();

        try {
            ArrayList<Person> allPeople = Person.all();
            Collections.shuffle(allPeople);

            for (Person p : allPeople) {
                if (questions.size() != PhotoQuizController.NUM_OF_QUESTIONS) {
                    Map<String, Object> question = new HashMap<>();

                    // generate 4 options for question
                    ArrayList<String> opts = new ArrayList<>();
                    while (opts.size() != 4) {
                        opts.add(Person.allNames().get(rd.nextInt(allPeople.size())));
                    }

                    // Set first opt as correct option.
                    if (opts.contains(p.name())) {
                        Collections.swap(opts, 0, opts.indexOf(p.name()));
                    } else {
                        opts.set(0, p.name());
                    }

                    question.put("options", opts);
                    questions.add(question);
                }
            }
            return questions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
