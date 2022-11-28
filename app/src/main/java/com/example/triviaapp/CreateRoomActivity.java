package com.example.triviaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

public class CreateRoomActivity extends AppCompatActivity {

    private static final String TAG = "CreateRoomActivity";
    private static final int LAUNCH_SECOND_ACTIVITY = 7;
    private Room m_room;
    private ArrayList<Question> questions;
    private FirebaseFirestore db;
    public final String MAIN_SUBJECT_COLLECTION = "subjects_questions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        Intent intent = getIntent();



        m_room.is_solo = true;
        m_room.maxPlayers = 4;
        m_room.questions_number = 10;
        m_room.name = "test room";

        findViewById(R.id.chooseSubjectButton).setOnClickListener(new chooseSubjectClickHandler());
        findViewById(R.id.startGameButton).setOnClickListener(new chooseSubjectClickHandler());

    }


    private class chooseSubjectClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View view){

            Intent i = new Intent(getApplicationContext(), ChooseSubjectActivity.class);
            startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){
                String subject = data.getStringExtra(String.valueOf(R.string.subject));
                m_room.subject = subject;

                callGetQuestions(m_room.questions_number);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
            }
        }
    } //onActivityResult

    private class startGameClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View view){

            Intent intent = new Intent(getApplicationContext(), GameActivity.class);
            intent.putExtra(getString(R.string.questions), questions);
            finish();
            startActivity(intent);
        }
    }



    private void callGetQuestions(int numberOfWantedQuestions){
        String subjectPath = m_room.subject + "_subject";
        DocumentReference docRef = db.collection(MAIN_SUBJECT_COLLECTION).document(subjectPath);
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            int maxQuestions = (int) documentSnapshot.get("Length");
            getQuestions(numberOfWantedQuestions, maxQuestions);
        });
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    private void getQuestions(int numberOfWantedQuestions, int maxQuestions){
        HashSet<Integer> randomIndicesSet = new HashSet<Integer>();

        while(randomIndicesSet.size() < numberOfWantedQuestions){
            randomIndicesSet.add(getRandomNumber(0, maxQuestions - 1));
        }

        for (int index : randomIndicesSet) {
            getQuestionFromFirestore(index);
        }

    }

    private void getQuestionFromFirestore(int index){

        String path = MAIN_SUBJECT_COLLECTION + "/" + m_room.subject + "_subject/" + m_room.subject + "_questions";
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
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });

    }


}