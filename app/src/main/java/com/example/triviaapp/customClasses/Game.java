package com.example.triviaapp.customClasses;

import java.io.Serializable;

public class Game implements Serializable {
    protected boolean isCompetitive;
    protected String subject;
    protected Question[] questions;

    protected int numberOfQuestions = 0;

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
        this.numberOfQuestions = this.questions.length;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public static final String DEFAULT_SUBJECT = "capitals";
    public static final int DEFAULT_QUESTION_NUMBER = 5;
    public static final int DEFAULT_TIME_PER_QUESTION_SEC = 5;

    public static final int COMPETITIVE_QUESTION_NUMBER = 4;
    public static final int COMPETITIVE_TIME_PER_QUESTION_SEC = 5;

    public Game() {
        this.subject = DEFAULT_SUBJECT;
        this.isCompetitive = false;
        this.questions = null;
    }

    public Game(Game game){
        this.subject = game.subject;
        this.isCompetitive = game.isCompetitive;
        this.questions = game.questions;
        this.numberOfQuestions = game.numberOfQuestions;
    }

}
