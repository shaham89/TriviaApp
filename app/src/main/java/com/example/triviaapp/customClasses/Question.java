package com.example.triviaapp.customClasses;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Question implements Serializable {
    public final String questionText;
    public final String[] options;
    public final String answerHash;

    public final String OPTION_TEXT = "Options";
    public final String QUESTION_TEXT = "QuestionText";
    public final String TRUE_ANSWER = "TrueAnswer";

    public final String CHAT_GPT_QUESTION = "questions";

    public final int NUMBER_OF_DIFFERENT_OPTIONS = 4;
//    public Question(String questionText, String[] options, String answerHash){
//        this.questionText = questionText;
//        this.options = options;
//        this.answerHash = answerHash;
//    }

    public Question(Map<String, Object> json){
        this.questionText = (String) json.get(QUESTION_TEXT);
        this.options = (String[]) ((ArrayList<String>) json.get(OPTION_TEXT)).toArray(new String[NUMBER_OF_DIFFERENT_OPTIONS]);
        this.answerHash = (String) json.get(TRUE_ANSWER);
    }

    public Question(JSONObject jsonObject) throws JSONException {
        this.questionText = (String) jsonObject.get(QUESTION_TEXT);
        this.options = (String[]) jsonObject.get(OPTION_TEXT);
        this.answerHash = (String) jsonObject.get(TRUE_ANSWER);
    }
}
