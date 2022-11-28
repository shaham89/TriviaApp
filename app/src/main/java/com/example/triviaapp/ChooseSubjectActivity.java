package com.example.triviaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class ChooseSubjectActivity extends AppCompatActivity {

    private static final String TAG = "ChooseSubjectActivity";
    private boolean isSolo;
    private Room room;
    private String subject;
    private ArrayList<Question> questions;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_subject);

        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        room = (Room) intent.getExtras().get(String.valueOf(R.string.room));


        questions = new ArrayList<Question>();

        findViewById(R.id.capitals_image).setOnClickListener(new imageClickHandler());

    }

    protected class imageClickHandler implements View.OnClickListener {
        //check if the user exist, and if he doesn't call addUser
        //if he does, show Toast
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.capitals_image){
                subject = getString(R.string.capitals);
            }

        }

    }

    private void getQuestionFromFirestore(int index){
        String MAIN_SUBJECT_COLLECTION = "subjects_questions";
        String path = MAIN_SUBJECT_COLLECTION + "/" + subject + "_subject/" + subject + "_questions";
        CollectionReference docRef = db.collection(path);

        docRef
                .whereEqualTo("Index", index)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> questionJson = document.getData();
                            Log.d(TAG, document.getId() + " => " + questionJson);
                            questions.add(new Question(questionJson));
                        }
                        startGame();
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });

    }

    private void startGame(){
        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
        intent.putExtra(getString(R.string.questions), questions);
        finish();
        startActivity(intent);
    }

}