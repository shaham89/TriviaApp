package com.example.triviaapp;

import static com.example.triviaapp.helperFunctions.FireStoreConstants.ID_FIELD_FIELD;
import static com.example.triviaapp.helperFunctions.FireStoreConstants.MAIN_STATS_COLLECTION;
import static com.example.triviaapp.helperFunctions.FireStoreConstants.STATS_DISPLAY_NAME_FIELD;
import static com.example.triviaapp.helperFunctions.FireStoreConstants.STATS_SCORE_FIELD;
import static com.example.triviaapp.helperFunctions.FireStoreConstants.STATS_SUBJECT_FIELD;
import static com.example.triviaapp.helperFunctions.FireStoreConstants.STATS_TIME_SCORE_FIELD;
import static com.example.triviaapp.helperFunctions.FireStoreConstants.STATS_TOTAL_CORRECT_ANSWERS_FIELD;
import static com.example.triviaapp.helperFunctions.FireStoreConstants.STATS_TOTAL_GAMES_PLAYED_FIELD;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.triviaapp.customClasses.UserStats;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class LeaderBoardActivity extends AppCompatActivity {


    private static final String TAG = "LeaderBoardActivity";
    private FirebaseFirestore db;
    private final int NUMBER_OF_TOP_SCORES = 3;
    private FirebaseUser m_user;

    private TextView[] scoresTextView;
    private TextView userScoreTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        db = FirebaseFirestore.getInstance();

        m_user = FirebaseAuth.getInstance().getCurrentUser();


        //topUsersStats = new UserStats[NUMBER_OF_TOP_SCORES];
        findViewById(R.id.getSubjectButton).setOnClickListener(new LeaderBoardActivity.chooseSubjectClickHandler());
        scoresTextView = new TextView[NUMBER_OF_TOP_SCORES];
        userScoreTextView = findViewById(R.id.userScoreTextView);
        scoresTextView[0] = findViewById(R.id.scoreTextView1);
        scoresTextView[1] = findViewById(R.id.scoreTextView2);
        scoresTextView[2] = findViewById(R.id.scoreTextView3);

    }

    private void updateTopScore(UserStats[] userStats){
        int i = 0;
        for(UserStats stats : userStats){
            if(stats == null || stats.getTimeScore() == 0){
                continue;
            }
            String text = stats.getDisplayName() + ": " + stats.getScore() + "/10" +
                    " time: " + stats.getTimeScore();

            scoresTextView[i].setText(text);
            i += 1;
        }
    }



    @SuppressWarnings("ConstantConditions")
    private void getTopScore(String subject){

        Query query = db.collection(MAIN_STATS_COLLECTION)
                .whereEqualTo(STATS_SUBJECT_FIELD, subject)
                .orderBy(STATS_SCORE_FIELD, Query.Direction.DESCENDING)
                .orderBy(STATS_TIME_SCORE_FIELD, Query.Direction.ASCENDING).limit(NUMBER_OF_TOP_SCORES);

        query.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        int i = 0;
                        UserStats[] topUsersStats = new UserStats[NUMBER_OF_TOP_SCORES];
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            topUsersStats[i] = new UserStats((String)document.get(STATS_DISPLAY_NAME_FIELD),
                                    (double)document.get(STATS_TIME_SCORE_FIELD),
                                    (long)document.get(STATS_SCORE_FIELD),
                                    subject);
                            i += 1;
                        }
                        updateTopScore(topUsersStats);
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    private void updateUserStats(long score, double timeScore, long totalGames, long totalCorrectAnswers){
        String text = "Your score: " + score +
                " " + timeScore + ", total games:" + totalGames +
                " totalCorrectAnswers:" + totalCorrectAnswers;
        userScoreTextView.setText(text);
    }

    @SuppressWarnings("ConstantConditions")
    private void getUserScore(String subject){

        Query query = db.collection(MAIN_STATS_COLLECTION)
                .whereEqualTo(STATS_SUBJECT_FIELD, subject)
                .whereEqualTo(ID_FIELD_FIELD, m_user.getUid());

        query.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        boolean documentExist = false;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            long userBestScore = (long) document.get(STATS_SCORE_FIELD);
                            double userBestTimeScore = (double) document.get(STATS_TIME_SCORE_FIELD);
                            long totalGames = (long) document.get(STATS_TOTAL_GAMES_PLAYED_FIELD);
                            long totalCorrectAnswers = (long) document.get(STATS_TOTAL_CORRECT_ANSWERS_FIELD);

                            updateUserStats(userBestScore,
                                    userBestTimeScore,
                                    totalGames,
                                    totalCorrectAnswers);
                            documentExist = true;

                        }

                        if(!documentExist){
                            String text = "You haven't played competitive in this subject yet";
                            userScoreTextView.setText(text);
                        }

                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    private class chooseSubjectClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View view){

            Intent intent = new Intent(LeaderBoardActivity.this, ChooseSubjectActivity.class);
            getSubjectResult.launch(intent);

        }
    }

    ActivityResultLauncher<Intent> getSubjectResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // Here, no request code
                    Intent data = result.getData();
                    assert data != null;

                    String subject = data.getStringExtra(String.valueOf(R.string.subject));
                    getTopScore(subject);
                    getUserScore(subject);
                }
            });

}