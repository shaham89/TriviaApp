package com.example.triviaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private ArrayList<Question> questions;
    private static int correctAnswers = 0;
    private Question currQuestion;

    private Button answer1, answer2, answer3, answer4;
    private Button[] answers;
    private TextView gameQuestionTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();

        questions = (ArrayList<Question>) intent.getSerializableExtra(getString(R.string.questions));

        init_views();



        currQuestion = questions.get(0);
        showCurrQuestion();
    }



    private void showCurrQuestion(){
        gameQuestionTextview.setText(currQuestion.questionText);

        for(int i = 0; i < answers.length; i++){
            answers[i].setText(currQuestion.options.get(0));
        }

    }

    private String getMd5Hashed(String answer) {
        byte[] bytesOfMessage = answer.getBytes(StandardCharsets.UTF_8);

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] MD5digestBytes = md.digest(bytesOfMessage);
            String md5String = new String(MD5digestBytes, StandardCharsets.UTF_8);

            return md5String;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

    }

    //Change the color of the correct answer button to green
    private void greenLightCorrectAnswer(){
        for(Button answer: answers){
            if(currQuestion.TRUE_ANSWER.equals(getMd5Hashed(answer.getText().toString()))) {
                answer.setBackgroundColor(getResources().getColor(R.color.green_answer));
            }
        }

    }

    protected class answerClickedHandler implements View.OnClickListener {
        //check if the user exist, and if he doesn't call addUser
        //if he does, show Toast
        @Override
        public void onClick(View view) {

            String userAnswer = ((Button)view).getText().toString();
            String userAnswerHash = getMd5Hashed(userAnswer);

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