package com.example.triviaapp.game;

import static java.lang.Math.min;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.triviaapp.R;
import com.example.triviaapp.chatgpt.chatApi;
import com.example.triviaapp.customClasses.Question;
import com.example.triviaapp.customClasses.Game;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class GameActivity extends AppCompatActivity {

    private Game m_game;
    private static boolean[] correctAnswers;
    private static int currentQuestionIndex;
    private Question currQuestion;

    private Button[] answers;
    private TextView gameQuestionTextview;
    private int[] timeScores;
    private Stopwatch stopwatch;

    private static boolean isFreezeState;

    //private static final long TIME_PER_QUESTION_MS = 5 * 1000;
    private static final long TIME_BETWEEN_QUESTIONS_MS = 2 * 1000;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        m_game = (Game) getIntent().getSerializableExtra(getString(R.string.game_intent_text));

        currentQuestionIndex = 0;
        timeScores = new int[m_game.getNumberOfQuestions()];
        correctAnswers = new boolean[m_game.getNumberOfQuestions()];
        isFreezeState = false;
        generateChatQuestions(m_game.getNumberOfQuestions() - m_game.getQuestions().length, m_game.getSubject().getSubjectDisplayName());
        init_views();

        playGame();

    }

    private static final Object lock = new Object();
    private static final Object waitForMoreQuestionsLock = new Object();
    private void playQuestion(int questionIndex) throws InterruptedException {
        long REFRESH_RATE_MS = 100;

        while(questionIndex >= m_game.getQuestions().length){
            lock.wait(REFRESH_RATE_MS);
        }

        currQuestion = m_game.getQuestions()[questionIndex];
        showCurrQuestion();
        isFreezeState = false;
        stopwatch.reset();

        try {
            long start = System.currentTimeMillis();
            lock.wait(m_game.getTimePerQuestionSec() * 1000L);
            long finish = System.currentTimeMillis();

            stopwatch.stop();

            isFreezeState = true;
            long timeElapsed = finish - start;

            timeScores[questionIndex] = (int) timeElapsed;

            greenLightCorrectAnswer();
            GameActivity.currentQuestionIndex += 1;
            lock.wait(TIME_BETWEEN_QUESTIONS_MS);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void playGame() {
        Thread game_thread = new Thread() {
            @Override
            public void run() {
                synchronized(lock) {
                    isFreezeState = false;
                    for(int i = 0; i < m_game.getNumberOfQuestions(); i++){
                        try {
                            playQuestion(i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    gameEnded();
                }
            }
        };

        game_thread.start();
    }


    private void gameEnded(){

        Intent intent = new Intent(this, GameStatsActivity.class);

        intent.putExtra(getString(R.string.correct_answers_text), correctAnswers);
        intent.putExtra(getString(R.string.game_intent_text), m_game);
        intent.putExtra(getString(R.string.scores_text), timeScores);
        finish();
        startActivity(intent);

    }

    private void showCurrQuestion(){
        gameQuestionTextview.setText(currQuestion.questionText);
        resetButtonColors();

        for(int i = 0; i < answers.length; i++){
            answers[i].setText(currQuestion.options[i]);
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
            answer.setBackgroundColor(getResources().getColor(R.color.default_button_color));
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
                    correctAnswers[GameActivity.currentQuestionIndex] = true;
                } else {
                    view.setBackgroundColor(getResources().getColor(R.color.red_answer));
                }
                lock.notify();
            }

        }

    }

    //for stopwatch
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void init_views(){

        answers = new Button[]{ findViewById(R.id.questionButton1),
                                findViewById(R.id.questionButton2),
                                findViewById(R.id.questionButton3),
                                findViewById(R.id.questionButton4)};

        for (Button answer : answers) {
            answer.setOnClickListener(new answerClickedHandler());
        }
        long time = m_game.getTimePerQuestionSec() * 1000L;
        gameQuestionTextview = findViewById(R.id.gameQuestionTextview);
        stopwatch = new Stopwatch(findViewById(R.id.stopWatchProgressBar), time);
    }

    //returns the hex string representation of a hashMd5 on string
    public static String getMd5Hashed(String answer) {

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

    public void generateChatQuestions(int numberOfQuestions, String subject) {
        //okhttp
        //messageList.add(new Message("Typing... ",Message.SENT_BY_BOT));


        Context context = getApplicationContext();
        String[] oldQuestions = new String[m_game.getQuestions().length];
        for(int i = 0; i < m_game.getQuestions().length; i++){
            oldQuestions[i] = m_game.getQuestions()[i].questionText;
        }

        Request request = chatApi.getRequest(numberOfQuestions, subject, true, oldQuestions);


        chatApi.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("CALLED", "Failed to load response due to " + e.getMessage());
                Toast.makeText(context, "Response failed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {

                        JSONObject jsonResponse = new JSONObject(Objects.requireNonNull(response.body()).string());
                        JSONArray jsonArrayChoices = jsonResponse.getJSONArray("choices");
                        String result = jsonArrayChoices.getJSONObject(0).getJSONObject("message").getString("content");
                        //String result = jsonArray.getJSONObject(0).getString("text");
                        Log.d("CALLED", result);

                        JSONObject json_questions = new JSONObject(result);

                        Iterator<String> keys = json_questions.keys();
                        int i = m_game.getQuestions().length;

                        Question[] newQuestions = new Question[m_game.getNumberOfQuestions()];
                        while(keys.hasNext()) {
                            String key = keys.next();
                            if (json_questions.get(key) instanceof JSONObject) {
                                newQuestions[i] = new Question((JSONObject) json_questions.get(key));
                            }
                            i++;
                        }
                        System.arraycopy(m_game.getQuestions(), 0, newQuestions, 0, m_game.getQuestions().length);
                        m_game.setQuestions(newQuestions);

                    } catch (JSONException | NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }

//https://github.com/easy-tuto/Android_ChatGPT/blob/main/app/src/main/java/com/example/easychatgpt/MainActivity.java
                } else {
                    Log.d("ELSE CALLED", "Failed to load response due to " + Objects.requireNonNull(response.body()).toString());
                    Toast.makeText(context, "Error with response", Toast.LENGTH_LONG).show();
                }
            }

        });

    }

}