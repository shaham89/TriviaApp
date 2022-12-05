package com.example.triviaapp;

import static com.example.triviaapp.helperFunctions.FireStoreConstents.MAIN_STATS_COLLECTION;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class LeaderBoardActivity extends AppCompatActivity {


    private static final String TAG = "LeaderBoardActivity";
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        db = FirebaseFirestore.getInstance();

    }


    private void getTopScore(String subject){
        Query query = db.collection(MAIN_STATS_COLLECTION)
                .whereEqualTo(getString(R.string.subject), subject)
                .orderBy("timeScore", Query.Direction.ASCENDING).limit(3);

        query.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

}