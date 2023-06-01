package com.example.triviaapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
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

    public Question(JSONObject questions_dict) throws JSONException, NoSuchAlgorithmException {
        this.questionText = (String) questions_dict.get(QUESTION_TEXT);
        this.options = toStringArray(((JSONArray) questions_dict.get(OPTION_TEXT)));
        String trueAnswer = (String) questions_dict.get(TRUE_ANSWER);
        String currentAnswer = options[0];
        double currentMax = similarity(trueAnswer, options[0]);

        for(int i = 0; i < NUMBER_OF_DIFFERENT_OPTIONS; i++){
            double currentSimilarity = similarity(trueAnswer, options[i]);
            if(currentMax < currentSimilarity){
               currentAnswer = options[i];
               currentMax = currentSimilarity;
            }
        }

        this.answerHash = GameActivity.getMd5Hashed(currentAnswer);
    }

    //finds how similar two strings are, returns a number between 0 and 1
    private static double similarity(String s1, String s2) {
        String longer = s1, shorter = s2;
        if (s1.length() < s2.length()) { // longer should always have greater length
            longer = s2; shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) { return 1.0; /* both strings are zero length */ }
    /* // If you have Apache Commons Text, you can use it to calculate the edit distance:
    LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
    return (longerLength - levenshteinDistance.apply(longer, shorter)) / (double) longerLength; */
        return (longerLength - editDistance(longer, shorter)) / (double) longerLength;

    }

    // Example implementation of the Levenshtein Edit Distance
    // See http://rosettacode.org/wiki/Levenshtein_distance#Java
    private static int editDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0)
                    costs[j] = j;
                else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1))
                            newValue = Math.min(Math.min(newValue, lastValue),
                                    costs[j]) + 1;
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
                costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
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
