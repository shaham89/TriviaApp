package com.example.triviaapp.custom_classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable {
    protected boolean isCompetitive;
    protected String subject;
    protected Question[] questions;

    public boolean isCompetitive() {
        return isCompetitive;
    }

    public void setCompetitive(boolean competitive) {
        isCompetitive = competitive;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Question[] getQuestions() {
        return questions;
    }

    public void setQuestions(Question[] questions) {
        this.questions = questions;
    }

    public static final String DEFAULT_SUBJECT = "capitals";
    public static final int DEFAULT_QUESTION_NUMBER = 5;


    public Game() {
        this.subject = DEFAULT_SUBJECT;
        this.isCompetitive = false;
        this.questions = null;
    }

    public Game(Game game){
        this.subject = game.subject;
        this.isCompetitive = game.isCompetitive;
        this.questions = game.questions;
    }

}
