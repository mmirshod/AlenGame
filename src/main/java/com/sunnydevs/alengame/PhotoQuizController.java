package com.sunnydevs.alengame;

import com.sunnydevs.alengame.db.Person;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.sql.*;
import java.util.*;

public class PhotoQuizController {
    @FXML
    private Label questionNum;
    @FXML
    private GridPane btnsContainer;
    @FXML
    private ImageView imgHolder;

    @FXML
    private void initialize() {
        setOptions();
        questionNum.setText("#1");
//        imgHolder.setImage((Image) questions.get(counter).get("photo"));
        counter++;
    }

    int NUM_OF_QUESTIONS = 10;
    int counter;
    ArrayList<Integer> correctQuestions = new ArrayList<>();
    ArrayList<Integer> incorrectQuestions = new ArrayList<>();
//    ArrayList<Map<String, Object>> questions = generateQuestions();

    @FXML
    void nextQuestion(ActionEvent event) {
        if (counter != 10) {
            questionNum.setText("#" + (counter + 1));
//            ArrayList<String> options = (ArrayList<String>) questions.get(counter).get("options");

//            if (options.indexOf(((Button) event.getSource()).getText()) == 0)
//                correctQuestions.add(counter);
//            else
//                incorrectQuestions.add(counter);
//            setOptions();

//            imgHolder.setImage((Image) questions.get(counter).get("photo"));
            counter++;
        } else {
            showResults();
        }
    }

    private void setOptions() {
        List<Node> btns = btnsContainer.getChildren();
//        for (int i = 0; i < btns.size(); i++) {
//            Node btn = btns.get(i);
//            if (btn instanceof Button) {
//                ((Button) btn).setText(((ArrayList<String>) questions.get(counter).get("options")).get(i));
//            }
//        }
    }

    private void showResults(){

    }

//    private ArrayList<Map<String, Object>> generateQuestions() {
//        Random rd = new Random();
//        ArrayList<Map<String, Object>> questions = new ArrayList<>();
//
//        try {
//            ArrayList<Person> allPeople = Person.all();
//            Collections.shuffle(allPeople);
//
//            for (Person p : allPeople) {
//                if (questions.size() != 10) {
//                    Map<String, Object> question = new HashMap<>();
//
//                    // add Image object to question
//                    question.put("photo", new Image(p.photo().getBinaryStream()));
//
//                    // generate 4 options for question
//                    ArrayList<String> opts = new ArrayList<>();
//                    while (opts.size() != 4) {
//                        opts.add(Person.allNames().get(rd.nextInt(allPeople.size())));
//                    }
//
//                    // Set first opt as correct option.
//                    if (opts.contains(p.name())) {
//                        Collections.swap(opts, 0, opts.indexOf(p.name()));
//                    } else {
//                        opts.set(0, p.name());
//                    }
//
//                    question.put("options", opts);
//                    questions.add(question);
//                }
//            }
//            return questions;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
