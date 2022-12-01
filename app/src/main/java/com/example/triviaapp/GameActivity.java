package com.example.triviaapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.triviaapp.custom_classes.Question;
import com.example.triviaapp.helperFunctions.TinyDB;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Locale;

public class GameActivity extends AppCompatActivity {

    private ArrayList<Question> questions;
    private static int correctAnswers = 0;
    private Question currQuestion;

    private Button[] answers;
    private TextView gameQuestionTextview;
    private long[] timeScores;

    private static boolean isFreezeState;

    private static final long TIME_PER_QUESTION_MS = 5 * 1000;
    private static final long TIME_BETWEEN_QUESTIONS_MS = 2 * 1000;

    //if true the client won't ask questions from the database
    static final boolean IS_TESTING = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        getQuestions();

        timeScores = new long[questions.size()];
        init_views();


        playGame();

    }

    private static final Object lock = new Object();

    @SuppressWarnings("unchecked")
    private void getQuestions(){
        String listName = "m_tempQuestions";
        if(IS_TESTING){
            TinyDB m_tinydb = new TinyDB(getApplicationContext());
            questions = m_tinydb.getListQuestions(listName, Question.class);
        } else {
            Intent intent = getIntent();

            questions = (ArrayList<Question>) intent.getSerializableExtra(getString(R.string.questions));
        }

        //m_tinydb.putListObject(listName, questions);
    }

    private void playQuestion(int questionIndex){
        currQuestion = questions.get(questionIndex);
        showCurrQuestion();
        isFreezeState = false;

        try {
            long start = System.currentTimeMillis();
            lock.wait(TIME_PER_QUESTION_MS);
            long finish = System.currentTimeMillis();
            isFreezeState = true;
            long timeElapsed = finish - start;

            timeScores[questionIndex] = timeElapsed;

            greenLightCorrectAnswer();
            lock.wait(TIME_BETWEEN_QUESTIONS_MS);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void playGame(){
        Thread game_thread = new Thread() {
            @Override
            public void run() {
                synchronized(lock) {
                    for(int i = 0; i < questions.size(); i++){
                        playQuestion(i);
                    }
                    gameEnded();
                }
            }
        };

        game_thread.start();
    }


    private void gameEnded(){
        Log.d("GameActivity", "TIme scores:" + timeScores[4]);

        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("correctAnswers", correctAnswers);
    }

    private void showCurrQuestion(){
        gameQuestionTextview.setText(currQuestion.questionText);
        resetButtonColors();

        for(int i = 0; i < answers.length; i++){
            answers[i].setText(currQuestion.options.get(i));
        }

    }

    //checks if the button that was clicked contains the correct answer
    private boolean isAnswerCorrect(Button answer){
        return currQuestion.answerHash.equals(  getMd5Hashed(answer.getText().toString()) );
    }

    //Change the color of the correct answer button to green
    private void greenLightCorrectAnswer(){
        for(Button answer: answers){
            if(isAnswerCorrect(answer)) {
                answer.setBackgroundColor(getResources().getColor(R.color.green_answer));
            }
        }
    }
    //change the color of all the buttons to the default value
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
            synchronized (lock){
                if(isFreezeState){return;}  //if the game is in the waiting state don't do anything


                if(isAnswerCorrect((Button) view)){
                    correctAnswers += 1;
                } else {
                    view.setBackgroundColor(getResources().getColor(R.color.red_answer));
                }
                lock.notify();
            }

        }

    }

    private void init_views(){

        answers = new Button[]{ findViewById(R.id.questionButton1),
                                findViewById(R.id.questionButton2),
                                findViewById(R.id.questionButton3),
                                findViewById(R.id.questionButton4)};

        for (Button answer : answers) {
            answer.setOnClickListener(new answerClickedHandler());
        }

        gameQuestionTextview = findViewById(R.id.gameQuestionTextview);
    }

    //returns the hex string representation of a hashMd5 on string
    private static String getMd5Hashed(String answer) {

        byte[] bytesOfMessage = answer.getBytes(StandardCharsets.UTF_8);

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] MD5digestBytes = md.digest(bytesOfMessage);

            return bytesToHex(MD5digestBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

    }

    //helper function to convert byte array to hex string representation
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


}