package com.example.triviaapp;

import static com.example.triviaapp.helperFunctions.FireStoreConstants.ID_FIELD_FIELD;
import static com.example.triviaapp.helperFunctions.FireStoreConstants.MAIN_STATS_COLLECTION;
import static com.example.triviaapp.helperFunctions.FireStoreConstants.STATS_DISPLAY_NAME_FIELD;
import static com.example.triviaapp.helperFunctions.FireStoreConstants.STATS_SCORE_FIELD;
import static com.example.triviaapp.helperFunctions.FireStoreConstants.STATS_SUBJECT_FIELD;
import static com.example.triviaapp.helperFunctions.FireStoreConstants.STATS_TIME_SCORE_FIELD;
import static com.example.triviaapp.helperFunctions.FireStoreConstants.STATS_TOTAL_CORRECT_ANSWERS_FIELD;
import static com.example.triviaapp.helperFunctions.FireStoreConstants.STATS_TOTAL_GAMES_PLAYED_FIELD;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.triviaapp.custom_classes.UserStats;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class LeaderBoardActivity extends AppCompatActivity {


    private static final String TAG = "LeaderBoardActivity";
    private FirebaseFirestore db;
    private final int NUMBER_OF_TOP_SCORES = 3;
    private static UserStats[] topUsersStats;
    private String subject;
    private FirebaseUser m_user;
    private static long userBestScore;
    private static double userBestTimeScore;
    private static long totalGames;
    private static long totalCorrectAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        db = FirebaseFirestore.getInstance();

        m_user = FirebaseAuth.getInstance().getCurrentUser();


        topUsersStats = new UserStats[NUMBER_OF_TOP_SCORES];
        findViewById(R.id.getSubjectButton).setOnClickListener(new LeaderBoardActivity.chooseSubjectClickHandler());



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
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            topUsersStats[i] = new UserStats((String)document.get(STATS_DISPLAY_NAME_FIELD),
                                    (double)document.get(STATS_TIME_SCORE_FIELD),
                                    (long)document.get(STATS_SCORE_FIELD),
                                    subject);
                            i += 1;
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    @SuppressWarnings("ConstantConditions")
    private void getUserScore(String subject){

        Query query = db.collection(MAIN_STATS_COLLECTION)
                .whereEqualTo(STATS_SUBJECT_FIELD, subject)
                .whereEqualTo(ID_FIELD_FIELD, m_user.getUid());

        query.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            userBestScore = (long) document.get(STATS_SCORE_FIELD);
                            userBestTimeScore = (double) document.get(STATS_TIME_SCORE_FIELD);
                            totalGames = (long) document.get(STATS_TOTAL_GAMES_PLAYED_FIELD);
                            totalCorrectAnswers = (long) document.get(STATS_TOTAL_CORRECT_ANSWERS_FIELD);
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
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Here, no request code
                        Intent data = result.getData();
                        assert data != null;
                        subject = data.getStringExtra(String.valueOf(R.string.subject));
                        getTopScore(subject);
                        getUserScore(subject);
                    }
                }
            });

}