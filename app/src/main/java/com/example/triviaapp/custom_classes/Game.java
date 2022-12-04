package com.example.triviaapp.custom_classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable {
    public boolean is_competitive;
    public String subject;
    public int questions_number;
    public ArrayList<Question> questions;


    public static final String DEFAULT_SUBJECT = "capitals";
    public static final int DEFAULT_QUESTION_NUMBER = 20;


    public Game() {
        this.subject = DEFAULT_SUBJECT;
        this.is_competitive = false;
        this.questions_number = DEFAULT_QUESTION_NUMBER;
        this.questions = null;
    }

    public Game(Game game){
        this.subject = game.subject;
        this.is_competitive = game.is_competitive;
        this.questions_number = game.questions_number;
        this.questions = game.questions;
    }

}
