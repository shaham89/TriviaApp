package com.example.triviaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.triviaapp.customClasses.Question;
import com.example.triviaapp.customClasses.Game;
import com.example.triviaapp.customClasses.Subject;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ChooseSubjectActivity extends AppCompatActivity {

    private static final String TAG = "ChooseSubjectActivity";
    private boolean isSolo;
    private Game m_game;
    private String subject;
    private int subjectImageID;
    private ArrayList<Question> questions;
    private FirebaseFirestore db;
    private Subject chosen_subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_subject);

//        db = FirebaseFirestore.getInstance();
//
//        Intent intent = getIntent();
//        m_room = (Room) intent.getExtras().get(String.valueOf(R.string.room));


        //questions = new ArrayList<Question>();

        findViewById(R.id.capitals_image).setOnClickListener(new imageClickHandler());
        findViewById(R.id.astronomy_image).setOnClickListener(new imageClickHandler());
        findViewById(R.id.history_image).setOnClickListener(new imageClickHandler());
        findViewById(R.id.generalKnowledge_image).setOnClickListener(new imageClickHandler());
        findViewById(R.id.harryPotter_image).setOnClickListener(new imageClickHandler());
        findViewById(R.id.worldCup_image).setOnClickListener(new imageClickHandler());
        findViewById(R.id.customSubject_image).setOnClickListener(new imageClickHandler());
    }

    protected class imageClickHandler implements View.OnClickListener {
        //check if the user exist, and if he doesn't call addUser
        //if he does, show Toast
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            int m_id = view.getId();
            switch (m_id){
                case R.id.capitals_image:
                    chosen_subject = new Subject(Subject.subjectNameCapitals,
                            R.drawable.earth,
                            Subject.displayNameCapitals);
                    break;
                case R.id.astronomy_image:
                    chosen_subject = new Subject(Subject.subjectNameAstronomy,
                            R.drawable.astronomy,
                            Subject.displayNameAstronomy);
                    break;
                case R.id.history_image:
                    chosen_subject = new Subject(Subject.subjectNameHistory,
                            R.drawable.history,
                            Subject.displayNameHistory);
                    break;
                case R.id.generalKnowledge_image:
                    chosen_subject = new Subject(Subject.subjectNameGeneralKnowledge,
                            R.drawable.general_knowledge,
                            Subject.displayNameGeneralKnowledge);
                    break;
                case R.id.harryPotter_image:
                    chosen_subject = new Subject(Subject.subjectNameHarryPotter,
                            R.drawable.harry_potter,
                            Subject.displayNameHarryPotter);
                    break;
                case R.id.worldCup_image:
                    chosen_subject = new Subject(Subject.subjectNameWorldCup,
                            R.drawable.worldcup,
                            Subject.displayNameWorldCup);
                    break;
                case R.id.customSubject_image:
                    String customSubjectName = ((EditText)findViewById(R.id.customSubjectTitleTextView)).getText().toString();
                    chosen_subject = new Subject(Subject.subjectNameCustomSubject,
                            R.drawable.network_brain,
                            customSubjectName);
                    break;
            }

            returnSubject();
        }

    }


    private void returnSubject(){
        Intent intent = new Intent();
        intent.putExtra(String.valueOf(R.string.subject), chosen_subject);
//        intent.putExtra(String.valueOf(R.string.subject), subject);
//        intent.putExtra(String.valueOf(R.string.image_id), subjectImageID);
        setResult(Activity.RESULT_OK, intent);
        finish();

    }



}