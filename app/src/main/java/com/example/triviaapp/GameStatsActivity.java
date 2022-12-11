package com.example.triviaapp;


import static com.example.triviaapp.helperFunctions.FireStoreConstants.ID_FIELD_FIELD;
import static com.example.triviaapp.helperFunctions.FireStoreConstants.MAIN_STATS_COLLECTION;
import static com.example.triviaapp.helperFunctions.FireStoreConstants.STATS_SCORE_FIELD;
import static com.example.triviaapp.helperFunctions.FireStoreConstants.STATS_SUBJECT_FIELD;
import static com.example.triviaapp.helperFunctions.FireStoreConstants.STATS_TIME_SCORE_FIELD;
import static com.example.triviaapp.helperFunctions.FireStoreConstants.STATS_TOTAL_CORRECT_ANSWERS_FIELD;
import static com.example.triviaapp.helperFunctions.FireStoreConstants.STATS_TOTAL_GAMES_PLAYED_FIELD;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.triviaapp.custom_classes.Game;
import com.example.triviaapp.custom_classes.GameResults;
import com.example.triviaapp.custom_classes.Question;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GameStatsActivity extends AppCompatActivity {

    private GraphView graphView;
    private TextView dataPointTextView;
    private GameResults m_gameResults;
    private static Question[] questions;
    private FirebaseFirestore db;
    private FirebaseUser m_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_stats);

        m_user = FirebaseAuth.getInstance().getCurrentUser();
        assert m_user != null;

        db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();

        int[] scores = intent.getIntArrayExtra(getString(R.string.scores_text));
        boolean[] correctAnswers = intent.getBooleanArrayExtra(getString(R.string.correct_answers_text));

        Game game = (Game) intent.getSerializableExtra(getString(R.string.game_intent_text));
        m_gameResults = new GameResults(scores, correctAnswers, game);
        questions = m_gameResults.getQuestions();

        initViews();
        if(m_gameResults.isCompetitive()){
            updateStats();
        }

    }
    private static final String TAG = "StatsActivity";
    private static int user_place = 0;

    private void updateUserGames(String docId, long score, double timeScore, boolean doesAlreadyExists){
        //UserStats stats = new UserStats(m_user.getUid(), 1, 2, 4000, m_gameResults.getSubject())


        HashMap<String, Object> updateDict = new HashMap<>();
        //total games played in subject
        updateDict.put(STATS_TOTAL_GAMES_PLAYED_FIELD, FieldValue.increment(1));
        //total correct answer in subject
        updateDict.put(STATS_TOTAL_CORRECT_ANSWERS_FIELD, FieldValue.increment(m_gameResults.getNumberOfCorrectQuestions()));
        //subject
        updateDict.put(STATS_SUBJECT_FIELD, m_gameResults.getSubject());
        //score
        updateDict.put(STATS_SCORE_FIELD, score);
        //timeScore
        updateDict.put(STATS_TIME_SCORE_FIELD, timeScore);
        //user id
        updateDict.put(ID_FIELD_FIELD, m_user.getUid());

        if(doesAlreadyExists){
            db.collection(MAIN_STATS_COLLECTION)
                    .document(docId)
                    .update(updateDict)
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully updated!"))
                    .addOnFailureListener(e -> Log.w(TAG, "Error updating document", e));
        } else {
            db.collection(MAIN_STATS_COLLECTION)
                    .add(updateDict)
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully updated!"))
                    .addOnFailureListener(e -> Log.w(TAG, "Error updating document", e));
        }


    }

    private void updateUserScore(String docId, List<Float> scores){
        //UserStats stats = new UserStats(m_user.getUid(), 1, 2, 4000, m_gameResults.getSubject())


        HashMap<String, Object> updateDict = new HashMap<>();
        updateDict.put(STATS_SCORE_FIELD, m_gameResults.getAverageTimeScore());


        db.collection(MAIN_STATS_COLLECTION)
                .document(docId)
                .update(updateDict)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "score successfully updated!"))
                .addOnFailureListener(e -> Log.w(TAG, "Error updating score", e));

    }

    private void updateStats(){

        //query for the same user and subject
        Query query = db.collection(MAIN_STATS_COLLECTION)
                .whereEqualTo(STATS_SUBJECT_FIELD, m_gameResults.getSubject())
                .whereEqualTo(ID_FIELD_FIELD, m_user.getUid());
//                .whereLessThanOrEqualTo(STATS_SCORE_FIELD, m_gameResults.getAverageTimeScore())
//                .orderBy(STATS_SCORE_FIELD, Query.Direction.ASCENDING).limit(1);

        query.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        boolean doesDocumentExist = false;
                        String documentID = "";
                        long userCurrentScore = m_gameResults.getNumberOfCorrectQuestions();
                        double userCurrentTimeScore = m_gameResults.getAverageTimeScore();

                        long bestUserScore = userCurrentScore;
                        double bestUserTimeScore = userCurrentTimeScore;

                        for (QueryDocumentSnapshot document : task.getResult()) {

                            doesDocumentExist = true;
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            documentID = document.getId();

                            //if current score/timeScore is better than the database score
                            //update the score and the time score
                            long firestoreUserScore = (long)document.get(STATS_SCORE_FIELD);
                            double firestoreUserTimeScore = (double) document.get(STATS_TIME_SCORE_FIELD);
                            bestUserScore = firestoreUserScore;
                            bestUserTimeScore = firestoreUserTimeScore;

                            //if the current score is better, update the timeScore also
                            if(userCurrentScore > firestoreUserScore){
                                bestUserScore = userCurrentScore;
                                bestUserTimeScore = userCurrentTimeScore;

                            } else if(userCurrentScore == firestoreUserScore && userCurrentScore < firestoreUserTimeScore){
                                //if the currentScore==FirestoreScore, update the timeScore
                                //only if the currentTimeScore is better
                                Toast.makeText(GameStatsActivity.this, "New best score!", Toast.LENGTH_SHORT).show();
                                bestUserTimeScore = userCurrentTimeScore;
                            }
                        }


                        updateUserGames(documentID, bestUserScore, bestUserTimeScore, doesDocumentExist);

                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });

    }

    private void initViews(){
        graphView = findViewById(R.id.scoresGraph);
        dataPointTextView = findViewById(R.id.dataPointTextView);

        initTextViewsScore();
        initGraph();
        initSeries();
    }

    private void initTextViewsScore(){

        TextView avgView = findViewById(R.id.averageScore);
        avgView.setText(String.format("Average Time Score: %s", m_gameResults.getAverageTimeScore()));

        TextView answerScores = findViewById(R.id.answerScore);

        String correctAnswerRatio = String.format("%s/%s Correct Answers",
                m_gameResults.getNumberOfCorrectQuestions(), m_gameResults.getNumberOfQuestions());

        answerScores.setText(correctAnswerRatio);
    }

    private static String[] generateXAxis(int size){
        String[] data = new String[size];
        for(int i = 1; i <= size; i++){
            data[i - 1] = String.valueOf(i);
        }
        return data;
    }


    private void initSeries(){
        DataPoint[] points = new DataPoint[m_gameResults.getNumberOfQuestions()];

        for(int i = 0; i < m_gameResults.getNumberOfQuestions(); i++){
            points[i] = new DataPoint(i + 1, m_gameResults.getTimeScores()[i]);
        }

        PointsGraphSeries<DataPoint> correctSeries = new PointsGraphSeries<>();
        PointsGraphSeries<DataPoint> inCorrectSeries = new PointsGraphSeries<>();
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);


        for(int i = 0; i < m_gameResults.getNumberOfQuestions(); i++){
            if(m_gameResults.getCorrectAnswers()[i]){
                correctSeries.appendData(points[i], false, m_gameResults.getNumberOfQuestions());
            } else{
                inCorrectSeries.appendData(points[i], false, m_gameResults.getNumberOfQuestions());
            }
        }

        correctSeries.setColor(Color.GREEN);
        inCorrectSeries.setColor(Color.RED);


        OnDataPointTapListener showQuestionTap = (OnDataPointTapListener) (series1, dataPoint) -> {
            String showOutput = questions[(int) dataPoint.getX() - 1].questionText + "\n" + (int) dataPoint.getY() + " ms";
            dataPointTextView.setText(showOutput);
        };

        correctSeries.setOnDataPointTapListener(showQuestionTap);
        inCorrectSeries.setOnDataPointTapListener(showQuestionTap);

        correctSeries.setSize(20);
        inCorrectSeries.setSize(20);

        graphView.addSeries(series);
        graphView.addSeries(correctSeries);
        graphView.addSeries(inCorrectSeries);
    }

    private void initGraph(){
        graphView.setTitle("Time scores");

        // on below line we are setting
        // text color to our graph view.
        graphView.setTitleColor(R.color.purple_200);

        // on below line we are setting
        // our title text size.
        graphView.setTitleTextSize(40);


        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(0.9);
        graphView.getViewport().setMaxX(m_gameResults.getNumberOfQuestions() + 0.1);

// Set manual Y bounds
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(0);
        graphView.getViewport().setMaxY(5100);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graphView);
        String[] axis = generateXAxis(m_gameResults.getNumberOfQuestions());
        Log.w("Stats activity", Arrays.toString(axis));
        staticLabelsFormatter.setHorizontalLabels(axis);

        staticLabelsFormatter.setVerticalLabels(new String[] {"0,", "1000", "2000", "3000","4000", "5000"});
        //staticLabelsFormatter.setVerticalLabels(new String[] {"1000", "2000"});

        graphView.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);


        graphView.setBackgroundColor(Color.WHITE);

    }


}