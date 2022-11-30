package com.example.triviaapp.custom_classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Question implements Serializable {
    public final String questionText;
    public final ArrayList<String> options;
    public final String answerHash;
    public final String OPTION_TEXT = "Options";
    public final String QUESTION_TEXT = "QuestionText";
    public final String TRUE_ANSWER = "TrueAnswer";

//    public Question(String questionText, String[] options, String answerHash){
//        this.questionText = questionText;
//        this.options = options;
//        this.answerHash = answerHash;
//    }

    public Question(Map<String, Object> json){
        this.questionText = (String) json.get(QUESTION_TEXT);
        this.options = (ArrayList<String>) json.get(OPTION_TEXT);
        this.answerHash = (String) json.get(TRUE_ANSWER);
    }

}
