package com.example.triviaapp.customClasses;

import com.example.triviaapp.game.GameActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

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
        this.options = (String[]) ((ArrayList<String>) Objects.requireNonNull(json.get(OPTION_TEXT))).toArray(new String[NUMBER_OF_DIFFERENT_OPTIONS]);
        this.answerHash = (String) json.get(TRUE_ANSWER);
    }

    public Question(JSONObject jsonObject) throws JSONException, NoSuchAlgorithmException {
        this.questionText = (String) jsonObject.get(QUESTION_TEXT);
        this.options = toStringArray(((JSONArray) jsonObject.get(OPTION_TEXT)));
        this.answerHash = GameActivity.getMd5Hashed(options[(int) jsonObject.get(TRUE_ANSWER)]);
    }

    private static String[] toStringArray(JSONArray array) {
        if(array==null)
            return new String[0];

        String[] arr=new String[array.length()];
        for(int i=0; i<arr.length; i++) {
            arr[i]=array.optString(i);
        }
        return arr;
    }
}
