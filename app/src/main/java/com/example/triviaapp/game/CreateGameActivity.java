package com.example.triviaapp.game;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.triviaapp.ChooseSubjectActivity;
import com.example.triviaapp.R;
import com.example.triviaapp.customClasses.Question;
import com.example.triviaapp.customClasses.Game;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

public class CreateGameActivity extends AppCompatActivity {

    private static final String TAG = "CreateRoomActivity";

    private EditText questionNumberEditText;
    private EditText questionsTimeEditText;


    private Game m_game;
    private static ArrayList<Question> questions;
    private int numberOfWantedQuestions;

    private FirebaseFirestore db;
    public final String MAIN_SUBJECT_COLLECTION = "subjects_questions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);

        db = FirebaseFirestore.getInstance();
        FirebaseAuth m_auth = FirebaseAuth.getInstance();
        FirebaseUser m_user = m_auth.getCurrentUser();
        assert m_user != null;

        hasStartGameClickedAlready = false;

        //m_room = new Room(true, "", 4, 10, "test room");
        initViews();


        m_game = new Game();
        Intent intent = getIntent();
        m_game.setCompetitive(intent.getBooleanExtra(String.valueOf(R.string.is_competitive_text), false));

        if(m_game.isCompetitive()){
            questionNumberEditText.setText(String.valueOf(Game.COMPETITIVE_QUESTION_NUMBER));
            questionNumberEditText.setEnabled(false);

            questionsTimeEditText.setText(String.valueOf(Game.COMPETITIVE_TIME_PER_QUESTION_SEC));
            questionNumberEditText.setEnabled(false);
        }

    }

    private void initViews() {
        findViewById(R.id.chooseSubjectButton).setOnClickListener(new chooseSubjectClickHandler());
        findViewById(R.id.startGameButton).setOnClickListener(new startGameClickHandler());

        //views

        // To listen for a switch's checked/unchecked state changes
        //isCompetitiveSwitch.setOnCheckedChangeListener();

        questionsTimeEditText = findViewById(R.id.timePerQuestionEditText);
        questionsTimeEditText.setText(String.valueOf(Game.DEFAULT_TIME_PER_QUESTION_SEC));

        questionNumberEditText = findViewById(R.id.questionsNumberEditText);

        questionNumberEditText.setText(String.valueOf(Game.DEFAULT_QUESTION_NUMBER));
    }

    private class chooseSubjectClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View view){

            Intent intent = new Intent(CreateGameActivity.this, ChooseSubjectActivity.class);
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
                        m_game.setSubject(data.getStringExtra(String.valueOf(R.string.subject)));
                        int imageID = data.getIntExtra(String.valueOf(R.string.image_id), 0);

                        if (imageID != 0){
                            final ImageView img = findViewById(R.id.subjectImage);
                            img.setImageResource(imageID);
                            final TextView subjectView = findViewById(R.id.currentSubjectTitleTextView);
                            subjectView.setText(m_game.getSubject());
                        }

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
                        m_game.setQuestions(new Question[questions.size()]);
                        m_game.setQuestions(questions.toArray(m_game.getQuestions()));
                        startGameActivity();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        game_thread.start();
    }

    private static boolean hasStartGameClickedAlready = false;

    private class startGameClickHandler implements View.OnClickListener {

        @Override
        public void onClick(View view){
            if(hasStartGameClickedAlready){return;}
            hasStartGameClickedAlready = true;

            //m_game.setCompetitive(isCompetitiveSwitch.isChecked());
            if(m_game.isCompetitive()){
                numberOfWantedQuestions = Game.COMPETITIVE_QUESTION_NUMBER;
                m_game.setTimePerQuestionSec(Game.COMPETITIVE_TIME_PER_QUESTION_SEC);
            } else {
                numberOfWantedQuestions = Integer.parseInt(questionNumberEditText.getText().toString());
                m_game.setTimePerQuestionSec(Integer.parseInt(questionsTimeEditText.getText().toString()));
            }

            callGetQuestions();
            waitUntilQuestionsAreRead();
        }

    }

    private void startGameActivity(){
        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
        intent.putExtra(getString(R.string.game_intent_text), m_game);
        finish();
        startActivity(intent);
    }


    private void callGetQuestions(){
        String subjectPath = m_game.getSubject() + "_subject";
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

        String path = MAIN_SUBJECT_COLLECTION + "/" + m_game.getSubject() + "_subject/" + m_game.getSubject() + "_questions";
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