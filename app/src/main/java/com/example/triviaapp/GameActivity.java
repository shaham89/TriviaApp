package com.example.triviaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.triviaapp.custom_classes.Question;
import com.example.triviaapp.tinyDB.TinyDB;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Locale;

public class GameActivity extends AppCompatActivity {

    private ArrayList<Question> questions;
    private static int correctAnswers = 0;
    private Question currQuestion;

    private Button answer1, answer2, answer3, answer4;
    private Button[] answers;
    private TextView gameQuestionTextview;
    private TinyDB m_tinydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Intent intent = getIntent();

        //questions = (ArrayList<Question>) intent.getSerializableExtra(getString(R.string.questions));

        m_tinydb = new TinyDB(getApplicationContext());
        String listName = "m_tempQuestions";
        //m_tinydb.putListObject(listName, questions);

        questions = m_tinydb.getListQuestions(listName, Question.class);
        init_views();



        currQuestion = questions.get(0);
        showCurrQuestion();
    }



    private void showCurrQuestion(){
        gameQuestionTextview.setText(currQuestion.questionText);

        for(int i = 0; i < answers.length; i++){
            answers[i].setText(currQuestion.options.get(i));
        }

    }

    final static char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars).toLowerCase(Locale.ROOT);
    }

    private static String getMd5Hashed(String answer) {



        byte[] bytesOfMessage = answer.getBytes(StandardCharsets.UTF_8);

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] MD5digestBytes = md.digest(bytesOfMessage);
            String md5String = bytesToHex(MD5digestBytes);

            return md5String;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

    }

    //Change the color of the correct answer button to green
    private void greenLightCorrectAnswer(){
        for(Button answer: answers){
            if(currQuestion.answerHash.equals(getMd5Hashed(answer.getText().toString()))) {
                answer.setBackgroundColor(getResources().getColor(R.color.green_answer));
            }
        }
    }

    private void resetButtonColors(){
        for(Button answer: answers){
            answer.setBackgroundColor(getResources().getColor(R.color.purple_500));
        }
    }

    protected class answerClickedHandler implements View.OnClickListener {
        //check if the user exist, and if he doesn't call addUser
        //if he does, show Toast
        @Override
        public void onClick(View view) {

            resetButtonColors();

            view.setBackgroundColor(getResources().getColor(R.color.red_answer));

            greenLightCorrectAnswer();
        }

    }

    private void init_views(){
        answer1 = findViewById(R.id.questionButton1);
        answer2 = findViewById(R.id.questionButton2);
        answer3 = findViewById(R.id.questionButton3);
        answer4 = findViewById(R.id.questionButton4);

        answers = new Button[]{answer1, answer2, answer3, answer4};

        for (Button answer : answers) {
            answer.setOnClickListener(new answerClickedHandler());
        }


        gameQuestionTextview = findViewById(R.id.gameQuestionTextview);
    }


}