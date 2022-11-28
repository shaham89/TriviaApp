package com.example.triviaapp;

import java.util.ArrayList;

public class Question {
    public final String questionText;
    public final String[] options;
    public final String answerHash;

    public Question(String questionText, String[] options, String answerHash){
        this.questionText = questionText;
        this.options = options;
        this.answerHash = answerHash;
    }
}
