package com.example.triviaapp;

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
import android.widget.EditText;

import com.example.triviaapp.custom_classes.Question;
import com.example.triviaapp.custom_classes.Room;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

public class CreateSoloRoomActivity extends AppCompatActivity {

    //views
    private SwitchMaterial isCompetitiveSwitch;
    private EditText roomTitleEditText;
    private EditText questionNumberEditText;

    private static final String TAG = "CreateRoomActivity";
    private Room m_room;
    private static ArrayList<Question> questions;
    private FirebaseFirestore db;
    private FirebaseUser m_user;
    public final String MAIN_SUBJECT_COLLECTION = "subjects_questions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_solo_room);

        db = FirebaseFirestore.getInstance();
        FirebaseAuth m_auth = FirebaseAuth.getInstance();
        m_user = m_auth.getCurrentUser();
        assert m_user != null;

        //m_room = new Room(true, "", 4, 10, "test room");
        initViews();


        m_room = new Room();

    }

    private void initViews() {
        findViewById(R.id.chooseSubjectButton).setOnClickListener(new chooseSubjectClickHandler());
        findViewById(R.id.startGameButton).setOnClickListener(new startGameClickHandler());

        isCompetitiveSwitch = findViewById(R.id.isCompetitiveSwitch);

        // To listen for a switch's checked/unchecked state changes
        //isCompetitiveSwitch.setOnCheckedChangeListener();

        roomTitleEditText = findViewById(R.id.roomTitleEditText);
        questionNumberEditText = findViewById(R.id.questionsNumberEditText);

        roomTitleEditText.setText(MessageFormat.format("{0}''s room", m_user.getDisplayName()));
        questionNumberEditText.setText(getString(R.string.DEFAULT_QUESTION_NUM));
    }

    private class chooseSubjectClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View view){

            Intent intent = new Intent(CreateSoloRoomActivity.this, ChooseSubjectActivity.class);
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
                        m_room.subject = data.getStringExtra(String.valueOf(R.string.subject));

                    }
                }
            });

    private static final Object lock = new Object();

    private void waitUntilQuestionsAreRead(){
        final long MAXIMUM_WAITING_TIME_MS = 20000;
        Thread game_thread = new Thread() {
            @Override
            public void run() {
                synchronized(lock) {
                    try {
                        lock.wait(MAXIMUM_WAITING_TIME_MS);
                        m_room.questions = questions;
                        startGameActivity();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        game_thread.start();
    }


    private class startGameClickHandler implements View.OnClickListener {

        @Override
        public void onClick(View view){

            callGetQuestions(m_room.questions_number);
            m_room.is_competitive = isCompetitiveSwitch.isChecked();
            if(m_room.is_competitive){
                m_room.questions_number = Room.DEFAULT_QUESTION_NUMBER;
            } else {
                m_room.questions_number = Integer.parseInt(questionNumberEditText.getText().toString());
            }
            m_room.room_name = roomTitleEditText.getText().toString();

            waitUntilQuestionsAreRead();
        }
    }

    private void startGameActivity(){
        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
        intent.putExtra(getString(R.string.room), m_room);
        finish();
        startActivity(intent);
    }


    private void callGetQuestions(int numberOfWantedQuestions){
        String subjectPath = m_room.subject + "_subject";
        DocumentReference docRef = db.collection(MAIN_SUBJECT_COLLECTION).document(subjectPath);

        questions = new ArrayList<>();

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Document found in the offline cache
                DocumentSnapshot document = task.getResult();
                Log.d(TAG, "Cached document data: " + document.getData());
                int maxQuestions = Objects.requireNonNull(document.getLong("Length")).intValue();
                getQuestions(numberOfWantedQuestions, maxQuestions);
                Log.d(TAG, "Questions:" + questions);

            } else {
                Log.d(TAG, "Cached get failed: ", task.getException());
            }
        });
    }

    private int getRandomNumber(int max) {
        return (int) ((Math.random() * (max)));
    }

    private void getQuestions(int numberOfWantedQuestions, int maxQuestions){
        HashSet<Integer> randomIndicesSet = new HashSet<>();

        while(randomIndicesSet.size() < numberOfWantedQuestions){
            randomIndicesSet.add(getRandomNumber(maxQuestions - 1));
        }

        for (int index : randomIndicesSet) {
            getQuestionFromFirestore(index, numberOfWantedQuestions);
        }

    }

    private void getQuestionFromFirestore(int index, int numberOfWantedQuestions){

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

                            if(questions.size() == numberOfWantedQuestions){
                                synchronized (lock){
                                    lock.notify();
                                }
                            }
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                    Log.d(TAG, "Questions:" + questions);

                });



    }


}